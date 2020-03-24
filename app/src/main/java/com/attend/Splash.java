package com.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.attend.slider.LuncherManager;
import com.attend.slider.Slider;

public class Splash extends AppCompatActivity {

    LuncherManager launcherManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        launcherManager = new LuncherManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (launcherManager.isFirstTime()) {
                    launcherManager.setFirstLunch(false);
                    startActivity(new Intent(getApplicationContext(), Slider.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        }, 2000);
    }
}
