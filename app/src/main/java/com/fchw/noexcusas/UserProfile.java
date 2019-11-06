package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserProfile extends Fragment {
    private EditText mChildValueEditText,
            muserName, muserAge, muserSex;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btEdit;


    private Button mAddButton, mRemoveButton;
    private TextView mchildValueTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_user_profile, container, false);


        /*final EditText mChildValueEditText = v.findViewById(R.id.childValueEditText);
        mAddButton = v.findViewById(R.id.addButton);
        mRemoveButton = v.findViewById(R.id.removeButton);
        mchildValueTextView = v.findViewById(R.id.childValueTextView);*/
        // muserAge = v.findViewById(R.id.userAge);
        //  muserName = v.findViewById(R.id.userName);
        //  muserSex = v.findViewById(R.id.userSex);
        btEdit = v.findViewById(R.id.button_edit);
        //  mAuth = FirebaseAuth.getInstance();
      /*  firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference mRef = database.getReference("simCoder");

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

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference mRef = database.getReference("simCoder");
                btEdit.setOnClickListener(new View.OnClickListener() {



                    @Override
                    public void onClick(View v) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new UserProfileEdit())
                                .commit();


                    }
                });
            }
        };
        return v;

    }
}


/*
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
*/
