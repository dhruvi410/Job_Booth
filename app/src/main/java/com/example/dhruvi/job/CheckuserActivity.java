package com.example.dhruvi.job;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhruvi.job.company.CompanyloginActivity;
import com.example.dhruvi.job.company.CompanyregistrationActivity;
import com.example.dhruvi.job.jobseeker.JobseekerloginActivity;
import com.example.dhruvi.job.jobseeker.JobseekerregistrationActivity;

public class CheckuserActivity extends AppCompatActivity {

    Button checkBtnlogin,checkBtnsignup;
    ImageView checkLogo;
    Animation animation,animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkuser);

        checkBtnlogin=findViewById(R.id.checkBtnlogin);
        checkLogo=findViewById(R.id.checkLogo);
        checkBtnsignup=findViewById(R.id.checkBtnsignup);



        checkBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button dialogBtnjobseeker,dialogBtnemployee;
                final Dialog dialog=new Dialog(CheckuserActivity.this);
                dialog.setContentView(R.layout.dialog_checkuser);
                dialogBtnjobseeker=dialog.findViewById(R.id.dialogBtnjobseeker);
                dialogBtnemployee=dialog.findViewById(R.id.dialogBtnemployee);

                dialogBtnjobseeker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(CheckuserActivity.this,JobseekerloginActivity.class));
                        finish();
                        dialog.dismiss();
                    }
                });

                dialogBtnemployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(CheckuserActivity.this,CompanyloginActivity.class));
                        finish();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        checkBtnsignup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Button dialogBtnjobseeker,dialogBtnemployee;
                TextView dialog_title;
                final Dialog dialog=new Dialog(CheckuserActivity.this);
                dialog.setContentView(R.layout.dialog_checkuser);
                dialogBtnjobseeker=dialog.findViewById(R.id.dialogBtnjobseeker);
                dialogBtnemployee=dialog.findViewById(R.id.dialogBtnemployee);
                dialog_title=dialog.findViewById(R.id.dialogTitle);

                dialog_title.setText("Sign up as");

                dialogBtnjobseeker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(CheckuserActivity.this,JobseekerregistrationActivity.class));
                        finish();
                        dialog.dismiss();
                    }
                });

                dialogBtnemployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(CheckuserActivity.this,CompanyregistrationActivity.class));
                        finish();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
