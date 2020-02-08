package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileActivity extends AppCompatActivity implements RewardedVideoAdListener {

    TextView mText, userEmail;
    private int pointCount;


    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    Button userLogout;
    Button goToHome;
    ImageView ivQR;
    private AdView mAdView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textView=findViewById(R.id.text_date_display);
        SimpleDateFormat sdf = new SimpleDateFormat("🗓 dd.MM.yyyy '⌚️' HH:mm:");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);


        userEmail = findViewById(R.id.tvUserEmail);
        userLogout = findViewById(R.id.btnLogout);
        goToHome = findViewById(R.id.btnToHome);
        ivQR = findViewById(R.id.qrIV);

        mText = findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersSheets");


        //rewards ads
        MobileAds.initialize(getApplicationContext(), ("ca-app-pub-3940256099942544~3347511713"));
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);


        //points count
        mText = findViewById(R.id.textView);
        pointCount = 0;
        mText.setText("Points: " + pointCount);


        loadRewardedVideoAd();


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
    private void loadRewardedVideoAd ()
    {

        if (!mRewardedVideoAd.isLoaded())
        {
            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
        }
    }

    public void startVideoAd(View view)
    {
        if(mRewardedVideoAd.isLoaded())
        {
            mRewardedVideoAd.show();

        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

        loadRewardedVideoAd();

    }

    private void addPoints(int points) {
        pointCount +=  points;
        mText.setText("Points: " + pointCount);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Toast.makeText(ProfileActivity.this, "onUserEarnedReward", Toast.LENGTH_SHORT).show();
        addPoints(rewardItem.getAmount());

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    protected void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}