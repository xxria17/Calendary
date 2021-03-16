package com.example.calendary.diary;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.calendary.Activitiy.DetailActivity;
import com.example.calendary.Activitiy.MainActivity;
import com.example.calendary.R;
import com.example.calendary.RecyclerClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.calendary.Activitiy.MainActivity.hotDiary;
import static com.example.calendary.Activitiy.MainActivity.todayDiary;


public class DiaryFragment extends Fragment{

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
    private DrawerLayout drawerLayout;
    MainActivity mainActivity;

    public DiaryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        init(view);
        todayDiary.setAlpha(0.3f);
        hotDiary.setAlpha(1f);
        baseDiary.load_Bestdiary(view.getContext(), diary_list);
        baseDiary.select_diary(view.getContext(), diary_list);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseDiary.load_Bestdiary(view.getContext(), diary_list);
                refreshLayout.setRefreshing(false);

                //인기 파트에 있을 때, 최신 파트에 있을 때 구분해서 새로고침 구현하기
            }
        });

        hotDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDiary.load_Bestdiary(view.getContext(), diary_list);
                hotDiary.setAlpha(1f);
                todayDiary.setAlpha(0.3f);
            }
        });

        todayDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseDiary.todayDiaryLoad(view.getContext(), diary_list);
                hotDiary.setAlpha(0.3f);
                todayDiary.setAlpha(1f);
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

        baseDiary = new BaseDiary();
    }
}
