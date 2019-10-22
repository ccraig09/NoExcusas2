package com.fchw.noexcusas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeMenu extends AppCompatActivity {


    ImageButton profile;
    ImageButton order;
    ImageButton nutrition;
    ImageButton contact;
    ImageButton coach;
    ImageButton aboutUs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        profile = findViewById(R.id.btnProfile);
        order = findViewById(R.id.btnOrder);
        nutrition = findViewById(R.id.btnNutrition);
        contact = findViewById(R.id.btnContact);
        coach = findViewById(R.id.btnCoach);
        aboutUs = findViewById(R.id.btnAboutUs);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });

        coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeMenu.this, UserProfile.class));

            }


        });
    }

}