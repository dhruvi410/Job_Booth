package com.example.dhruvi.job.company;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.PermissionManager;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Session;
import com.example.dhruvi.job.Url;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.makeText;
import static com.example.dhruvi.job.jobseeker.postrecyclerview.JobseekerpostInfo.STORAGE_PERMISSION_CODE;

public class EditprofileCompany extends AppCompatActivity {

    EditText editcompanyname, editcompanyemail;
    Button editcompanybtn;
    Toolbar toolbar;
    CircleImageView editcompanyimg;
    TextView editcompanypassword;
    String unm, newname, newemail, newpassword, pwd, img, path;
    Session s;
    Context context;
    CallServices cs, cs2;
    ArrayList<String> key, value;
    Uri filePath;
    Bitmap bitmap;
    Boolean flag=false;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile_company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        editcompanyname = findViewById(R.id.editcompanyname);
        editcompanyemail = findViewById(R.id.editcompanyemail);
        editcompanybtn = findViewById(R.id.editcompanybtn);
        editcompanyimg = findViewById(R.id.editcompanyimg);
        editcompanypassword = findViewById(R.id.editcompanypassword);
        context = EditprofileCompany.this;
        s = new Session(context);
        unm = s.checkLogin();
        cs = new CallServices();
        cs2 = new CallServices();
        key = new ArrayList<>();
        value = new ArrayList<>();


        key.add("email");
        value.add(unm);
        img = cs.CallServices(EditprofileCompany.this, Url.URL + "getimagecompany.php", Url.METHOD, key, value);
        Glide.with(context).load(Url.URL + "companydb/" + img).into(editcompanyimg);
        key.clear();
        value.clear();
        key.add("email");
        value.add(unm);
        String res = cs.CallServices(context, Url.URL + "profileupdatecompany.php", Url.METHOD, key, value);
        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(res);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                editcompanyname.setText(c.getString("name"));
                editcompanyemail.setText(c.getString("email"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        editcompanyname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newname = editcompanyname.getText().toString();
            }
        });

        editcompanyemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newemail = editcompanyemail.getText().toString();
            }
        });

        editcompanypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(context);
                final EditText pcoldpassword, pcnewpassword;
                Button pcbtn;
                d.setContentView(R.layout.passwordcheck);
                pcoldpassword = d.findViewById(R.id.pcoldpassword);
                pcnewpassword = d.findViewById(R.id.pcnewpassword);
                pcbtn = d.findViewById(R.id.pcbtn);
                pwd = s.getPwd();
                pcbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pcoldpassword.getText().toString().equals(pwd)) {
                            newpassword = pcnewpassword.getText().toString();
                            key.clear();
                            value.clear();
                            key.add("email");
                            key.add("npassword");
                            value.add(unm);
                            value.add(newpassword);
                            String rr= cs.CallServices(EditprofileCompany.this,Url.URL+"changepasswordcomp.php",Url.METHOD,key,value);
                            if(rr.equals("1"))
                            {Toast.makeText(EditprofileCompany.this, "Password is changed", Toast.LENGTH_SHORT).show();
                                d.dismiss();}
                            else
                                Toast.makeText(EditprofileCompany.this, "Your password is not changed", Toast.LENGTH_SHORT).show();
                        } else {
                            s.logout();
                            startActivity(new Intent(context, CompanyloginActivity.class));
                        }
                    }
                });
                d.show();
            }
        });

        Log.e("all===", unm);

        editcompanybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    key.clear();
                    value.clear();
                    key.add("oldname");
                    key.add("newname");
                    key.add("newemail");


                    value.add(unm);
                    value.add(newname);
                    value.add(newemail);

                    String response = cs2.CallServices(context, Url.URL + "updateprofilecomp.php", Url.METHOD, key, value);
                    if (response.equals("1")) {
                        if (flag=true)
                        {
                            uploadMultipart();
                        }

                        Toast.makeText(context, "Updated Succefull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, CompanyloginActivity.class));
                        finish();
                    } else
                        Toast.makeText(context, "Not successfull", Toast.LENGTH_SHORT).show();



                Toast.makeText(context, "" + newname + " " + newemail + " " + newpassword, Toast.LENGTH_SHORT).show();
            }

        });

        editcompanyimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionManager.checkStoragePermission((Activity) context)) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start((Activity) context);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            filePath = result.getUri();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                editcompanyimg.setImageBitmap(bitmap);
                flag=true;

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

            new MultipartUploadRequest(this, uploadId, Url.URL + "updatecompanyimage.php")
                    .addParameter("email", unm)
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
