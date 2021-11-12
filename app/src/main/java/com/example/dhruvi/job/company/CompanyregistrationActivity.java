package com.example.dhruvi.job.company;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.makeText;
import static com.example.dhruvi.job.jobseeker.postrecyclerview.JobseekerpostInfo.STORAGE_PERMISSION_CODE;

public class CompanyregistrationActivity extends AppCompatActivity {

    EditText companyregistrationname, companyregistrationemail, companyregistrationpassword,companyregistrationans;
    Button companyregistrationbtn;
    ArrayList<String> key;
    ArrayList<String> value;
    CallServices cs;
    CircleImageView companyregistrationlogo;
    Uri filePath;
    String path;
    Bitmap bitmap;
    Boolean status = false;
    static String name, email, password,ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyregistration);

        companyregistrationname = findViewById(R.id.companyregistrationname);
        companyregistrationans = findViewById(R.id.companyregistrationans);
        companyregistrationemail = findViewById(R.id.companyregistrationemail);
        companyregistrationpassword = findViewById(R.id.companyregistrationpassword);
        companyregistrationbtn = findViewById(R.id.companyregistrationbtn);
        companyregistrationlogo = findViewById(R.id.companyregistrationlogo);


        companyregistrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get values from edittext in string objects
                name = companyregistrationname.getText().toString();
                email = companyregistrationemail.getText().toString();
                password = companyregistrationpassword.getText().toString();
                ans=companyregistrationans.getText().toString();
                if(ans.equals(""))
                    Toast.makeText(CompanyregistrationActivity.this, "Please complete the form", Toast.LENGTH_SHORT).show();
                else{
                    if (Url.isEmailValid(email)) {
                        //initialize key  and value arraylist
                        key = new ArrayList<>();
                        value = new ArrayList<>();

                        // give values to key and value arraylist
                        key.add("name");
                        key.add("email");
                        key.add("password");
                        key.add("ans");

                        value.add(name);
                        value.add(email);
                        value.add(password);
                        value.add(ans);


                        cs = new CallServices();
                        String res = cs.CallServices(CompanyregistrationActivity.this, Url.URL + "insert.php", Url.METHOD, key, value);


                        if (res.equals("1")) {
                            if (status)
                            {
                                uploadMultipart();
                            }


                            Toast.makeText(CompanyregistrationActivity.this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CompanyregistrationActivity.this, CompanyloginActivity.class));
                            finish();
                        } else if (res.equals("0")) {
                            Toast.makeText(CompanyregistrationActivity.this, "Not Registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CompanyregistrationActivity.this, res, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CompanyregistrationActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        companyregistrationemail.getText().clear();

                }}
            }
        });

        companyregistrationlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionManager.checkStoragePermission(CompanyregistrationActivity.this)) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(CompanyregistrationActivity.this);
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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                companyregistrationlogo.setImageBitmap(bitmap);
                status = true;


            } catch (IOException e) {
                e.printStackTrace();
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

            new MultipartUploadRequest(this, uploadId, Url.URL + "companyimage.php")
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
