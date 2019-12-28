package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CoachesActivity extends Fragment {
    private ImageView joseIv, kikiIV, carlosIV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_coaches,container, false);

        joseIv = view.findViewById(R.id.jose);
        kikiIV = view.findViewById(R.id.kiki);
        carlosIV = view.findViewById(R.id.carlos);


        Picasso.get()
                .load("https://i.imgur.com/pn0iWI6.jpg")
                .placeholder(R.drawable.ic_cloud)
                .into(joseIv);

        Picasso.get()
                .load("https://i.imgur.com/DHNBAH0.png")
                .placeholder(R.drawable.ic_cloud)
                .into(kikiIV);


        Picasso.get()
                .load("https://i.imgur.com/gTGHHqu.png")
                .placeholder(R.drawable.ic_cloud)
                .into(carlosIV);



        return view;
    }
}
