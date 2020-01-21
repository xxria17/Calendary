package com.example.calendary.diary;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.calendary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.value.TimestampValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class DiaryFragment extends Fragment {

    private RecyclerView diary_list;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    List<DiaryModel> diaryModelList;
    private Map<String, Object> diaryMap;
    BaseDiary baseDiary;
    DiaryAdapter diaryAdapter;
    private ProgressDialog progressDialog;

    public DiaryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        init(view);

        BestDiary_load();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BestDiary_load();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }



    private void init(View v){
        diary_list = (RecyclerView) v.findViewById(R.id.shareDiary_list);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout1);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        layoutManager = new LinearLayoutManager(getActivity());
        diary_list.setLayoutManager(layoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void BestDiary_load(){
        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

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
                        diaryMap = document.getData();

                        diaryModel.id = (String) document.getId();
                        diaryModel.title = (String) diaryMap.getOrDefault("title", "제목");
                        diaryModel.content = (String) diaryMap.getOrDefault("content", "내용");
                        diaryModel.username = (String) diaryMap.getOrDefault("user name", "이름");
                        diaryModel.timestamp = ((Timestamp) diaryMap.getOrDefault("timestamp", 0)).toDate();
//                    diaryModel.imageView = (ImageView) diaryMap.getOrDefault("title", "제목");

                        diaryModelList.add(diaryModel);
                    }
                    diaryAdapter = new DiaryAdapter(diaryModelList);
                    diary_list.setAdapter(diaryAdapter);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void loading(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("로딩중입니다..");
                        progressDialog.show();
                    }
                } , 0);
    }

    public void loadingEnd(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                } , 0);
    }

}
