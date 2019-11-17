package com.fchw.noexcusas;

import android.os.Bundle;

import com.fchw.noexcusas.AboutUsActivity;
import com.fchw.noexcusas.CoachesActivity;
import com.fchw.noexcusas.ContactActivity;
import com.fchw.noexcusas.NutritionActivity;
import com.fchw.noexcusas.OrdersActivity;
import com.fchw.noexcusas.R;
import com.fchw.noexcusas.UserProfile;
import com.fchw.noexcusas.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class drawer_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    Toolbar toolbar;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView testIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_home);

        toolbar = findViewById(R.id.tb);
      /*  mStartDate = findViewById(R.id.startDateTV);
        mEndDate = findViewById(R.id.endDateTV);
        mPlan = findViewById(R.id.planTV);
        mDays = findViewById(R.id.daysTV);
        //testIV = findViewById(R.id.testiv); */

        setSupportActionBar(toolbar);
        /*TextView textView = (TextView)toolbar.findViewById(R.id.toolbartv);
        textView.setText("No Excusas");
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment2()).commit();
            navigationView.setCheckedItem(R.id.nav_homeMain);
        }
      /*  firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String startdate = "Fecha de Inicio = " + ds.child("Start Date").getValue();
                    String enddate = "Fecha de Salida = " + ds.child("End Date").getValue();
                    String plan = "Plan = " + ds.child("Plan").getValue();
                    String days = "Tu plan acaba en " + ds.child("Days Left").getValue() +" dias ";


                    mStartDate.setText(startdate);
                    mEndDate.setText(enddate);
                    mPlan.setText(plan);
                    mDays.setText(days);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeMain:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment2()).commit();
                break;
            case R.id.nav_qr2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new QrcodeShowfragment()).commit();

            case R.id.nav_to_go:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ExerciseToGoActivity()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserProfile()).commit();
                break;
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrdersActivity()).commit();
                break;
            case R.id.nav_nutrition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NutritionActivity()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactActivity()).commit();
                break;
            case R.id.nav_coach:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CoachesActivity()).commit();
                break;
            case R.id.nav_information:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutUsActivity()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }
}
