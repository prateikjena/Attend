package com.attend.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.attend.MainActivity;
import com.attend.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     private View header;
     private DrawerLayout drawerLayout;
     private ActionBarDrawerToggle actionBarDrawerToggle;
     private Toolbar toolbar;
     private NavigationView navigationView;
     private FragmentTransaction fragmentTransaction;
     private FragmentManager fragmentManager;
     private TextView navName;
     private ImageView navImageView;
     private FirebaseAuth fAuth;
     String emailId, getFullName, userType;
     private FirebaseFirestore fStore;
     Uri getImage;
    boolean doubleBackToExitPressedOnce = false;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_welcome);

         toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         fAuth = FirebaseAuth.getInstance();
         fStore = FirebaseFirestore.getInstance();
         userType = getIntent().getStringExtra("USER");

         drawerLayout = findViewById(R.id.drawer);
         navigationView = findViewById(R.id.navigationView);
         navigationView.setNavigationItemSelectedListener(this);

         header = navigationView.getHeaderView(0);
         navName = header.findViewById(R.id.navigation_name);
         navImageView = header.findViewById(R.id.navigation_image);

         actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
         drawerLayout.addDrawerListener(actionBarDrawerToggle);
         actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
         actionBarDrawerToggle.syncState();

         String user = getIntent().getStringExtra("USER");

         Bundle bundle = new Bundle();
         bundle.putString("USER", user);

         // set Fragment class Arguments
         WelcomeFragment welcomeFragment = new WelcomeFragment();
         welcomeFragment.setArguments(bundle);


         //load fragment by default
         fragmentManager = getSupportFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.add(R.id.content_main, welcomeFragment);
         fragmentTransaction.commit();

         navProfileSetup();
     }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

         drawerLayout.closeDrawer(GravityCompat.START);

         if (menuItem.getItemId() == R.id.home) {
             fragmentManager = getSupportFragmentManager();
             fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.add(R.id.content_main, new WelcomeFragment());
             fragmentTransaction.commit();
         }

         if (menuItem.getItemId() == R.id.logout) {
             fAuth.signOut();
             GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build())
                     .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     startActivity(new Intent(getApplicationContext(), MainActivity.class));
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(Welcome.this, "LogOut failed..", Toast.LENGTH_SHORT).show();
                 }
             });
             startActivity(new Intent(getApplicationContext(), MainActivity.class));
             finish();
         }

         return true;
     }

     private void navProfileSetup() {
         GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
         if (signInAccount != null) {
             getFullName = signInAccount.getDisplayName();
             getImage = signInAccount.getPhotoUrl();
             emailId = signInAccount.getEmail();

             Glide.with(this).load(getImage).apply(RequestOptions.circleCropTransform()).into(navImageView);
             navName.setText(getFullName);
         }
     }

     public void storeOnFirestore() {
         Map<String, Object> user = new HashMap<>();
         user.put("fName", getFullName);
         user.put("email", emailId);
         user.put("userType", userType);
         user.put("ImageURI", getImage);

         String userID = fAuth.getCurrentUser().getUid();
         DocumentReference documentReference = fStore.collection("users").document(userID);

                 documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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

}