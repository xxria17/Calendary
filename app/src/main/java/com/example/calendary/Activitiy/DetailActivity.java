package com.example.calendary.Activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calendary.R;
import com.example.calendary.diary.DiaryModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private TextView detail_title, detail_content, detail_username, detail_timestamp;
    private ImageView back, detail_userImage;
    private Intent intent;

    private String title, content, name;
    private Date time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        intent = getIntent();

        DiaryModel diaryModel = (DiaryModel) intent.getSerializableExtra("Content");
        title = diaryModel.title;
        content = diaryModel.content;
        time = diaryModel.timestamp;
        name = diaryModel.username;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");

        detail_title.setText(title);
        detail_content.setText(content);
        detail_timestamp.setText(dateFormat.format(time));
        detail_username.setText(name);
        

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        detail_content = findViewById(R.id.detail_content);
        detail_timestamp = findViewById(R.id.detail_timestamp);
        detail_title = findViewById(R.id.detail_title);
        detail_username = findViewById(R.id.detail_userName);

        detail_userImage = findViewById(R.id.detail_userImage);

        back = findViewById(R.id.detail_back);
    }
}
