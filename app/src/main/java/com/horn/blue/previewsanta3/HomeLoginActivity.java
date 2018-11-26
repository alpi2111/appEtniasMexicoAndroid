package com.horn.blue.previewsanta3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class HomeLoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private ImageSwitcher imageSwitcher;
    private int p;
    private static final int DURATION = 5000;
    private Timer timer = null;
    private int[] img = {R.drawable.e01, R.drawable.e02, R.drawable.e03, R.drawable.e04, R.drawable.e05, R.drawable.e06, R.drawable.e07};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        imageSwitcher = findViewById(R.id.isEtnias);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(HomeActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });

        Animation fadeIn = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);

        if (timer != null) {
            timer.cancel();
        }
        p = 0;
        startSlider();
    }

    private void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageSwitcher.setImageResource(img[p]);
                        p++;
                        if (p == img.length) {
                            p = 0;
                        }
                    }
                });
            }
        }, 0, DURATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
        }
        startSlider();
    }
}
