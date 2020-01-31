package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class UserProfile extends Fragment {
    private
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    private AdView mAdView;

    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btEdit, btRefresh, btnBack;
    ImageView avatarIv;
    TextView nameTv, emailTv, phoneTv, pesatv,
            tallatv, imctv, grasatv,
            musculotv, kcaltv, grasavitv;


public UserProfile(){

}


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_user_profile, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersSheets");

        //init views
        avatarIv =view.findViewById(R.id.avatarIv);
        nameTv =view.findViewById(R.id.nameTV);
        emailTv =view.findViewById(R.id.emailTV);
        phoneTv =view.findViewById(R.id.phoneTV);
        btRefresh =view.findViewById(R.id.button_refresh);

        //admob
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //personal evaluation
        pesatv =view.findViewById(R.id.pesaTV);
        tallatv =view.findViewById(R.id.tallaTV);
        imctv =view.findViewById(R.id.imcTV);
        grasatv =view.findViewById(R.id.grasaTV);
        musculotv =view.findViewById(R.id.musculoTV);
        kcaltv =view.findViewById(R.id.kcalTV);
        grasavitv =view.findViewById(R.id.grasaviTV);


        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String email = ""+ds.child("email").getValue();



                    //set data
                    emailTv.setText(email);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query2 = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name = ""+ds.child("Name").getValue();
                    String phone = ""+ds.child("celular").getValue();
                    String image = ""+ds.child("Profile Image").getValue();
                    //evalutation
                    String pesa =  ""+ds.child("PESO").getValue();
                    String talla = ""+ds.child("TALLA").getValue();
                    String imc =   "= "+ds.child("IMC").getValue();
                    String grasa = ""+ds.child("%GRASA").getValue();
                    String musculo = ""+ds.child("%MUSCULO").getValue();
                    String kcal = ""+ds.child("KCAL").getValue();




                    //set data
                    nameTv.setText(name);
                    phoneTv.setText(phone);
                    pesatv.setText(pesa);
                    tallatv.setText(talla);
                    imctv.setText(imc);
                    grasatv.setText(grasa);
                    musculotv.setText(musculo);
                    kcaltv.setText(kcal);


                    try {
                        // if image is received then set
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_photo).into(avatarIv);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new UserProfile())
                        .commit();


            }
        });

        return view;



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


//---------------------------------------------------------------------------

/*final EditText mChildValueEditText = v.findViewById(R.id.childValueEditText);
        mAddButton = v.findViewById(R.id.addButton);
        mRemoveButton = v.findViewById(R.id.removeButton);
        mchildValueTextView = v.findViewById(R.id.childValueTextView);*/
// muserAge = v.findViewById(R.id.userAge);
//  muserName = v.findViewById(R.id.userName);
//  muserSex = v.findViewById(R.id.userSex);

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

//---------------------------------------------------------------------------

              /*  mAuth = FirebaseAuth.getInstance();
                firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
@Override
public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
final DatabaseReference mRef = database.getReference("Users");*/