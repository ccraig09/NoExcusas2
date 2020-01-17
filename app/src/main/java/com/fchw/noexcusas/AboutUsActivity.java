package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class AboutUsActivity extends Fragment {
    private TextView clickwap, clickFB;

    ImageView pricesIV, scheduleIV, bigPlanIV, locationIV;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayout linlaybtm;
    RelativeLayout relativeLayout;
    ImageView ivBCG;
    private AdView mAdView;


    public AboutUsActivity(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_about_us, container, false);

        clickFB = view.findViewById(R.id.clickfb);
        clickwap = view.findViewById(R.id.clickwapp);
        pricesIV = view.findViewById(R.id.pricesPhotoIV);
        scheduleIV = view.findViewById(R.id.schedulePhotoIV);
        bigPlanIV = view.findViewById(R.id.planbBigIV);
        locationIV = view.findViewById(R.id.locationPhotoIV);
        relativeLayout = view.findViewById(R.id.rellaygrass);

        //addmob
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       // ivBCG = view.findViewById(R.id.grassIV);


    /*    Picasso.get()
                .load("https://i.imgur.com/8EDj8Hj.png")
                .resize(720, 740)
                .centerCrop()
                .into(ivBCG);


          <ImageView
        android:id="@+id/grassIV"
        android:scaleType="fitXY"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
*/





                /////////////


        /*Picasso.get().load("https://i.imgur.com/8EDj8Hj.png").into(ivBCG , new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                relativeLayout.setBackground(new BitmapDrawable(context.getResources(), bitmap));
            }
            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.e("TAG", "Failed");
            }
            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.e("TAG", "Prepare Load");
            }

        };
*/

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersSheets");
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String imageprice = ""+ds.child("prices photo").getValue();
                    String imageschedule = ""+ds.child("schedule photo").getValue();
                    String imagebigplan = ""+ds.child("big plan photo").getValue();
                    String imagelocation = ""+ds.child("location photo").getValue();




                    try {
                        // if image is received then set
                        Picasso.get().load(imageprice).into(pricesIV);
                        Picasso.get().load(imageschedule).into(scheduleIV);
                        Picasso.get().load(imagebigplan).into(bigPlanIV);
                        Picasso.get().load(imagelocation).into(locationIV);

                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_cloud).into(pricesIV);
                        Picasso.get().load(R.drawable.ic_cloud).into(scheduleIV);
                        Picasso.get().load(R.drawable.ic_cloud).into(bigPlanIV);
                        Picasso.get().load(R.drawable.ic_cloud).into(locationIV);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return view;

    }
}