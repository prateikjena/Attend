package com.attend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.attend.authenticate.Authenticate;
import com.attend.welcome.Welcome;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button student,teacher;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        student = findViewById(R.id.student);
        teacher = findViewById(R.id.teacher);

        student.setOnClickListener(this);
        teacher.setOnClickListener(this);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null || mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this,Welcome.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student:
                startActivity(new Intent(this, Authenticate.class));
                break;
            case R.id.teacher:
                startActivity(new Intent(this,Authenticate.class));
                break;
        }
    }
}