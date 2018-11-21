package com.horn.blue.previewsanta3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        guardarIsLogin("Yes");
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!cargarPref()) {
                    Intent intentHome = new Intent(MainActivity.this, HomeLoginActivity.class);
                    startActivity(intentHome);
                    finish();
                } else {
                   Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }

    private void guardarIsLogin(String x) {
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isLogin", x);
        editor.commit();
    }

    private boolean cargarPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("isLogin", "No").equals("Yes")) {
            return true;
        }
        return false;
    }
}
