package com.example.calendary.Fragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calendary.diary.BaseDiary;
import com.example.calendary.diary.DiaryModel;
import com.example.calendary.R;
import com.example.calendary.diary.DiaryAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDiaryFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    private SwipeRefreshLayout refreshLayout;
    RecyclerView.LayoutManager layoutManager;
    private DiaryAdapter diaryAdapter;
    private RecyclerView mydiary_list;
    List<DiaryModel> diaryModelList;
    private Map<String, Object> diaryMap;
    private ProgressDialog progressDialog;
    private BaseDiary baseDiary;

    public MyDiaryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_diary, container, false);
        init(view);
        baseDiary.load_myDiary(view.getContext(), mydiary_list);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseDiary.load_myDiary(view.getContext(), mydiary_list);
                refreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private void init(View v){
        mydiary_list = (RecyclerView) v.findViewById(R.id.mydiary_list);

        layoutManager = new LinearLayoutManager(getActivity());
        mydiary_list.setLayoutManager(layoutManager);

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout2);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        baseDiary = new BaseDiary();
    }

}
