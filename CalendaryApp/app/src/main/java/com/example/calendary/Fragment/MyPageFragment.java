package com.example.calendary.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendary.Adapter.TabPagerAdapter;
import com.example.calendary.Activitiy.LoginActivity;
import com.example.calendary.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MyPageFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView logout, username;
    private ImageView alarm, setting;
    TabPagerAdapter pagerAdapter;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public MyPageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        init(view);
        setViewPager();

        user = firebaseAuth.getCurrentUser();
        CollectionReference reference = firebaseFirestore.collection("Users");
        reference.whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot snapshots = task.getResult();
                for(QueryDocumentSnapshot queryDocumentSnapshot : snapshots){
                    username.setText(queryDocumentSnapshot.getData().get("name").toString());
                }
            } else{
                Log.d("MyPageFragment!!" , "get failed with ", task.getException());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void init(View view){
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        logout = (TextView) view.findViewById(R.id.logout);
        alarm = (ImageView) view.findViewById(R.id.notification);
        setting = (ImageView) view.findViewById(R.id.settingButton);
        username = (TextView) view.findViewById(R.id.username);

        if(firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setViewPager(){

        tabLayout.addTab(tabLayout.newTab().setText("내 일기"));
        tabLayout.addTab(tabLayout.newTab().setText("활동"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        pagerAdapter = new TabPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void logout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                Toast.makeText(getActivity(), "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        });
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }

}
