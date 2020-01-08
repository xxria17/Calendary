package com.example.calendary.login;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.calendary.R;
import com.google.firebase.auth.FirebaseAuth;


/**
 * 로그인 화면
 */

public class loginFragment extends Fragment  {


    ProgressDialog mLoginProgressDialog = null;
    private FirebaseAuth firebaseAuth;

    public loginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);



    }




}
