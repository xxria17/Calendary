package com.example.calendary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.calendary.Fragment.DiaryFragment;
import com.example.calendary.Fragment.MyPageFragment;
import com.example.calendary.Fragment.SearchFragment;
import com.example.calendary.calendar.CalendarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar maintoolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout main_framelayout;
    TextView toolbar_text, hotDiary, todayDiary;
    SearchView searchView;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private DiaryFragment diaryFragment = new DiaryFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_framelayout, calendarFragment).commitAllowingStateLoss();
        searchView.setVisibility(View.GONE);
        hotDiary.setVisibility(View.GONE);
        todayDiary.setVisibility(View.GONE);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.calendar:{
                        transaction.replace(R.id.main_framelayout, calendarFragment).commit();
                        toolbar_text.setText("달력");
                        searchView.setVisibility(View.GONE);
                        hotDiary.setVisibility(View.GONE);
                        todayDiary.setVisibility(View.GONE);
                        maintoolbar.setBackgroundResource(R.color.white);
                        break;
                    }
                    case R.id.diary:{
                        transaction.replace(R.id.main_framelayout, searchFragment).commit();
                        searchView.setVisibility(View.VISIBLE);
                        toolbar_text.setText("");
                        hotDiary.setVisibility(View.GONE);
                        todayDiary.setVisibility(View.GONE);
                        maintoolbar.setBackgroundResource(R.color.white);
                        break;
                    }
                    case R.id.mood:{
                        transaction.replace(R.id.main_framelayout, diaryFragment).commit();
                        searchView.setVisibility(View.GONE);
                        hotDiary.setVisibility(View.VISIBLE);
                        todayDiary.setAlpha(0.3f);
                        todayDiary.setVisibility(View.VISIBLE);
                        toolbar_text.setText("");
                        maintoolbar.setBackgroundResource(R.color.white);
                        break;
                    }
                    case R.id.setting:{
                        transaction.replace(R.id.main_framelayout, myPageFragment).commit();
                        toolbar_text.setText("");
                        maintoolbar.setBackgroundResource(R.color.colorPrimary);
                        searchView.setVisibility(View.GONE);
                        hotDiary.setVisibility(View.GONE);
                        todayDiary.setVisibility(View.GONE);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void init(){
        maintoolbar = (Toolbar) findViewById(R.id.main_toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNavigation);
        main_framelayout = (FrameLayout) findViewById(R.id.main_framelayout);

        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        hotDiary = (TextView) findViewById(R.id.hotDiary);
        todayDiary = (TextView) findViewById(R.id.todayDiary);

        searchView = (SearchView) findViewById(R.id.searchView);
    }
}
