package com.example.calendary.Activitiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendary.R;
import com.example.calendary.diary.DiaryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WriteDiaryActivity extends AppCompatActivity {

    private ImageView backBtn, addImgBtn;
    private TextView saveBtn;
    private EditText editTitle, editContent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateCheck();
                saveDiary();
            }
        });

    }

    private void init(){
        backBtn = (ImageView) findViewById(R.id.back_btn);
        addImgBtn = (ImageView) findViewById(R.id.add_img_btn);
        saveBtn = (TextView) findViewById(R.id.save_btn);
        editContent = (EditText) findViewById(R.id.edit_content);
        editTitle = (EditText) findViewById(R.id.edit_title);
    }

    private void validateCheck(){
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(content)){
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void saveDiary(){

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("업로드중...");
        dialog.show();

        DiaryModel diaryModel = new DiaryModel();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",2020);
        int month = intent.getIntExtra("month",4);
        int day = intent.getIntExtra("day",13);

        Date currentTime = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        Date select_day = new Date(year-1900, month, day);

        diaryModel.user_id = firebaseUser.getEmail();
        diaryModel.title = editTitle.getText().toString();
        diaryModel.content = editContent.getText().toString();
        diaryModel.timestamp = currentTime;
        diaryModel.select_timestamp = select_day;

        Map<String, Object> diary_map = new HashMap<>();

        diary_map.put("user id", diaryModel.user_id);
        diary_map.put("title",diaryModel.title);
        diary_map.put("content", diaryModel.content);
        diary_map.put("timestamp", diaryModel.timestamp);
        diary_map.put("select timestamp", diaryModel.select_timestamp);

        CollectionReference reference = firebaseFirestore.collection("User");
        reference.whereEqualTo("Email", firebaseUser.getEmail()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot snapshots = task.getResult();
                for(QueryDocumentSnapshot queryDocumentSnapshot : snapshots){
                    DiaryModel diaryModel1 = new DiaryModel();
                    diaryModel1.username = queryDocumentSnapshot.getData().get("name").toString();
                    diary_map.put("user name", diaryModel1.username);
                    firebaseFirestore.collection("Content").add(diary_map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    dialog.dismiss();
                                    Toast.makeText(WriteDiaryActivity.this, "성공적으로 업로드 되었습니다!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("WriteDiaryActivity",e.getMessage());
                                    dialog.dismiss();
                                    Toast.makeText(WriteDiaryActivity.this, "업로드 실패하였습니다", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                }
            } else{
                Log.d("WriteDiaryActivity", "get failed with ", task.getException());
            }
        });
    }
}
