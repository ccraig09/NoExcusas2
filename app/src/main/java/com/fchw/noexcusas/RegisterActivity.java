package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    private ImageView iv;
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
    ProgressDialog progressDialog;
    EditText mEmail;
    Button btnBack;
    CheckBox remember;
    EditText mPassword;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    private Button mAddButton, mRemoveButton;
    private TextView mchildValueTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


       /*final EditText mChildValueEditText = findViewById(R.id.childValueEditText);
        mAddButton = findViewById(R.id.addButton);
        mRemoveButton = findViewById(R.id.removeButton);
        mchildValueTextView = findViewById(R.id.childValueTextView);*/
        btnBack = findViewById(R.id.btn_back);

        // firebase login
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);
        forgotPassword = findViewById(R.id.btnForgotPassword);
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando...");


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            //set error and focus to email edittext
                            mEmail.setError(getString(R.string.error));
                            mEmail.setFocusable(true);

                        } else if (password.length()<6) {
                            // set error and focus to password edittext
                            mPassword.setError(getString(R.string.password_length_error));
                            mPassword.setFocusable(true);
                        }
                        else {
                            registerUser(email, password); //register the user
                        }

                    }
                });

            }

    private void registerUser(String email, String password) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();
                            //when user is registered store user info in FB realtime database too
                            //using Hashmap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info in hashmap
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");//add later in edit profile
                            hashMap.put("phone","");
                            hashMap.put("image","");
                            hashMap.put("imageQR","");
                            hashMap.put("pesa","");
                            hashMap.put("talla","");
                            hashMap.put("imc","");
                            hashMap.put("grasa","");
                            hashMap.put("musculo","");
                            hashMap.put("kcal","");
                            hashMap.put("edadmeta","");
                            hashMap.put("grasavi","");

                            //firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user datea named "Users"
                            DatabaseReference reference = database.getReference("Users");
                            //put data within hasmap in databse
                            reference.child(uid).setValue(hashMap);


                            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+ e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}


               /* FirebaseDatabase database = FirebaseDatabase.getInstance();
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
*/