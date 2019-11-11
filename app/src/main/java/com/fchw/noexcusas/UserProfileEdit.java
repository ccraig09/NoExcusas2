package com.fchw.noexcusas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserProfileEdit extends Fragment {
    private EditText mChildValueEditText,
            muserName, muserAge, muserSex;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btSaveName,btSave, btnBack;


    private Button mAddButton, mRemoveButton;
    private TextView mchildValueTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_user_profile_edit, container, false);


        /*final EditText mChildValueEditText = v.findViewById(R.id.childValueEditText);
        mAddButton = v.findViewById(R.id.addButton);
        mRemoveButton = v.findViewById(R.id.removeButton);
        mchildValueTextView = v.findViewById(R.id.childValueTextView);*/
        muserAge = v.findViewById(R.id.userAge);
        muserName = v.findViewById(R.id.userName);
        muserSex = v.findViewById(R.id.userSex);
        btnBack = v.findViewById(R.id.btn_back);
        btSave = v.findViewById(R.id.button_save);
        //btSaveName = v.findViewById(R.id.button_save_name);
        mAuth = FirebaseAuth.getInstance();


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference mRef = database.getReference("Users");

               /* mAddButton.setOnClickListener(new View.OnClickListener() {
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
                });*/
                btSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                        String name = muserName.getText().toString();
                        String age = muserAge.getText().toString();
                        String sex = muserSex.getText().toString();
                        Map newPost = new HashMap();
                        newPost.put("Name", name);
                        newPost.put("Age", age);
                        newPost.put("Sex", sex);


                        current_user_db.setValue(newPost);

                        Toast.makeText(getApplicationContext(), getString(R.string.info_updated), Toast.LENGTH_LONG).show();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new UserProfile())
                                .commit();
                    }
                });


                /* Start of code for name only change
                btSaveName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                        String name = muserName.getText().toString();
                        Map newPost = new HashMap();
                        newPost.put("Name", name);

                        current_user_db.setValue(newPost);

                        Toast.makeText(getApplicationContext(),getString(R.string.info_updated), Toast.LENGTH_LONG).show();


                    }
                });
*/



                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),getString(R.string.no_changes), Toast.LENGTH_LONG).show();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new UserProfile())
                                .commit();
                    }
                });



            }


        };

        return v;

    }@Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}

