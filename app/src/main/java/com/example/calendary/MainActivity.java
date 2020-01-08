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

import com.example.calendary.Fragment.MyPageFragment;
import com.example.calendary.Fragment.SearchFragment;
import com.example.calendary.calendar.CalendarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar maintoolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout main_framelayout;
    TextView toolbar_text;
    SearchView searchView;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private MyPageFragment myPageFragment = new MyPageFragment();
    private SearchFragment searchFragment = new SearchFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_framelayout, calendarFragment).commitAllowingStateLoss();
        searchView.setVisibility(View.GONE);

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
                        break;
                    }
                    case R.id.diary:{
                        transaction.replace(R.id.main_framelayout, searchFragment).commit();
                        searchView.setVisibility(View.VISIBLE);
                        toolbar_text.setText("");
                        break;
                    }
                    case R.id.mood:{
//                        transaction.replace(R.id.main_framelayout, ).commit();
                        searchView.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.setting:{
                        transaction.replace(R.id.main_framelayout, myPageFragment).commit();
                        toolbar_text.setText("마이 페이지");
                        searchView.setVisibility(View.GONE);
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
        searchView = (SearchView) findViewById(R.id.searchView);
    }
}
