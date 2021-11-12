package com.example.dhruvi.job.company.applierrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppliersInfoActivity extends AppCompatActivity {

    Intent intent;
    String strappliername,title,compemail,stremail,strname,img,straccept="true",compimage,compname;
    CircleImageView applierimg;
    ImageView applierresume;
    TextView appliername,applieremail,applierno,applierskill;
    CallServices cs;
    String res;
    ArrayList<String> key;
    ArrayList<String> value;
    Button accept,decline;
    Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appliers_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        intent = getIntent();
        strappliername= intent.getStringExtra("appliername");

        applierimg = findViewById(R.id.applierimg);
        appliername = findViewById(R.id.appliername);
        applieremail = findViewById(R.id.applieremail);
        applierno = findViewById(R.id.applierno);
        applierresume = findViewById(R.id.applierresume);
        applierskill = findViewById(R.id.applierskill);
        accept=findViewById(R.id.accept);
        decline=findViewById(R.id.decline);
        key=new ArrayList<>();
        value=new ArrayList<>();
        s=new Session(AppliersInfoActivity.this);
        cs = new CallServices();
        compemail=s.checkLogin();
        key.add("name");
        value.add(strappliername);
        res = cs.CallServices(AppliersInfoActivity.this, Url.URL+"selectapplierinfo.php",Url.METHOD,key,value);

        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject=new JSONObject(res);
            // fetch array from json object
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for(int i=0;i<=jsonArray.length();i++)
            {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c=jsonArray.getJSONObject(i);
                // get the value from c
                 strname = c.getString("name");
                 stremail = c.getString("email");
                String strno = c.getString("no");
                String strskill = c.getString("skill");
                img = c.getString("img");
                title=c.getString("title");
                Log.e("img",img);
                // add it in arraylist
               appliername.append(strname);
               applieremail.append(stremail);
               applierno.append(strno);
               applierskill.append(strskill);
                Glide.with(AppliersInfoActivity.this).load(Url.URL+"jobdp/"+img).into(applierimg);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        key.add("email");
        value.add(compemail);
        compimage=cs.CallServices(AppliersInfoActivity.this,Url.URL+"getimagecompany.php",Url.METHOD,key,value);
        compname=cs.CallServices(AppliersInfoActivity.this,Url.URL+"getcompanyname.php",Url.METHOD,key,value);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key.clear();
                value.clear();


                key.add("accept");
                key.add("title");
                key.add("email");
                key.add("compemail");
                key.add("compimage");
                key.add("compname");
                value.add(straccept);
                value.add(title);
                value.add(stremail);
                value.add(compemail);
                value.add(compimage);
                value.add(compname);

                String res = cs.CallServices(AppliersInfoActivity.this, Url.URL+"acceptapplier.php", Url.METHOD, key, value);
                Log.e("resss",res);
                if (res.equals("1"))
                {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{stremail});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Congratulation you get appoinment");
                    i.putExtra(Intent.EXTRA_TEXT   , "You get the appoinment from your choice company.\n Thank you for using our application Job Booth");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(AppliersInfoActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(AppliersInfoActivity.this, "You assigned this employee", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else if(res.equals("0"))
                {
                    Toast.makeText(AppliersInfoActivity.this, "Your request will be assigned soon...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key.clear();
                value.clear();
                key.add("accept");
                key.add("title");
                key.add("email");
                value.add("false");
                value.add(title);
                value.add(stremail);
                String ro=cs.CallServices(AppliersInfoActivity.this,Url.URL+"decline.php",Url.METHOD,key,value);
                if(ro.equals("1"))
                {
                    Toast.makeText(AppliersInfoActivity.this, "You rejected this user", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(AppliersInfoActivity.this, "Your request of rejection of this user is declined", Toast.LENGTH_SHORT).show();
            }
        });

        applierresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AppliersInfoActivity.this,ShowResume.class);
                i.putExtra("email",stremail);
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
