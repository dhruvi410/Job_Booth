package com.example.dhruvi.job.jobseeker.subcategoryrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subcategory extends AppCompatActivity {

    RecyclerView subcategoryrv;
    AdapterSub adapterCategory;
    ArrayList<String> title, img, key, value;
    ArrayList<Beansub> cb;
    CallServices cs;
    Intent i;
    String catname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        subcategoryrv = findViewById(R.id.subcategoryrv);

        title = new ArrayList<>();
        img = new ArrayList<>();
        cb = new ArrayList<>();
        cs = new CallServices();
        key = new ArrayList<>();
        value = new ArrayList<>();
        i = getIntent();
        catname = i.getStringExtra("catname");

        Toast.makeText(this, ""+catname, Toast.LENGTH_SHORT).show();
        key.add("catname");
        value.add(catname);

        String res = cs.CallServices(Subcategory.this, Url.URL + "sub.php", Url.METHOD, key, value);
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
                String subname = c.getString("subname");
                String subimage = c.getString("subimage");
                // add it in arraylist
                Beansub cab = new Beansub(subname, subimage);
                cb.add(cab);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterCategory = new AdapterSub(Subcategory.this, cb);

        subcategoryrv.setLayoutManager(new GridLayoutManager(Subcategory.this, 2));

        subcategoryrv.setAdapter(adapterCategory);
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
