package com.example.dhruvi.job.company;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
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

import static com.example.dhruvi.job.Url.isEmailValid;

public class CompanyloginActivity extends AppCompatActivity {

    EditText companyloginunm, companyloginpwd;
    TextView compfrgtpwd;
    Button companyloginbtn;
    ImageView companyimg;
    CardView complogincv;
    String email, pwd;
    ArrayList<String> key;
    ArrayList<String> value;
    CallServices cs;
    Animation animation,animation1;
    Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companylogin);


        companyloginunm = findViewById(R.id.companyloginunm);
        compfrgtpwd = findViewById(R.id.compfrgtpwd);
        companyimg = findViewById(R.id.companyimg);
        complogincv = findViewById(R.id.complogincv);
        companyloginpwd = findViewById(R.id.companyloginpwd);
        companyloginbtn = findViewById(R.id.companyloginbtn);
        key=new ArrayList<>();
        value=new ArrayList<>();
        cs=new CallServices();

        animation= AnimationUtils.loadAnimation(CompanyloginActivity.this,R.anim.fadein);
        animation1= AnimationUtils.loadAnimation(CompanyloginActivity.this,R.anim.bottomtotop);
        s=new Session(CompanyloginActivity.this);

        companyimg.setAnimation(animation);
        complogincv.setAnimation(animation1);


        compfrgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(CompanyloginActivity.this);
                final EditText ansfrcomp,newpfrcomp;
                Button btnfrcomp;
                d.setContentView(R.layout.forgetpasscomp);
                ansfrcomp=d.findViewById(R.id.ansfrcomp);
                newpfrcomp=d.findViewById(R.id.newpfrcomp);
                btnfrcomp=d.findViewById(R.id.btnfrcomp);
                btnfrcomp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key.clear();
                        value.clear();
                        key.add("email");
                        key.add("ans");
                        key.add("new");
                        value.add(companyloginunm.getText().toString());
                        value.add(ansfrcomp.getText().toString());
                        value.add(newpfrcomp.getText().toString());
                        String res = cs.CallServices(CompanyloginActivity.this,Url.URL+"forgetpasscomp.php",Url.METHOD,key,value);
                        if(res.equals("1"))
                        {
                            Toast.makeText(CompanyloginActivity.this, "Changed succesfully", Toast.LENGTH_SHORT).show();
                            d.dismiss();
                        }
                        else if (res.equals("0"))
                        {
                            Toast.makeText(CompanyloginActivity.this, "Not changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                d.show();
            }
        });

            companyloginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cs = new CallServices();
                    //initialize key and value arraylist
                    key = new ArrayList<>();
                    value = new ArrayList<>();

                    // get values from edittext in string object
                    s = new Session(CompanyloginActivity.this);
                    email = companyloginunm.getText().toString();
                    pwd = companyloginpwd.getText().toString();

                    if (isEmailValid(email)) {

                        // give value to php script
                        key.add("email");
                        key.add("password");
                        value.add(email);
                        value.add(pwd);

                        String res = cs.CallServices(CompanyloginActivity.this, Url.URL + "login.php", Url.METHOD, key, value);
                        Log.e("aaaaaaaaaaa", res);
                        if (res.trim().equals("1")) {
                            s.setLogin(email);
                            s.setPwd(pwd);
                            s.setType("comp");

                            startActivity(new Intent(CompanyloginActivity.this, CompanydashboardActivity.class));
                            finish();
                        } else if (res.trim().equals("0")) {
                            companyloginpwd.getText().clear();
                            Toast.makeText(CompanyloginActivity.this, "Wrong username and password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(CompanyloginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        companyloginunm.getText().clear();
                    }
                }
            });
            companyloginunm.getText().clear();
        }

    }


