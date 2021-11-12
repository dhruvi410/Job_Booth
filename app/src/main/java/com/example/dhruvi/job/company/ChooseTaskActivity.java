package com.example.dhruvi.job.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.dhruvi.job.R;
import com.example.dhruvi.job.company.addquiz.AddQuizActivity;
import com.example.dhruvi.job.company.applierrecyclerview.AppliersActivity;

public class ChooseTaskActivity extends AppCompatActivity {

    CardView cvAddQuiz,cvViewAppliers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_task);

        final String title;
        Intent intent = getIntent();
        intent=getIntent();
        title=intent.getStringExtra("title");

        cvAddQuiz = findViewById(R.id.cvAddQuiz);
        cvViewAppliers = findViewById(R.id.cvViewAppliers);

        cvViewAppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseTaskActivity.this, AppliersActivity.class);
                i.putExtra("title",title);
                startActivity(i);
            }
        });

        cvAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseTaskActivity.this, AddQuizActivity.class);
                i.putExtra("title",title);
                startActivity(i);
            }
        });
    }
}
