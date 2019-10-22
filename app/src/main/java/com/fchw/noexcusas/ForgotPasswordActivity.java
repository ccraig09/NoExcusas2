package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    EditText userEmail;
    Button userPass;
    Button btnBack;
    TextView textView;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar = findViewById(R.id.toolbar3);
        progressBar = findViewById(R.id.progressBar);
        userEmail = findViewById(R.id.etForgotEmail);
        userPass = findViewById(R.id.btnForgotPass);
        textView = findViewById(R.id.tvforgotpw);
        btnBack = findViewById(R.id.btn_back);

        toolbar.setTitle(getResources().getText(R.string.forgot_password));



        firebaseAuth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {

                              if (task.isSuccessful()){
                              Toast.makeText(ForgotPasswordActivity.this,getString(R.string.instructions_sent), Toast.LENGTH_LONG).show();
                              }else{
                            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.error_email), Toast.LENGTH_LONG).show();
                        }

                             progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}
