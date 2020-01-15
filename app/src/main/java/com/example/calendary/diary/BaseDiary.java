package com.example.calendary.diary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendary.DetailActivity;
import com.example.calendary.Model.DiaryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BaseDiary {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

    private DiaryAdapter diaryAdapter;

    List<DiaryModel> diaryModelList;
    private Map<String, Object> diaryMap;

    /*
    *  인기순
    * */
    public void load_Bestdiary(final RecyclerView recyclerView){
        CollectionReference reference = firebaseFirestore.collection("Content");
        reference.whereEqualTo("show", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshots = task.getResult();
                    diaryModelList = new ArrayList<>();
                    diaryMap = new HashMap<>();
                    for(QueryDocumentSnapshot document : documentSnapshots) {
                        DiaryModel diaryModel = new DiaryModel();

                        diaryModel.id = (String) document.getId();
                        diaryModel.title = (String) diaryMap.getOrDefault("title", "제목");
                        diaryModel.content = (String) diaryMap.getOrDefault("content", "내용");
                        diaryModel.username = (String) diaryMap.getOrDefault("user name", "이름");
//                    diaryModel.imageView = (ImageView) diaryMap.getOrDefault("title", "제목");

                        diaryModelList.add(diaryModel);
                    }
                    diaryAdapter = new DiaryAdapter(diaryModelList);
                    recyclerView.setAdapter(diaryAdapter);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /*
    * 내가 쓴 일기
    * */
    public void load_myDiary(final RecyclerView recyclerView){
        CollectionReference reference = firebaseFirestore.collection("Content");
        reference.whereEqualTo("user id", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshots = task.getResult();
                    diaryModelList = new ArrayList<>();
                    diaryMap = new HashMap<>();
                    for(QueryDocumentSnapshot document : documentSnapshots) {
                        DiaryModel diaryModel = new DiaryModel();

                        diaryModel.id = (String) document.getId();
                        diaryModel.title = (String) diaryMap.getOrDefault("title", "제목");
                        diaryModel.content = (String) diaryMap.getOrDefault("content", "내용");
                        diaryModel.username = (String) diaryMap.getOrDefault("user name", "이름");
//                        diaryModel.timestamp = ((Timestamp)diaryMap.getOrDefault("timestamp", 0)).toDate();
//                    diaryModel.imageView = (ImageView) diaryMap.getOrDefault("title", "제목");

                        diaryModelList.add(diaryModel);

                        Collections.sort(diaryModelList, new Comparator<DiaryModel>() {
                            @Override
                            public int compare(DiaryModel o1, DiaryModel o2) {
                                return o2.timestamp.compareTo(o1.timestamp);
                            }
                        });
                    }
                    diaryAdapter = new DiaryAdapter(diaryModelList);
                    recyclerView.setAdapter(diaryAdapter);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

//    public void select_diary(Context context , int position){
//        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra("id", diaryModelList.get(position).id);
//        intent.putExtra("Content", diaryModelList.get(position));
//        startActivity(intent);
//    }
}
