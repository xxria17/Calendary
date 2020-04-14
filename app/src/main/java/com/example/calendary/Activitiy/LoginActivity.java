package com.example.calendary.Activitiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendary.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edit_email;
    public EditText edit_password;
    public Button signin_btn;
    public TextView signup_tv, find_password;
    public ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;


    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isTaskRoot() != true) {
            finish();
        }
        super.onCreate(null);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        init();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


    }

    private void init() {

        edit_email = (EditText) findViewById(R.id.edit_loginEmail);
        edit_password = (EditText) findViewById(R.id.edit_loginPw);
        signin_btn = (Button) findViewById(R.id.loginButton);
        signup_tv = (TextView) findViewById(R.id.signUp);
        find_password = (TextView) findViewById(R.id.findPassword);
        signInButton = (SignInButton) findViewById(R.id.google_btn);
        progressDialog = new ProgressDialog(this);

        signin_btn.setOnClickListener(this);
        signup_tv.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        find_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == signin_btn) {
            login();
        } else if (v == signup_tv) {
            startActivity(new Intent(getApplicationContext(), JoinActivity.class));
        } else if (v == signInButton) {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public void login() {

        email = edit_email.getText().toString().trim();
        password = edit_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("로그인중입니다. 잠시만 기다려주세요!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("APIException", e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "로그인이 성공하였습니다", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
}
