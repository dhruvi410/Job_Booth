package com.example.dhruvi.job.jobseeker;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.CheckuserActivity;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;
import com.example.dhruvi.job.jobseeker.applied.AppliedJob;
import com.example.dhruvi.job.jobseeker.categoryrecyclerview.Category;

import java.util.ArrayList;

public class JobseekerDashboardActivity extends AppCompatActivity {

    CardView cvpost,cvlogout,cvfeed,cvedit,cvmyapply;
    ArrayList<String> key,value;
    RatingBar ratecomp;
    EditText ed;
    Button btn;
    String feed,stremail;
    Session s;
    Dialog d;
    CallServices cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker_dashboard);

        cvpost=findViewById(R.id.cvpost);
        cvmyapply=findViewById(R.id.cvmyapply);
        cvlogout=findViewById(R.id.cvlogout);
        cvfeed=findViewById(R.id.cvfeed);
        cvedit=findViewById(R.id.cvedit);
        s=new Session(JobseekerDashboardActivity.this);
        cs=new CallServices();
        stremail=s.checkLogin();
        key=new ArrayList<>();
        value=new ArrayList<>();

        cvpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobseekerDashboardActivity.this,Category.class));
            }
        });

        cvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.logout();
                startActivity(new Intent(JobseekerDashboardActivity.this, CheckuserActivity.class));
                finish();
            }
        });
        cvfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              d=new Dialog(JobseekerDashboardActivity.this);
              d.setContentView(R.layout.companyfeedback);
                ed=d.findViewById(R.id.ed);
                btn=d.findViewById(R.id.btn);
                ratecomp=d.findViewById(R.id.ratecomp);
                d.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        feed=ed.getText().toString();
                        key.add("email");
                        key.add("feed");
                        key.add("rate");
                        value.add(stremail);
                        value.add(feed);
                        value.add(String.valueOf(ratecomp.getRating()));
                        String rr=cs.CallServices(JobseekerDashboardActivity.this, Url.URL+"jobfeed.php",Url.METHOD,key,value);
                        if(rr.equals("1"))
                        {
                            Toast.makeText(JobseekerDashboardActivity.this, "Feedback given", Toast.LENGTH_SHORT).show();
                            d.dismiss();}
                        else
                        {Toast.makeText(JobseekerDashboardActivity.this, "Feedback not reached", Toast.LENGTH_SHORT).show();
                            d.dismiss();}
                    }
                });
            }
        });

        cvedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobseekerDashboardActivity.this, EditprofileJobseeker.class));
            }
        });
        cvmyapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobseekerDashboardActivity.this,AppliedJob.class));
            }
        });
    }
}
