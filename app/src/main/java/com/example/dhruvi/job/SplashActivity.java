package com.example.dhruvi.job;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhruvi.job.company.CompanydashboardActivity;
import com.example.dhruvi.job.jobseeker.JobseekerDashboardActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView splashLogo;
    TextView splashtitle;
    Handler handler;
    Animation animation,animation1;
    Session s;
    String res,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        splashLogo=findViewById(R.id.splashLogo);
        splashtitle=findViewById(R.id.splashtitle);

        animation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.lefttocenter);
        animation1= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fadein);
        s=new Session(SplashActivity.this);

        splashLogo.setAnimation(animation);
        splashtitle.setAnimation(animation1);


        res=s.checkLogin();
        type=s.getType();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(res.equals(""))
                {
                    startActivity(new Intent(SplashActivity.this,CheckuserActivity.class));
                    finish();
                }
                else
                {
                    if(type.equals("job"))
                    {
                        startActivity(new Intent(SplashActivity.this, JobseekerDashboardActivity.class));
                        finish();
                    }
                    else if(type.equals("comp"))
                    {
                        startActivity(new Intent(SplashActivity.this, CompanydashboardActivity.class));
                        finish();
                    }
                }

            }
        },5000);
    }
    }

