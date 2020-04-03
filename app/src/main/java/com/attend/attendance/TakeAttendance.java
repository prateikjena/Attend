package com.attend.attendance;

import android.animation.ArgbEvaluator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.attend.R;
import com.attend.attendance.cardview.Adapter;
import com.attend.attendance.cardview.Model;
import com.attend.welcome.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TakeAttendance extends AppCompatActivity implements View.OnClickListener {

    TextView dateTimeDisplay;
    Button present, absent;
    NonSwipableViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Map<String, Boolean> attendance = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();
    FirebaseFirestore db;
    String date, batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_attendance);

        batch = getIntent().getStringExtra("Batch");

        present = findViewById(R.id.present);
        absent = findViewById(R.id.absent);
        dateTimeDisplay = findViewById(R.id.showDate);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        present.setOnClickListener(this);
        absent.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        models = new ArrayList<>();

        sheet(batch);
    }

    public void nextCard() {
        if (viewPager.getCurrentItem()+1 < models.size()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        } else {
            confirmDialog();
            Toast.makeText(this, "Attendance Taken.. \n Press back to close sheet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        String roll = list.get(viewPager.getCurrentItem());
        switch (v.getId()) {
            case R.id.present:
                attendance.put(roll, true);
                nextCard();
                break;

            case R.id.absent:
                attendance.put(roll, false);
                nextCard();
                break;
        }
    }

    public void storeAttendance(String batch) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        db.collection(batch+"sheet").document(date)
                .set(attendance)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    void sheet(String batch) {
        db.collection(batch)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("Name");
                                String roll = document.getId();
                                list.add(roll);
                                models.add(new Model(R.drawable.namecard, roll, name));
                            }

                            adapter = new Adapter(models, getApplicationContext());

                            viewPager = findViewById(R.id.viewPager);
                            viewPager.setAdapter(adapter);
                            viewPager.setPadding(130, 0, 130, 0);

                            colors = new Integer[]{
                                    getResources().getColor(R.color.color1),
                                    getResources().getColor(R.color.color2),
                                    getResources().getColor(R.color.color3),
                                    getResources().getColor(R.color.color4),
                            };

                            viewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                                        viewPager.setBackgroundColor(
                                                (Integer) argbEvaluator.evaluate(
                                                        positionOffset,
                                                        colors[position],
                                                        colors[position + 1]
                                                )
                                        );
                                    }

                                    else {
                                        viewPager.setBackgroundColor(colors[colors.length - 1]);
                                    }
                                }
                                @Override
                                public void onPageSelected(int position) {

                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                        } else {
                            Toast.makeText(TakeAttendance.this, "Unable to fetch sheet\nCheck connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Attendance");
        builder.setMessage("Are you sure to save the attendance ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeAttendance(batch);
                Toast.makeText(getApplicationContext(), "Attendance Saved..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext() , Welcome.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Attendance not Saved!!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}