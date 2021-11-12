package com.example.dhruvi.job.jobseeker.categoryrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category extends AppCompatActivity {

    RecyclerView categoryrv;
    AdapterCategory adapterCategory;
    ArrayList<String> title,img,key,value;
    ArrayList<BeanCat> cb;
    CallServices cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        categoryrv=findViewById(R.id.categoryrv);
        title=new ArrayList<>();
        img=new ArrayList<>();
        cb=new ArrayList<>();
        cs=new CallServices();
        key=new ArrayList<>();
        value=new ArrayList<>();

        String res=cs.CallServices(Category.this, Url.URL+"catselect.php",Url.METHOD,key,value);
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
                String catname=c.getString("catname");
                String catimage=c.getString("catimage");
                // add it in arraylist
                BeanCat cab = new BeanCat(catname,catimage);
                cb.add(cab);
                /*title.add(strtitle);
                email.add(stremail);
                date.add(strdate);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterCategory=new AdapterCategory(Category.this,cb);

        categoryrv.setLayoutManager(new GridLayoutManager(Category.this,2));

        categoryrv.setAdapter(adapterCategory);
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
