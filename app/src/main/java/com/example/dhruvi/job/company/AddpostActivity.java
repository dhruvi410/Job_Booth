package com.example.dhruvi.job.company;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddpostActivity extends AppCompatActivity {

    EditText titlepost,quantity,expertpost,skillpost,notepost,bypost,salarypost;
    Button addpost;
    Toolbar toolbar;
    Spinner catpost,subcatpost;
    CallServices cs,cs2,cs3,cs4;
    ArrayList<String> key;
    ArrayList<String> value;
    ArrayList<String> sendkey;
    ArrayList<String> sendvalue;
    ArrayList<String> catname;
    ArrayList<String> subname;
    String strcatname;
    String strsubcatname;
    String stremail;
    String strtitle;
    String stremployeeno,img,compname,salary;
    String strexpert;
    String strskill;
    String strnotes;
    String strname;
    ArrayList<String> inkey;
    ArrayList<String> invalue;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String response;
    Session s;
    ArrayList<String> ikey,ivalue;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        titlepost=findViewById(R.id.titlepost);
        salarypost=findViewById(R.id.salarypost);
        quantity=findViewById(R.id.quantity);
        expertpost=findViewById(R.id.expertpost);
        skillpost=findViewById(R.id.skillpost);
        notepost=findViewById(R.id.notepost);
        bypost=findViewById(R.id.bypost);
        addpost=findViewById(R.id.addpost);
        catpost=findViewById(R.id.catpost);
        subcatpost=findViewById(R.id.subcatpost);
        s=new Session(AddpostActivity.this);
        cs = new CallServices();
        key=new ArrayList<>();
        ikey=new ArrayList<>();
        ivalue=new ArrayList<>();
        value=new ArrayList<>();
        catname=new ArrayList<>();
        sendkey=new ArrayList<>();
        sendvalue=new ArrayList<>();
        cs2=new CallServices();
        subname=new ArrayList<>();
        inkey = new ArrayList<>();
        invalue = new ArrayList<>();
        cs3 = new CallServices();
        stremail=s.checkLogin();

        String res = cs.CallServices(AddpostActivity.this, Url.URL+"catselect.php",Url.METHOD,key,value);

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
                String catname = c.getString("catname");
                // add it in arraylist
                this.catname.add(catname);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        catname); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.notifyDataSetChanged();
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catpost.setAdapter(spinnerArrayAdapter);





        catpost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strcatname = catpost.getSelectedItem().toString();
                Log.e("cat",strcatname);
                sendkey.add("catname");
                sendvalue.add(strcatname);
                String res2 = cs2.CallServices(AddpostActivity.this,Url.URL+"sub.php",Url.METHOD,sendkey,sendvalue);
                subname.clear();

                try {
                    // intialize json object and give json object the reference of array of object
                    JSONObject jsonObject=new JSONObject(res2);
                    // fetch array from json object
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    // loop for get the value from jsonarray one by one
                    for(int i2=0;i2<=jsonArray.length();i2++)
                    {
                        // create another json object as in jsonarray there are lots of object so to get specific position object from array
                        JSONObject c=jsonArray.getJSONObject(i2);
                        // get the value from c
                        String strsubname = c.getString("subname");
                        // add it in arraylist
                        subname.add(strsubname);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>
                        (AddpostActivity.this, android.R.layout.simple_spinner_item,
                                subname); //selected item will look like a spinner set from XML
                spinnerArrayAdapter2.notifyDataSetChanged();
                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                subcatpost.setAdapter(spinnerArrayAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        subcatpost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strsubcatname =subcatpost.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ikey.add("email");
        ivalue.add(stremail);
        cs4=new CallServices();
        img=cs4.CallServices(AddpostActivity.this,Url.URL+"getimagecompany.php",Url.METHOD,ikey,ivalue);
        ikey.clear();
        ivalue.clear();
        ikey.add("email");
        ivalue.add(stremail);
        compname=cs.CallServices(AddpostActivity.this,Url.URL+"getcompanyname.php",Url.METHOD,ikey,ivalue);


        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inkey = new ArrayList<>();
                invalue = new ArrayList<>();

                strtitle=titlepost.getText().toString();
                stremployeeno=quantity.getText().toString();
                strexpert=expertpost.getText().toString();
                strskill=skillpost.getText().toString();
                strnotes=notepost.getText().toString();
                strname=bypost.getText().toString();
                salary=salarypost.getText().toString();

                inkey.add("email");
                inkey.add("compname");
                inkey.add("catname");
                inkey.add("subname");
                inkey.add("title");
                inkey.add("salary");
                inkey.add("img");
                inkey.add("quantity");
                inkey.add("expert");
                inkey.add("skill");
                inkey.add("notes");
                inkey.add("postby");
                inkey.add("date");

                invalue.add(stremail);
                invalue.add(compname);
                invalue.add(strcatname);
                invalue.add(strsubcatname);
                invalue.add(strtitle);
                invalue.add(salary);
                invalue.add(img);
                invalue.add(stremployeeno);
                invalue.add(strexpert);
                invalue.add(strskill);
                invalue.add(strnotes);
                invalue.add(strname);
                invalue.add(date);

                response=cs3.CallServices(AddpostActivity.this,Url.URL+"insertpost.php",Url.METHOD,inkey,invalue);
                Log.e("res",response);
                if(response.trim().equals("1"))
                {
                    Toast.makeText(AddpostActivity.this, "Your post has been added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(response.trim().equals("0"))
                {
                    Toast.makeText(AddpostActivity.this, "Not yet inserted", Toast.LENGTH_SHORT).show();
                }


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
