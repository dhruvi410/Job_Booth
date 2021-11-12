package com.example.dhruvi.job.jobseeker.applied;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppliedJob extends AppCompatActivity {

    RecyclerView appliedjobrv;
    AdapterApplied adapter;
    CallServices cs;
    ArrayList<String> key,value;
    ArrayList<AppliedBean> bean;
    String res,email;
    Session s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        appliedjobrv=findViewById(R.id.appliedjobrv);
        key=new ArrayList<>();
        value=new ArrayList<>();
        s=new Session(AppliedJob.this);
        email=s.checkLogin();
        bean=new ArrayList<>();
        cs=new CallServices();
        key.add("email");
        value.add(email);
        res=cs.CallServices(AppliedJob.this, Url.URL+"getappliedpost.php",Url.METHOD,key,value);

        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(res);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                // get the value from c
                String strtitle = c.getString("title");
                key.clear();
                value.clear();
                key.add("title");
                value.add(strtitle);
                String compname = cs.CallServices(AppliedJob.this,Url.URL+"getcompanynameviatitle.php",Url.METHOD,key,value);
                String compimg ;
                key.clear();
                value.clear();
                //Log.e("jiii","heloo");
                key.add("title");
                value.add(strtitle);
                //Log.e("jiiii",strtitle);
                compimg=cs.CallServices(AppliedJob.this,Url.URL+"getcompanytitlewithimage.php",Url.METHOD,key,value);
                //Log.e("Image---",compimg);
                // add it in arraylist
                AppliedBean cab = new AppliedBean(compimg,strtitle,compname);
                bean.add(cab);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new AdapterApplied(AppliedJob.this,bean);

        appliedjobrv.setLayoutManager(new LinearLayoutManager(AppliedJob.this));

        appliedjobrv.setAdapter(adapter);
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
