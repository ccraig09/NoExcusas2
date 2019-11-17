package com.fchw.noexcusas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class ExerciseToGoActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exercise_to_go,container, false);

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
