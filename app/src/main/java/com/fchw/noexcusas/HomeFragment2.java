package com.fchw.noexcusas;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.fchw.noexcusas.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;


public class HomeFragment2 extends Fragment {

private
    TextView  mcheckin, mStartDate, mEndDate, mPlan, mDays, mAnnounce, mMotivate;
    ImageView mAnnouncephoto, mAnnouncephoto2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private AdView mAdView;



    public HomeFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home2, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerView youTubePlayerView2 = view.findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);

        YouTubePlayerView youTubePlayerView3 = view.findViewById(R.id.youtube_player_view3);
        getLifecycle().addObserver(youTubePlayerView3);


        //member info
        mcheckin = view.findViewById(R.id.checkintv);
        mStartDate = view.findViewById(R.id.startDateTV);
        mEndDate = view.findViewById(R.id.endDateTV);
        mPlan = view.findViewById(R.id.planTV);
        mDays = view.findViewById(R.id.daysTV);
        mMotivate = view.findViewById(R.id.motivationTV);
        mAnnounce = view.findViewById(R.id.announce);
        mAnnouncephoto = view.findViewById(R.id.announcephoto);
        mAnnouncephoto2 = view.findViewById(R.id.announcephoto2);

        //admob
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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

                    String checkin = "Check ins = " + ds.child("Check ins").getValue();
                    String startdate = "Fecha de Inicio = " + ds.child("Start Date").getValue();
                    String enddate = "Fecha de Salida = " + ds.child("End Date").getValue();
                    String plan = "Plan " + ds.child("Plan").getValue();
                    String days = "Tu plan vence en " + ds.child("Days Left").getValue() + " días ";
                    String motiv = "" + ds.child("Motivacion").getValue();
                    String announce = "" +ds.child("Anuncios").getValue();
                    String announcephoto = ""+ds.child("Anuncios photo").getValue();
                    String announcephoto2 = ""+ds.child("Anuncios photo 2").getValue();


                    mcheckin.setText(checkin);
                    mStartDate.setText(startdate);
                    mEndDate.setText(enddate);
                    mPlan.setText(plan);
                    mDays.setText(days);
                    mMotivate.setText(motiv);
                    mAnnounce.setText(announce);

                    try {
                        // if image is received then set
                        Picasso.get().load(announcephoto).into(mAnnouncephoto);
                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_photo).into(mAnnouncephoto);

                    }

                    try {
                        // if image is received then set
                        Picasso.get().load(announcephoto2).into(mAnnouncephoto2);
                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_photo).into(mAnnouncephoto2);

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
