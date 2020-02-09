package com.attend.authenticate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.attend.R;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
