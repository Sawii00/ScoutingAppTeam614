package com.example.saverio.scoutingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.saverio.scoutingapp.R;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread timer = new Thread(){

            public void run()
            {

                try
                {
                    sleep(3000);


                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();

                }
                finally
                {
                    Intent openStartingPoint = new Intent("com.example.saverio.STARTINGPOINT");
                    startActivity(openStartingPoint);

                }
            }


        };
        timer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
