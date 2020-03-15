package com.attend.authenticate;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.attend.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText emailId;
    private TextView submit;

    private FirebaseAuth fAuth;
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        fAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_button:
                // Call Submit button task
                submitButtonTask();
                break;
        }
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            Toast.makeText(this, "Please enter your Email Id.", Toast.LENGTH_SHORT).show();

            // Check if email id is valid or not
        else if (!m.find())
            Toast.makeText(this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();

            // Else submit email id and fetch passwod or do your stuff
        else
            //Toast.makeText(getActivity(), "Get Forgot Password.",Toast.LENGTH_SHORT).show();

            fAuth.sendPasswordResetEmail(getEmailId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Password Reset Link Set to your email", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error! Reset link not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
