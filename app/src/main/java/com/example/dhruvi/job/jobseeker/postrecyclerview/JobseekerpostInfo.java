package com.example.dhruvi.job.jobseeker.postrecyclerview;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.FilePath;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.makeText;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class JobseekerpostInfo extends AppCompatActivity {

    CircleImageView postinfoimg;
    TextView postinfotitle, postinfoemail, postinfovacancy, postinfoskill, postinfoby;
    Button postinfoapply, chooseresume;
    Intent i;
    Session s;
    static String unm;
    String title, name, no, path, email, image, compimg, compname;
    ArrayList<String> key, value, key2, value2, key3, value3;
    CallServices cs, cs2, cs3;
    EditText pdfname;
    public static final String UPLOAD_URL = Url.URL + "uploadpdf.php";
    private int PICK_PDF_REQUEST = 1;
    public static final int STORAGE_PERMISSION_CODE = 123;
    private Uri filePath;
    Context context = JobseekerpostInfo.this;
    Dialog dialog;
    Boolean status = false;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekerpost_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //requestStoragePermission();

        postinfoimg = findViewById(R.id.postinfoimg);
        postinfotitle = findViewById(R.id.postinfotitle);
        postinfoemail = findViewById(R.id.postinfoemail);
        postinfovacancy = findViewById(R.id.postinfovacancy);
        postinfoskill = findViewById(R.id.postinfoskill);
        s = new Session(JobseekerpostInfo.this);
        postinfoby = findViewById(R.id.postinfoby);
        postinfoapply = findViewById(R.id.postinfoapply);
        chooseresume = findViewById(R.id.chooseresume);

        email = s.checkLogin();
        key = new ArrayList<>();
        value = new ArrayList<>();
        cs = new CallServices();
        cs2 = new CallServices();
        cs3 = new CallServices();
        key2 = new ArrayList<>();
        key3 = new ArrayList<>();
        value2 = new ArrayList<>();
        value3 = new ArrayList<>();
        s = new Session(JobseekerpostInfo.this);
        i = getIntent();
        dialog = new Dialog(context);


        title = i.getStringExtra("title");

        key.add("title");


        value.add(title);

        cs = new CallServices();
        String res = cs.CallServices(this, Url.URL + "jobseekerpostinfo.php", Url.METHOD, key, value);
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
                Glide.with(JobseekerpostInfo.this).load(Url.URL + "companydb/" + c.getString("image")).into(postinfoimg);
                title = c.getString("title");
                postinfotitle.setText(title);
                postinfoemail.append(c.getString("email"));
                postinfovacancy.append(c.getString("quantity"));
                postinfoskill.append(c.getString("skill"));
                postinfoby.append(c.getString("postby"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        unm = s.checkLogin();
        key3.add("unm");
        value3.add(unm);

        String response = cs3.CallServices(this, Url.URL + "jobseekeruserinfo.php", Url.METHOD, key3, value3);
        Log.e("email=====", unm);
        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(response);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                // get the value from c
                name = c.getString("name");
                no = c.getString("mobile");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        chooseresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
                // postinfoapply.setEnabled(true);
            }
        });
        key.clear();
        value.clear();
        key.add("email");
        value.add(email);
        image = cs.CallServices(JobseekerpostInfo.this, Url.URL + "getjobseekerimage.php", Url.METHOD, key, value);

        key.clear();
        value.clear();
        key.add("title");
        value.add(title);
        String ves = cs.CallServices(JobseekerpostInfo.this, Url.URL + "getcominfo.php", Url.METHOD, key, value);
        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(ves);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                // get the value from c
                compname = c.getString("email");
                no = c.getString("mobile");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        postinfoapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (status)
                    uploadMultipart();
                key2.add("title");
                key2.add("name");
                key2.add("image");
                key2.add("email");
                key2.add("no");

                value2.add(title);
                value2.add(name);
                value2.add(image);
                value2.add(unm);
                value2.add(no);
                cs2 = new CallServices();
                String aa = cs2.CallServices(JobseekerpostInfo.this, Url.URL + "jobseekerapply.php", Url.METHOD, key2, value2);
                if (aa.equals("1")) {
                    Toast.makeText(JobseekerpostInfo.this, "Applied succefull", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(JobseekerpostInfo.this, "Applied fail", Toast.LENGTH_SHORT).show();

            }


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            status = true;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    public void uploadMultipart() {
        //getting name for the image


        //getting the actual path of the image
        path = FilePath.getPath(JobseekerpostInfo.this, filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request

            new MultipartUploadRequest(this, uploadId, Url.URL + "uploadpdf.php")
                    .addParameter("email", email)
                    .addFileToUpload(path, "image") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            makeText(this, "Success", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            makeText(this, "Error==" + exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


}

