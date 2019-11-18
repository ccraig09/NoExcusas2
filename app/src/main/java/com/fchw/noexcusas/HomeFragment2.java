package com.fchw.noexcusas;

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


public class HomeFragment2 extends Fragment {

private
    TextView mStartDate, mEndDate, mPlan, mDays, mAnnounce, mMotivate;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public HomeFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home2, container, false);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerView youTubePlayerView2 = view.findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);

        mStartDate = view.findViewById(R.id.startDateTV);
        mEndDate = view.findViewById(R.id.endDateTV);
        mPlan = view.findViewById(R.id.planTV);
        mDays = view.findViewById(R.id.daysTV);
        mMotivate = view.findViewById(R.id.motivationTV);
        mAnnounce = view.findViewById(R.id.announce);


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

                    String startdate = "Fecha de Inicio = " + ds.child("Start Date").getValue();
                    String enddate = "Fecha de Salida = " + ds.child("End Date").getValue();
                    String plan = "Plan " + ds.child("Plan").getValue();
                    String days = "Tu plan vence en " + ds.child("Days Left").getValue() + " días ";
                    String motiv = "" + ds.child("Motivacion").getValue();
                    String announce = "" +ds.child("Anuncios").getValue();


                    mStartDate.setText(startdate);
                    mEndDate.setText(enddate);
                    mPlan.setText(plan);
                    mDays.setText(days);
                    mMotivate.setText(motiv);
                    mAnnounce.setText(announce);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return view;

    }
}
