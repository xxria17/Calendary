package com.example.calendary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    private EditText edit_name, edit_email, edit_pw, edit_pwCheck;
    private Button joinButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public String name, email, pw, pw_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        init();
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void init(){
        edit_email = (EditText) findViewById(R.id.edit_joinEmail);
        edit_name = (EditText) findViewById(R.id.edit_joinName);
        edit_pw = (EditText) findViewById(R.id.edit_joinPw);
        edit_pwCheck = (EditText) findViewById(R.id.edit_joinPwcheck);

        joinButton = (Button) findViewById(R.id.joinButton);

        progressDialog = new ProgressDialog(this);
    }

    private void registerUser(){
        name = edit_name.getText().toString().trim();
        email = edit_email.getText().toString().trim();
        pw = edit_pw.getText().toString().trim();
        pw_check = edit_pwCheck.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pw)){
            Toast.makeText(this,"비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pw_check)){
            Toast.makeText(this, "비밀번호를 한번 더 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pw_check.equals(pw)){
            Toast.makeText(this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("등록중입니다. 잠시만 기다려주세요!");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            storeUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else{
                            Toast.makeText(JoinActivity.this, "회원가입을 실패하였습니다!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void storeUser(){
        name = edit_name.getText().toString().trim();
        email = edit_email.getText().toString().trim();

        Map<String,String> user_info = new HashMap<>();
        user_info.put("name" , name);
        user_info.put("email", email);

        firebaseFirestore.collection("Users").document()
                .set(user_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Toast.makeText(JoinActivity.this, "가입되었습니다!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(JoinActivity.this,"Error :" + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
