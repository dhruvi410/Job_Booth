package com.example.dhruvi.job.company.applierrecyclerview;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class AppliersActivity extends AppCompatActivity {

    Intent intent;
    String title;
    RecyclerView appliersActivityRV;
    AdapterApplier adapterApplier;
    ArrayList<String> getkey;
    ArrayList<String> getvalue;
    ArrayList<String> img;
    ArrayList<String> name;
    ArrayList<BeanAppliers> ba;

    CallServices cs,cs2;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliers);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        intent=getIntent();
        title=intent.getStringExtra("title");
        Log.e("dddd",""+title);
        appliersActivityRV=findViewById(R.id.appliersActivityRV);

        getkey=new ArrayList<>();
        getvalue=new ArrayList<>();
        img=new ArrayList<>();
        name=new ArrayList<>();
        ba=new ArrayList<>();

        getkey.add("title");
        getvalue.add(title);
        cs2=new CallServices();

        cs=new CallServices();

        String res=cs.CallServices(AppliersActivity.this, Url.URL+"applyselect.php",Url.METHOD,getkey,getvalue);
        Log.e("aaaa",res);
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
                String strname = c.getString("name");
                String strimg = c.getString("img");
                String stremail=c.getString("email");
                String strtitle=c.getString("title");
                String strid=c.getString("appliersid");
                String strseen = c.getString("seen");
                Log.e("name",strname);
                // add it in arraylist-
                BeanAppliers bea = new BeanAppliers(strname,strimg,stremail,strtitle,strid,strseen);
                ba.add(bea);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        adapterApplier=new AdapterApplier(AppliersActivity.this,ba);
        appliersActivityRV.setLayoutManager(new LinearLayoutManager(AppliersActivity.this));
        appliersActivityRV.setAdapter(adapterApplier);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // return super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
