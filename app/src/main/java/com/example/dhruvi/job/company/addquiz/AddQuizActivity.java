package com.example.dhruvi.job.company.addquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhruvi.job.R;

public class AddQuizActivity extends AppCompatActivity {

    EditText que;
    Button quizBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        que = findViewById(R.id.que);
        quizBtn = findViewById(R.id.quizBtn);

        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddQuizActivity.this, "Question added Succesfully", Toast.LENGTH_SHORT).show();
                que.setText("");
            }
        });
    }


}
