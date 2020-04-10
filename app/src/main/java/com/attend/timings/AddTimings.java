package com.attend.timings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.attend.R;

public class AddTimings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_timings);

        Button button = findViewById(R.id.addClass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomClassDialog bottomSheet = new BottomClassDialog();
                bottomSheet.show(getSupportFragmentManager(), "Unnecessary tag");
            }
        });
    }
}
