package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutUsActivity extends Fragment {
    private TextView clickwap, clickFB;

    public AboutUsActivity(){
}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_about_us, container, false);

        clickFB = view.findViewById(R.id.clickfb);
        clickwap = view.findViewById(R.id.clickwapp);

        return view;
        }
}
