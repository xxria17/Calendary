package com.example.calendary.Activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendary.BuildConfig;
import com.example.calendary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edit_email;
    public EditText edit_password;
    public Button signin_btn;
    public TextView signup_tv, find_password;
    public ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isTaskRoot() != true){ finish(); }
        super.onCreate(null);
        setContentView(R.layout.activity_login);

        init();

        if(BuildConfig.DEBUG){
            edit_email.setText("asdf@asdf.com");
            edit_password.setText("123456");
        }
    }

    private void init(){

        edit_email = (EditText) findViewById(R.id.edit_loginEmail);
        edit_password = (EditText) findViewById(R.id.edit_loginPw);
        signin_btn = (Button) findViewById(R.id.loginButton);
        signup_tv = (TextView) findViewById(R.id.signUp);
        find_password = (TextView) findViewById(R.id.findPassword);
        progressDialog = new ProgressDialog(this);

        signin_btn.setOnClickListener(this);
        signup_tv.setOnClickListener(this);
        find_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == signin_btn){
            login();
        }
        else if(v == signup_tv){
            startActivity(new Intent(getApplicationContext(), JoinActivity.class));
        }
    }

    public void login(){

        email = edit_email.getText().toString().trim();
        password = edit_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog.setMessage("로그인중입니다. 잠시만 기다려주세요!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else{
                            Toast.makeText(getApplicationContext(),"로그인 실패!",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }
}
