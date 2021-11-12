package com.example.dhruvi.job.company;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;
import com.example.dhruvi.job.company.acceptedemployee.AcceptedBean;
import com.example.dhruvi.job.company.acceptedemployee.AdapterAcceptedEmployee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AcceptedEmployee extends AppCompatActivity {

    RecyclerView acceptedrv;
    AdapterAcceptedEmployee adapter;
    ArrayList<AcceptedBean> bean;
    CallServices cs;
    ArrayList<String> key,value;
    String res,email;
    Session s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_employee);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        acceptedrv=findViewById(R.id.acceptedrv);
        s=new Session(AcceptedEmployee.this);
        key=new ArrayList<>();
        value=new ArrayList<>();
        bean=new ArrayList<>();
        cs=new CallServices();

        email=s.checkLogin();

        key.add("email");
        value.add(email);
        res=cs.CallServices(AcceptedEmployee.this, Url.URL+"acceptedapplierinfo.php",Url.METHOD,key,value);
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
                String name = c.getString("name");
                String img = c.getString("img");
                // add it in arraylist
                AcceptedBean cab = new AcceptedBean(name,strtitle,img);
                bean.add(cab);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter=new AdapterAcceptedEmployee(AcceptedEmployee.this,bean);

        acceptedrv.setLayoutManager(new LinearLayoutManager(AcceptedEmployee.this));

        acceptedrv.setAdapter(adapter);
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
