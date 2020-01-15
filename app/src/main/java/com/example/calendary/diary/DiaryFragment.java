package com.example.calendary.diary;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calendary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class DiaryFragment extends Fragment {

    private RecyclerView diary_list;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;

    BaseDiary baseDiary;

    public DiaryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        init(view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }



    private void init(View v){
        diary_list = (RecyclerView) v.findViewById(R.id.shareDiary_list);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout1);

        layoutManager = new LinearLayoutManager(getActivity());
        diary_list.setLayoutManager(layoutManager);
    }

    private void load(){
        baseDiary = new BaseDiary();
        baseDiary.load_Bestdiary(diary_list);
    }

}
