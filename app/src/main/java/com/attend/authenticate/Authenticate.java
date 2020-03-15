package com.attend.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.attend.R;
import com.attend.welcome.Welcome;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Authenticate extends AppCompatActivity implements OnClickListener {
    public static final int GOOGLE_SIGN_IN_CODE = 10005;
    private FirebaseAuth mAuth;

    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        Button withEmail = findViewById(R.id.withEmail);
        TextView signUp = findViewById(R.id.createAccount);

        withEmail.setOnClickListener(this);
        signUp.setOnClickListener(this);

        signIn = findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("419486184665-vife4n1du0oh9ang2t6obu9945mbeoas.apps.googleusercontent.com")
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this,gso);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAccount = signInTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);

                mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "You are now connected to Attend", Toast.LENGTH_SHORT).show();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.withEmail:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.createAccount:
                startActivity(new Intent(this, SignUp.class));
                break;
        }

    }

    private void updateUI(FirebaseUser currentUser) {
        startActivity(new Intent(this, Welcome.class));
    }
}
