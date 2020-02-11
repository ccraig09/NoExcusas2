package com.fchw.noexcusas;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class RutinasToGo extends Fragment {
    ImageView ivBCG;
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rutinas_to_go,container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       /* ivBCG = view.findViewById(R.id.grassIV);

        Picasso.get()
                .load("https://i.imgur.com/8EDj8Hj.png")
                .resize(720, 740)
                .centerCrop()
                .into(ivBCG);*/


        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerView youTubePlayerView2 = view.findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);

        YouTubePlayerView youTubePlayerView3 = view.findViewById(R.id.youtube_player_view3);
        getLifecycle().addObserver(youTubePlayerView3);

        YouTubePlayerView youTubePlayerView4 = view.findViewById(R.id.youtube_player_view4);
        getLifecycle().addObserver(youTubePlayerView4);






        return view;
    }
}
