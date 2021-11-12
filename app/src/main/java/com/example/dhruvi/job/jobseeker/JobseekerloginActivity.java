package com.example.dhruvi.job.jobseeker;

import static com.example.dhruvi.job.Url.isEmailValid;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;

import java.util.ArrayList;

public class JobseekerloginActivity extends AppCompatActivity {

    EditText jobseekerlogin_unm, jobseekerlogin_pwd;
    ImageView jobseekerlogin_img;
    Button jobseekerlogin_btn;
    CardView joblogincv;
    TextView jobfrgtpwd;
    public static String email, pwd;
    ArrayList<String> key;
    ArrayList<String> value;
    CallServices cs;
    Session s;
    Intent i;
    Animation animation,animation1;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekerlogin);

        jobseekerlogin_unm = findViewById(R.id.jobseekerlogin_unm);
        joblogincv = findViewById(R.id.joblogincv);
        key=new ArrayList<>();
        value=new ArrayList<>();
        jobseekerlogin_img = findViewById(R.id.jobseekerlogin_img);
        jobfrgtpwd = findViewById(R.id.jobfrgtpwd);
        jobseekerlogin_pwd = findViewById(R.id.jobseekerlogin_pwd);
        jobseekerlogin_btn = findViewById(R.id.jobseekerlogin_btn);
        s = new Session(JobseekerloginActivity.this);
        cs=new CallServices();


        animation= AnimationUtils.loadAnimation(JobseekerloginActivity.this,R.anim.fadein);
        animation1= AnimationUtils.loadAnimation(JobseekerloginActivity.this,R.anim.bottomtotop);
        s=new Session(JobseekerloginActivity.this);

        jobseekerlogin_img.setAnimation(animation);
        joblogincv.setAnimation(animation1);

        jobfrgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(JobseekerloginActivity.this);
                final EditText ansfrjob,newpfrjob;
                Button btnfrjob;
                d.setContentView(R.layout.forgetpasswordjob);
                ansfrjob=d.findViewById(R.id.ansfrjob);
                newpfrjob=d.findViewById(R.id.newpfrjob);
                btnfrjob=d.findViewById(R.id.btnfrjob);
                btnfrjob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key.clear();
                        value.clear();
                        key.add("email");
                        key.add("ans");
                        key.add("new");
                        value.add(jobseekerlogin_unm.getText().toString());
                        value.add(ansfrjob.getText().toString());
                        value.add(newpfrjob.getText().toString());
                        String res = cs.CallServices(JobseekerloginActivity.this,Url.URL+"forgetpassjob.php",Url.METHOD,key,value);
                        if(res.equals("1"))
                        {
                            Toast.makeText(JobseekerloginActivity.this, "Changed succesfully", Toast.LENGTH_SHORT).show();
                            d.dismiss();
                        }
                        else if (res.equals("0"))
                        {
                            Toast.makeText(JobseekerloginActivity.this, "Not changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                d.show();
            }
        });

        jobseekerlogin_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    // get values from edittext in string object
                    email = jobseekerlogin_unm.getText().toString();
                    pwd = jobseekerlogin_pwd.getText().toString();

                    if (isEmailValid(email)) {

                    // give value to php script
                    key.add("email");
                    key.add("password");
                    value.add(email);
                    value.add(pwd);

                        try{
                        res = cs.CallServices(JobseekerloginActivity.this, Url.URL + "loginjobseeker.php", Url.METHOD, key, value);

                    }
                    catch(Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                    //String res = cs.CallServices(JobseekerloginActivity.this, Url.URL + "loginjobseeker.php", Url.METHOD, key, value);
                    if (res.trim().equals("1")) {
                        s.setLogin(email);
                        s.setType("job");
                        s.setPwd(pwd);
                        startActivity(new Intent(JobseekerloginActivity.this, JobseekerDashboardActivity.class));
                        finish();
                    } else if (res.trim().equals("0")) {
                        jobseekerlogin_pwd.getText().clear();
                        Toast.makeText(JobseekerloginActivity.this, "Wrong username and password", Toast.LENGTH_SHORT).show();
                    }
                    } else {
                        Toast.makeText(JobseekerloginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        jobseekerlogin_unm.getText().clear();
                    }
                }
            });

    }


}
