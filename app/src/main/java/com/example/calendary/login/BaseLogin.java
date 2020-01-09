package com.example.calendary.login;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.calendary.LoginActivity;
import com.example.calendary.MainActivity;
import com.example.calendary.Model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class BaseLogin {

    FirebaseAuth firebaseAuth;
    LoginActivity loginActivity;
    UserInfo userInfo;

    String email, password;

    public void userLogin(){
        firebaseAuth = FirebaseAuth.getInstance();
        loginActivity = new LoginActivity();

        email = userInfo.getEmail();
        password = userInfo.getPassword();
//        loginActivity.login(email, password);

//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        loginActivity.progressDialog.dismiss();
//                        if(task.isSuccessful()){
//                            loginActivity.finish();
//                            loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
//                        } else{
////                            Toast.makeText(LoginActivity.class,"로그인 실패!",Toast.LENGTH_LONG).show();
//                            Log.d("LOGIN ERROR","login error");
//                            return;
//                        }
//                    }
//                });
    }
}
