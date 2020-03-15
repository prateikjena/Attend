package com.attend.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.attend.MainActivity;
import com.attend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText fullName, emailId, mobileNumber, password, confirmPassword, roll;
    private CheckBox terms_conditions;
    private Button signUpButton;
    private Spinner stream, batch;
    private FirebaseFirestore fStore;
    private String userID;

    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.userEmailId);
        mobileNumber = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpBtn);
        stream = findViewById(R.id.spinner);
        batch = findViewById(R.id.spinner2);
        roll = findViewById(R.id.roll);
        terms_conditions = findViewById(R.id.terms_conditions);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signUpBtn)
                createUser();

    }

    private void createUser() {

        final String getFullName = fullName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        final String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();

            // Check if email id valid or not
        else if (!m.find())
            Toast.makeText(this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            Toast.makeText(this, "Both password doesn't match.", Toast.LENGTH_SHORT).show();

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            Toast.makeText(this, "Please select Terms and Conditions.", Toast.LENGTH_SHORT).show();

            // Else do signup or do your stuff
        else {
            mAuth.createUserWithEmailAndPassword(getEmailId, getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("fName", getFullName);
                        user.put("email", getEmailId);
                        user.put("Phone", getMobileNumber);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "User Created for" + userID, Toast.LENGTH_SHORT).show();
                            }
                        });

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
