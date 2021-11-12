package com.example.dhruvi.job.jobseeker.postrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobseekerviewpostActivity extends AppCompatActivity {


    String strsubcat;
    RecyclerView rvpost;
    AdapterJobseekerPost adapter;
    ArrayList<String> title;
    ArrayList<String> email;
    ArrayList<String> date;
    ArrayList<String> postby;
    ArrayList<String> key;
    ArrayList<String> value;
    ArrayList<Beanjob> cb;
    CallServices cs;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekerviewpost);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rvpost=findViewById(R.id.rvpost);
        title=new ArrayList<>();
        email=new ArrayList<>();
        date=new ArrayList<>();
        postby=new ArrayList<>();
        key=new ArrayList<>();
        value=new ArrayList<>();
        cb=new ArrayList<>();
        i=getIntent();
        strsubcat=i.getStringExtra("subname");
        Toast.makeText(this, ""+strsubcat, Toast.LENGTH_SHORT).show();
        key.add("strsubcat");


        value.add(strsubcat);

        cs=new CallServices();
        String res=cs.CallServices(this, Url.URL+"jobpostselect.php",Url.METHOD,key,value);
        Log.e("res=====",res);
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
                String strtitle = c.getString("title");
                String stremail = c.getString("email");
                String strdate = c.getString("date");
                String strpostby = c.getString("postby");
                String img=c.getString("image");
                String salary=c.getString("salary");
                String pid=c.getString("pid");
                String seen=c.getString("seen");
                // add it in arraylist
                Beanjob cab = new Beanjob(strtitle,img,strdate,strpostby,stremail,salary,pid,seen);
                cb.add(cab);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new AdapterJobseekerPost(JobseekerviewpostActivity.this,cb);

        rvpost.setLayoutManager(new LinearLayoutManager(JobseekerviewpostActivity.this));

        rvpost.setAdapter(adapter);




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
