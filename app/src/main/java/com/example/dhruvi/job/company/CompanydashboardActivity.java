package com.example.dhruvi.job.company;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.dhruvi.job.company.recyclerView.AdapterCompanyDashboard;
import com.example.dhruvi.job.company.recyclerView.CategoryBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanydashboardActivity extends AppCompatActivity {

    RecyclerView companyDashboardRV;
    AdapterCompanyDashboard adapter;
    ArrayList<String> title;
    ArrayList<String> email;
    ArrayList<String> date;
    ArrayList<String> postby;
    ArrayList<String> key;
    ArrayList<String> value;
    ArrayList<CategoryBean> cb;
    CallServices cs;
    FloatingActionButton companyDashboardFAB;
    RatingBar ratecomp;
    Session s;
    Dialog d;
    Button btn;
    EditText ed;
    String feed,rate;
    String stremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companydashboard);


        companyDashboardRV = findViewById(R.id.companyDashboardRV);
        title = new ArrayList<>();
        email = new ArrayList<>();
        date = new ArrayList<>();
        postby = new ArrayList<>();
        key = new ArrayList<>();
        value = new ArrayList<>();
        cb = new ArrayList<>();
        companyDashboardFAB = findViewById(R.id.companyDashboardFAB);
        s = new Session(CompanydashboardActivity.this);
        stremail=s.checkLogin();
        key.add("email");
        value.add(stremail);
        cs = new CallServices();
        String res = cs.CallServices(CompanydashboardActivity.this, Url.URL + "select.php", Url.METHOD, key, value);
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
                //stremail = c.getString("email");
                String strdate = c.getString("date");
                String strpostby = c.getString("postby");
                String salary=c.getString("salary");
                String email=c.getString("email");
                // add it in arraylist
                CategoryBean cab = new CategoryBean("1", strtitle, "girl.jpg", strdate, strpostby,salary,email);
                cb.add(cab);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        companyDashboardRV.setItemAnimator(itemAnimator);
        adapter = new AdapterCompanyDashboard(CompanydashboardActivity.this, cb);

        companyDashboardRV.setLayoutManager(new LinearLayoutManager(CompanydashboardActivity.this));

        companyDashboardRV.setAdapter(adapter);

        companyDashboardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanydashboardActivity.this, AddpostActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @SuppressLint("ResourceType")
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.feedback:

                d=new Dialog(CompanydashboardActivity.this);
                d.setContentView(R.layout.companyfeedback);
                ed=d.findViewById(R.id.ed);
                ratecomp=d.findViewById(R.id.ratecomp);
                btn=d.findViewById(R.id.btn);
                d.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        feed=ed.getText().toString();
                        rate= String.valueOf(ratecomp.getRating());
                        key.clear();
                        value.clear();
                        key.add("email");
                        key.add("feed");
                        key.add("rate");
                        value.add(stremail);
                        value.add(feed);
                        value.add(rate);
                        String rr=cs.CallServices(CompanydashboardActivity.this,Url.URL+"compfeed.php",Url.METHOD,key,value);
                        if(rr.equals("1"))
                        {Toast.makeText(CompanydashboardActivity.this, "Feedback given", Toast.LENGTH_SHORT).show();
                            d.dismiss();}
                        else
                        {Toast.makeText(CompanydashboardActivity.this, "Feedback not reached", Toast.LENGTH_SHORT).show();
                            d.dismiss();}
                    }
                });
                break;
            case R.id.logout:
                s.logout();
                startActivity(new Intent(CompanydashboardActivity.this, CheckuserActivity.class));
                finish();
                break;
            case R.id.editprofile:
                startActivity(new Intent(CompanydashboardActivity.this,EditprofileCompany.class));
                break;
            case R.id.assign:
                startActivity(new Intent(CompanydashboardActivity.this,AcceptedEmployee.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
