package com.example.dhruvi.job.jobseeker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.PermissionManager;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.makeText;
import static com.example.dhruvi.job.jobseeker.postrecyclerview.JobseekerpostInfo.STORAGE_PERMISSION_CODE;

public class JobseekerregistrationActivity extends AppCompatActivity {

    EditText jobseekerregistration_email, jobseekerregistration_password, jobseekerregistration_name, jobseekerregistration_mobile, jobseekerregistration_exp, jobseekerregistration_city,jobseekerregistration_ans;
    RadioGroup jobseekerregistration_rdgrp;
    Button jobseekerregistration_date, jobseekerregistration_btn;
    CircleImageView jobseekerregistration_logo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<String> key, value, catname, subcatname, sendkey, sendvalue, catkey, catvalue;
    Uri filePath;
    CallServices cs, cs2, cs3;
    public static String email;
    static String password;
    static String name;
    static String mobile;
    static String exp;
    static String city;
    static String gender;
    static String cat;
    static String subcat;
    static String date,ans;
    Bitmap bitmap;
    String path;
    Boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseekerregistration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        jobseekerregistration_email = findViewById(R.id.jobseekerregistration_email);
        jobseekerregistration_password = findViewById(R.id.jobseekerregistration_password);
        jobseekerregistration_ans = findViewById(R.id.jobseekerregistration_ans);
        jobseekerregistration_name = findViewById(R.id.jobseekerregistration_name);
        jobseekerregistration_mobile = findViewById(R.id.jobseekerregistration_mobile);
        jobseekerregistration_exp = findViewById(R.id.jobseekerregistration_exp);
        jobseekerregistration_city = findViewById(R.id.jobseekerregistration_city);
        jobseekerregistration_rdgrp = findViewById(R.id.jobseekerregistration_rdgrp);
        jobseekerregistration_date = findViewById(R.id.jobseekerregistration_date);
        jobseekerregistration_btn = findViewById(R.id.jobseekerregistration_btn);
        jobseekerregistration_logo = findViewById(R.id.jobseekerregistration_logo);
        subcatname = new ArrayList<>();

        jobseekerregistration_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(JobseekerregistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        jobseekerregistration_rdgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                switch (i) {
                    case R.id.jobseekerregistration_rdmale:
                        gender = "male";
                        break;
                    case R.id.jobseekerregistration_rdfemale:
                        gender = "female";
                        break;
                    default:
                        Toast.makeText(JobseekerregistrationActivity.this, "Please choose the gender", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        key = new ArrayList<>();
        value = new ArrayList<>();

        jobseekerregistration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = jobseekerregistration_email.getText().toString();
                password = jobseekerregistration_password.getText().toString();
                name = jobseekerregistration_name.getText().toString();
                mobile = jobseekerregistration_mobile.getText().toString();
                exp = jobseekerregistration_exp.getText().toString();
                city = jobseekerregistration_city.getText().toString();
                ans = jobseekerregistration_ans.getText().toString();
                //gender=jobseekerregistration_email.getText().toString();
                //cat=jobseekerregistration_email.getText().toString();
                //subcat=jobseekerregistration_email.getText().toString();
                if(ans.equals(""))
                    Toast.makeText(JobseekerregistrationActivity.this, "Please complete form", Toast.LENGTH_SHORT).show();
                else{
                if (Url.isEmailValid(email)) {
                    key.add("email");
                    key.add("password");
                    key.add("name");
                    key.add("mobile");
                    key.add("gender");
                    key.add("dob");
                    key.add("experince");
                    key.add("catname");
                    key.add("subname");
                    key.add("city");
                    key.add("ans");

                    value.add(email);
                    value.add(password);
                    value.add(name);
                    value.add(mobile);
                    value.add(gender);
                    value.add(date);
                    value.add(exp);
                    value.add(cat);
                    value.add(subcat);
                    value.add(city);
                    value.add(ans);

                    if (flag)
                    {
                        uploadMultipart();
                    }
                    cs3 = new CallServices();
                    String r = cs3.CallServices(JobseekerregistrationActivity.this, Url.URL + "insertjobseeker.php", Url.METHOD, key, value);
                    if (r.equals("1")) {


                        makeText(JobseekerregistrationActivity.this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(JobseekerregistrationActivity.this, JobseekerloginActivity.class));
                        finish();
                    } else if (r.equals("0")) {
                        makeText(JobseekerregistrationActivity.this, "Not Registered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JobseekerregistrationActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    jobseekerregistration_email.getText().clear();
                }}

            }
        });

        jobseekerregistration_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionManager.checkStoragePermission(JobseekerregistrationActivity.this)) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(JobseekerregistrationActivity.this);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            filePath = result.getUri();
            if (filePath.equals("")) {
                Toast.makeText(this, "Please select your profile image", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    jobseekerregistration_logo.setImageBitmap(bitmap);
                    flag=true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadMultipart() {
        //getting name for the image

        Log.d("Filepath===>", String.valueOf(filePath));
        //getting the actual path of the image
        path = getPath(filePath);
        Log.d("IMage Path===>", path);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request

            new MultipartUploadRequest(this, uploadId, Url.URL + "jobseekerimage.php")
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


    public String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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
