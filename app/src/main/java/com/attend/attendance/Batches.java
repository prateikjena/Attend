package com.attend.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.attend.R;

public class Batches extends AppCompatActivity implements View.OnClickListener {

    Button first, second, third;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, TakeAttendance.class);
        switch (v.getId()) {
            case R.id.first:
                i.putExtra("Batch", "first");
                startActivity(i);
                break;
            case R.id.second:
                i.putExtra("Batch","SECOND");
                startActivity(i);
                break;
            case R.id.third:
                i.putExtra("Batch","THIRD");
                startActivity(i);
                break;
        }
    }
}
