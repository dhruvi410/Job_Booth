package com.example.dhruvi.job.company.applierrecyclerview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.dhruvi.job.CallServices;
import com.example.dhruvi.job.R;
import com.example.dhruvi.job.Url;

import java.util.ArrayList;

public class ShowResume extends AppCompatActivity {

    WebView webView;
    Intent i;
    String email,pdf,pdfurl;
    CallServices cs;
    ArrayList<String> key,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resume);

        webView=findViewById(R.id.webview);
        i=getIntent();
        key=new ArrayList<>();
        value=new ArrayList<>();
        email=i.getStringExtra("email");

        cs=new CallServices();

        key.add("email");
        value.add(email);
        pdf=cs.CallServices(ShowResume.this, Url.URL+"getresumename.php",Url.METHOD,key,value);
        pdfurl=Url.URL+"resume/"+pdf;
        Log.e("pdf",pdf);

        String pdf_url="http://jobbooth.000webhostapp.com/Practical-5.pdf";
        String googleDocsUrl = "http://docs.google.com/viewer?url="+ pdf_url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(googleDocsUrl ), "text/html");
        startActivity(intent);
        finish();
    }
}
