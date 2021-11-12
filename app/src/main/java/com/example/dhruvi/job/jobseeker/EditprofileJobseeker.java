package com.example.dhruvi.job.jobseeker;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditprofileJobseeker extends AppCompatActivity {

    EditText editjobname, editjobmobile, editjobgender, editjobdob, editjobexperince, editjobcity;
    Button editjobbtn;
    TextView changepass;
    CircleImageView editjobimg;
    CallServices cs, cs2;
    ArrayList<String> key, value;
    Context context;
    Session s;
    String unm, img, path, password, opass;
    Uri filePath;
    Bitmap bitmap;
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile_jobseeker);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        editjobname = findViewById(R.id.editjobname);
        editjobmobile = findViewById(R.id.editjobmobile);
        editjobgender = findViewById(R.id.editjobgender);
        editjobimg = findViewById(R.id.editjobimg);
        editjobdob = findViewById(R.id.editjobdob);
        editjobexperince = findViewById(R.id.editjobexperince);
        editjobcity = findViewById(R.id.editjobcity);
        changepass = findViewById(R.id.changepass);
        editjobbtn = findViewById(R.id.editjobbtn);
        context = EditprofileJobseeker.this;
        s = new Session(context);
        cs = new CallServices();
        cs2 = new CallServices();
        key = new ArrayList<>();
        value = new ArrayList<>();


        unm = s.checkLogin();
        key.add("email");
        value.add(unm);
        img = cs.CallServices(context, Url.URL + "getjobseekerimage.php", Url.METHOD, key, value);
        Glide.with(context).load(Url.URL + "jobdp/" + img).into(editjobimg);
        key.clear();
        value.clear();
        key.add("name");
        value.add(unm);
        String res = cs.CallServices(context, Url.URL + "profileupdatejob.php", Url.METHOD, key, value);
        try {
            // intialize json object and give json object the reference of array of object
            JSONObject jsonObject = new JSONObject(res);
            // fetch array from json object
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            // loop for get the value from jsonarray one by one
            for (int i = 0; i <= jsonArray.length(); i++) {
                // create another json object as in jsonarray there are lots of object so to get specific position object from array
                JSONObject c = jsonArray.getJSONObject(i);
                editjobname.setText(c.getString("name"));
                editjobmobile.setText(c.getString("mobile"));
                editjobgender.setText(c.getString("gender"));
                editjobdob.setText(c.getString("dob"));
                editjobexperince.setText(c.getString("experince"));
                editjobcity.setText(c.getString("city"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(EditprofileJobseeker.this);
                final EditText pcoldpassword, pcnewpassword;
                Button pcbtn;
                d.setContentView(R.layout.jobpassword);
                pcoldpassword = d.findViewById(R.id.jpcoldpassword);
                pcnewpassword = d.findViewById(R.id.jpcnewpassword);
                pcbtn = d.findViewById(R.id.jpcbtn);
                opass = s.getPwd();
                Toast.makeText(EditprofileJobseeker.this, opass, Toast.LENGTH_SHORT).show();
                pcbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (pcoldpassword.getText().toString().equals(opass)) {
                            password = pcnewpassword.getText().toString();
                            key.clear();
                            value.clear();
                            key.add("email");
                            key.add("npassword");
                            value.add(unm);
                            value.add(password);
                            String ror = cs.CallServices(EditprofileJobseeker.this, Url.URL + "changepasswordjob.php", Url.METHOD, key, value);
                            Log.e("roor", ror);
                            if (ror.equals("1")) {
                                Toast.makeText(EditprofileJobseeker.this, "Password is changed", Toast.LENGTH_SHORT).show();
                                d.dismiss();
                            } else {
                                Toast.makeText(EditprofileJobseeker.this, "Password is not changed", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(EditprofileJobseeker.this, "Naaaaaaaaaa", Toast.LENGTH_SHORT).show();
                    }
                });


                d.show();
            }
        });
        key.clear();
        value.clear();

        editjobbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag)
                    uploadMultipart();

                key.add("oldname");
                key.add("newname");
                key.add("newmobile");
                key.add("newgender");
                key.add("newdob");
                key.add("newexperince");
                key.add("newcity");

                value.add(unm);
                value.add(editjobname.getText().toString());
                value.add(editjobmobile.getText().toString());
                value.add(editjobgender.getText().toString());
                value.add(editjobdob.getText().toString());
                value.add(editjobexperince.getText().toString());
                value.add(editjobcity.getText().toString());

                String reponse = cs2.CallServices(context, Url.URL + "updateprofilejob.php", Url.METHOD, key, value);

                if (reponse.equals("1")) {

                    Toast.makeText(context, "Updated Succefully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, JobseekerDashboardActivity.class));
                } else
                    Toast.makeText(context, "Not Updated", Toast.LENGTH_SHORT).show();

            }
        });

        editjobimg.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            filePath = result.getUri();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                editjobimg.setImageBitmap(bitmap);
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void uploadMultipart() {
        //getting name for the image

        Log.d("Filepath===>", String.valueOf(filePath));
        //getting the actual path of the image
        Log.e("urriiiiiii", String.valueOf(filePath.toString().trim().length()));
        //if(filePath.toString().trim().length()>0)
        //{
        path = getPath(filePath);
        Log.d("IMage Path===>", path);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request

            new MultipartUploadRequest(this, uploadId, Url.URL + "updatejobimage.php")
                    .addParameter("email", unm)
                    .addFileToUpload(path, "image") //Adding file
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            makeText(this, "Success", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {
            makeText(this, "Error==" + exc.getMessage(), Toast.LENGTH_SHORT).show();
        }//}
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
