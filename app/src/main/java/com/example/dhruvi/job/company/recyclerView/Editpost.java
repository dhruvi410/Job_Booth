package com.example.dhruvi.job.company.recyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.dhruvi.job.Url;
import com.example.dhruvi.job.company.CompanydashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Editpost extends AppCompatActivity {

    Intent i;
    Toolbar toolbar;
    String title;
    Spinner editcatpost,editsubcatpost;
    ArrayList<String> catkey,catvalue,subkey,subvalue,catname,subname,ikey,ivalue,inkey,invalue;
    CallServices cs;
    String catres,strcatname,subres,strsubcatname,mainres,strtitle,stremployeeno,strexpert,strskill,strnotes,strname,mainres2,response,salary;
    EditText editposttitle,editpostquantity,editpostexpert,editpostskill,editpostnotes,editpostpostby,editpostsalary;
    Button editpostbtn;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpost);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        i=getIntent();
        title=i.getStringExtra("title");
        editcatpost=findViewById(R.id.editcatpost);
        editsubcatpost=findViewById(R.id.editsubcatpost);
        editpostsalary=findViewById(R.id.editpostsalary);
        catkey=new ArrayList<>();
        subname=new ArrayList<>();
        inkey=new ArrayList<>();
        invalue=new ArrayList<>();
        ikey=new ArrayList<>();
        ivalue=new ArrayList<>();
        catvalue=new ArrayList<>();
        subkey=new ArrayList<>();
        subvalue=new ArrayList<>();
        catname=new ArrayList<>();
        cs=new CallServices();
        editposttitle=findViewById(R.id.editposttitle);
        editpostquantity=findViewById(R.id.editpostquantity);
        editpostexpert=findViewById(R.id.editpostexpert);
        editpostskill=findViewById(R.id.editpostskill);
        editpostnotes=findViewById(R.id.editpostnotes);
        editpostpostby=findViewById(R.id.editpostpostby);
        editpostbtn=findViewById(R.id.editpostbtn);

        catres = cs.CallServices(Editpost.this, Url.URL+"catselect.php",Url.METHOD,catkey,catvalue);

        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject=new JSONObject(catres);
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
        editcatpost.setAdapter(spinnerArrayAdapter);





        editcatpost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strcatname = editcatpost.getSelectedItem().toString();
                Log.e("cat",strcatname);
                subkey.add("catname");
                subvalue.add(strcatname);
                subres = cs.CallServices(Editpost.this,Url.URL+"sub.php",Url.METHOD,subkey,subvalue);
                subname.clear();

                try {
                    // intialize json object and give json object the reference of array of object
                    JSONObject jsonObject=new JSONObject(subres);
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
                        (Editpost.this, android.R.layout.simple_spinner_item,
                                subname); //selected item will look like a spinner set from XML
                spinnerArrayAdapter2.notifyDataSetChanged();
                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                editsubcatpost.setAdapter(spinnerArrayAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        editsubcatpost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strsubcatname =editsubcatpost.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ikey.add("title");
        ivalue.add(title);

        response=cs.CallServices(Editpost.this,Url.URL+"getpost.php",Url.METHOD,ikey,ivalue);
        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(response);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                editposttitle.append(title);
                editpostquantity.setText(c.getString("quantity"));
                editpostexpert.setText(c.getString("expert"));
                editpostskill.setText(c.getString("skill"));
                editpostnotes.setText(c.getString("notes"));
                editpostpostby.setText(c.getString("postby"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        editpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inkey = new ArrayList<>();
                invalue = new ArrayList<>();

                strtitle=editposttitle.getText().toString();
                stremployeeno=editpostquantity.getText().toString();
                strexpert=editpostexpert.getText().toString();
                strskill=editpostskill.getText().toString();
                strnotes=editpostnotes.getText().toString();
                strname=editpostpostby.getText().toString();
                salary=editpostsalary.getText().toString();


                inkey.add("oldtitle");
                inkey.add("catname");
                inkey.add("subname");
                inkey.add("title");
                inkey.add("salary");
                inkey.add("quantity");
                inkey.add("expert");
                inkey.add("skill");
                inkey.add("notes");
                inkey.add("postby");
                inkey.add("date");


                invalue.add(title);
                invalue.add(strcatname);
                invalue.add(strsubcatname);
                invalue.add(strtitle);
                invalue.add(salary);
                invalue.add(stremployeeno);
                invalue.add(strexpert);
                invalue.add(strskill);
                invalue.add(strnotes);
                invalue.add(strname);
                invalue.add(date);

                mainres2=cs.CallServices(Editpost.this,Url.URL+"updatepost.php",Url.METHOD,inkey,invalue);
                Log.e("res",mainres2);
                if(mainres2.trim().equals("1"))
                {
                    Toast.makeText(Editpost.this, "Your post has been changed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Editpost.this,CompanydashboardActivity.class));
                }
                else if(mainres2.trim().equals("0"))
                {
                    Toast.makeText(Editpost.this, "Not yet inserted", Toast.LENGTH_SHORT).show();
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
