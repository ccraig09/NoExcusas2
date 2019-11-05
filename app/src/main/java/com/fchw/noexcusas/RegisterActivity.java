package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    private ImageView iv ;
    RelativeLayout rellay1, rellay2;
    ProgressBar progressBar;
    Button login;
    SignInButton signInButton;
    int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    String TAG = "MainActivity";
    FirebaseAuth mAuth;
    Button signup;
    Button forgotPassword;
    EditText email;
    CheckBox remember;
    EditText password;
    FirebaseAuth firebaseAuth;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    private EditText mChildValueEditText,
            muserName, muserAge, muserSex;

    private Button mAddButton, mRemoveButton;
    private TextView mchildValueTextView;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        final EditText mChildValueEditText =  findViewById(R.id.childValueEditText);
        mAddButton =  findViewById(R.id.addButton);
        mRemoveButton =  findViewById(R.id.removeButton);
        mchildValueTextView =  findViewById(R.id.childValueTextView);
        muserAge = findViewById(R.id.userAge);
        muserName = findViewById(R.id.userName);
        muserSex = findViewById(R.id.userSex);

        // firebase login
        progressBar = findViewById(R.id.progressBar);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);
        forgotPassword = findViewById(R.id.btnForgotPassword);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);


                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });



                FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = database.getReference("simCoder");

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String childValue = mChildValueEditText.getText().toString();
                mRef.setValue(childValue);

            }
        });
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.removeValue();

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                mchildValueTextView.setText(childValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

});
    }
}
