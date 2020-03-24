package com.attend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.attend.authenticate.Authenticate;
import com.attend.welcome.Welcome;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button student,teacher;
    private FirebaseAuth mAuth;
    CollectionReference usersRef;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        usersRef = fStore.collection("users");

        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);

        student.setOnClickListener(this);
        teacher.setOnClickListener(this);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null || mAuth.getCurrentUser() != null) {
            fetchType();
        }
    }

    public void fetchType() {
        String uid = mAuth.getCurrentUser().getUid();
        final Intent intent = new Intent(MainActivity.this, Welcome.class);
        usersRef.document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String type = document.getString("userType");
                        if(type.equals("Student")) {
                            intent.putExtra("USER", type);
                            startActivityForResult(intent, 1);
                        } else if (type.equals("Teacher")) {
                            intent.putExtra("USER", type);
                            startActivityForResult(intent, 2);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String btn;
        switch (v.getId()) {
            case R.id.student:
                intent = new Intent(this, Authenticate.class);
                btn = "Student";
                intent.putExtra("CLICKED_BUTTON", btn);
                startActivity(intent);
                break;
            case R.id.teacher:
                intent = new Intent(this, Authenticate.class);
                btn = "Teacher";
                intent.putExtra("CLICKED_BUTTON", btn);
                startActivity(intent);
                break;
        }
    }
}