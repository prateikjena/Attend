package com.attend.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.attend.MainActivity;
import com.attend.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

 public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     DrawerLayout drawerLayout;
     ActionBarDrawerToggle actionBarDrawerToggle;
     Toolbar toolbar;
     NavigationView navigationView;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_welcome);

         toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         drawerLayout = findViewById(R.id.drawer);
         navigationView = findViewById(R.id.navigationView);

         actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
         drawerLayout.addDrawerListener(actionBarDrawerToggle);
         actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
         actionBarDrawerToggle.syncState();
     }

     public void logout(final View view) {
         FirebaseAuth.getInstance().signOut();
         GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build())
                 .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 startActivity(new Intent(view.getContext(), MainActivity.class));
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

     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

         return true;
     }
 }