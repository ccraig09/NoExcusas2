package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ProfileActivity extends AppCompatActivity {

    TextView userEmail;
    Button userLogout;
    Button goToHome;
    ImageView ivQR;
    private AdView mAdView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = findViewById(R.id.tvUserEmail);
        userLogout = findViewById(R.id.btnLogout);
        goToHome = findViewById(R.id.btnToHome);
        ivQR = findViewById(R.id.qrIV);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersSheets");

        //admob
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            //checkc until requiered data get
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                //get data
                                                String name = "" + ds.child("Name").getValue();
                                                userEmail.setText(name);

                                                String imageQR = "" + ds.child("QR Image").getValue();

                                                try {
                                                    // if image is received then set
                                                    Picasso.get().load(imageQR).into(ivQR);
                                                } catch (Exception e) {
                                                    //if there is any exception while getting image then set default
                                                    Picasso.get().load(R.drawable.et_bg).into(ivQR);


                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });



            Query query2 = databaseReference.orderByChild("uid").equalTo(firebaseUser.getEmail());
        query2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //checkc until requiered data get
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //get data
                        String name = ""+ds.child("Name").getValue();
                        userEmail.setText(name);

                        String imageQR = "" + ds.child("QR Image").getValue();

                        try {
                            // if image is received then set
                            Picasso.get().load(imageQR).into(ivQR);
                        } catch (Exception e) {
                            //if there is any exception while getting image then set default
                            Picasso.get().load(R.drawable.et_bg).into(ivQR);


                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


        });
        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });

        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, drawer_home.class));
            }
        });


    }
}