package com.attend.about;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.attend.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Developers extends AppCompatActivity {

    private ImageView profile1, profile2, profile3, profile4;
    private Uri dev1, dev2, dev3, dev4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        dev1 = Uri.parse("android.resource://com.attend/drawable/prateek");
        dev2 = Uri.parse("android.resource://com.attend/drawable/noor");
        dev3 = Uri.parse("android.resource://com.attend/drawable/nitish");
        dev4 = Uri.parse("android.resource://com.attend/drawable/sangram");

        profile1 = findViewById(R.id.profile1);
        profile2 = findViewById(R.id.profile2);
        profile3 = findViewById(R.id.profile3);
        profile4 = findViewById(R.id.profile4);

        Glide.with(this).load(dev1).apply(RequestOptions.circleCropTransform()).into(profile1);
        Glide.with(this).load(dev2).apply(RequestOptions.circleCropTransform()).into(profile2);
        Glide.with(this).load(dev3).apply(RequestOptions.circleCropTransform()).into(profile3);
        Glide.with(this).load(dev4).apply(RequestOptions.circleCropTransform()).into(profile4);
    }
}
