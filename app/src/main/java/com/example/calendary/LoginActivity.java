package com.example.calendary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.calendary.login.loginFragment;


public class LoginActivity extends AppCompatActivity {

    FrameLayout mFrameLayoutLogin;

    private Intent mintent = null;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;
    private loginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isTaskRoot() != true){
            finish();
        }

        super.onCreate(null);
        setContentView(R.layout.activity_login);


        mintent = getIntent();
        openLoginFragment();
    }

    @Override
    protected void onNewIntent(android.content.Intent intent){
        super.onNewIntent(intent);
        mintent = getIntent();
    }

    private void openLoginFragment(){
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout_login, loginFragment).commit();
    }

}
