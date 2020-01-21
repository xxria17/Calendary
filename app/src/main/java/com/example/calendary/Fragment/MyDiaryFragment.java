package com.example.calendary.Fragment;


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

import com.example.calendary.Model.DiaryModel;
import com.example.calendary.R;
import com.example.calendary.diary.DiaryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public MyDiaryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_diary, container, false);
        init(view);
        load_myDiary();
        return view;
    }

    public void load_myDiary(){
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
                    mydiary_list.setAdapter(diaryAdapter);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void init(View v){
        mydiary_list = (RecyclerView) v.findViewById(R.id.mydiary_list);

        layoutManager = new LinearLayoutManager(getActivity());
        mydiary_list.setLayoutManager(layoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

}
