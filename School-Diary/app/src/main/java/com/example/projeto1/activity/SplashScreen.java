package com.example.projeto1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.projeto1.Login;
import com.example.projeto1.R;
import com.example.projeto1.activity.Menu;
import com.example.projeto1.functions.Globals;
import com.example.projeto1.worker.TestWorker;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // Test Worker

        final PeriodicWorkRequest testPeriodicWork =
                new PeriodicWorkRequest.Builder(TestWorker.class, 1, TimeUnit.DAYS)
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(testPeriodicWork);

        workManager.getWorkInfoByIdLiveData(testPeriodicWork.getId())
                .observe(this, new Observer<WorkInfo>() {

                            @Override
                            public void onChanged(@Nullable WorkInfo workInfo) {
                                if (workInfo != null) {
                                    Log.d("testPeriodicWork", "Status changed to : " + workInfo.getState());
                                }
                            }

                        }
                );


        // Login handler

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences secao = getSharedPreferences("Secao", 0);
                SharedPreferences emailsp = getSharedPreferences("Email", 0);

                SharedPreferences.Editor editor = secao.edit();
                SharedPreferences.Editor editor1 = emailsp.edit();

                if (secao.getString("login", "").equals("logado")) {

                    Intent intent = new Intent(SplashScreen.this, Menu.class);
                    startActivity(intent);
                    String id = emailsp.getString("id","");
                    Globals.user = Integer.valueOf(id);

                }  else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1600);
    }
}