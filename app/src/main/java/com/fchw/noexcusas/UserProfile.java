package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class UserProfile extends Fragment {
    private
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    private AdView mAdView;

    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button btEdit, btRefresh, btnBack;
    ImageView avatarIv;
    TextView nameTv, emailTv,agetv, phoneTv, pesatv,
            tallatv, imctv, grasatv,
            musculotv, kcaltv, grasavitv, metaagetv, evaltit2tv,
            pesatv2,evaltit3tv,
            tallatv2, imctv2, grasatv2,
            musculotv2, kcaltv2, grasavitv2, metaagetv2,
            pesatv3,
            tallatv3, imctv3, grasatv3,
            musculotv3, kcaltv3, grasavitv3, metaagetv3,
            pesatv4,evaltit4tv,
            tallatv4, imctv4, grasatv4,
            musculotv4, kcaltv4, grasavitv4, metaagetv4,
            pesatv5,evaltit5tv,
            tallatv5, imctv5, grasatv5,
            musculotv5, kcaltv5, grasavitv5, metaagetv5,
            pesatv6,evaltit6tv,
            tallatv6, imctv6, grasatv6,
            musculotv6, kcaltv6, grasavitv6, metaagetv6;


public UserProfile(){

}


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_user_profile, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UsersSheets");

        //init views
        avatarIv =view.findViewById(R.id.avatarIv);
        nameTv =view.findViewById(R.id.nameTV);
        agetv=view.findViewById(R.id.ageTV);
        emailTv =view.findViewById(R.id.emailTV);
        phoneTv =view.findViewById(R.id.phoneTV);
        btRefresh =view.findViewById(R.id.button_refresh);

        //admob
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //personal evaluation
        pesatv =view.findViewById(R.id.pesaTV);
        tallatv =view.findViewById(R.id.tallaTV);
        imctv =view.findViewById(R.id.imcTV);
        grasatv =view.findViewById(R.id.grasaTV);
        musculotv =view.findViewById(R.id.musculoTV);
        kcaltv =view.findViewById(R.id.kcalTV);
        metaagetv=view.findViewById(R.id.metaage);
        grasavitv =view.findViewById(R.id.grasaviTV);


        //eval2

        evaltit2tv=view.findViewById(R.id.evaltit2);
        pesatv2 =view.findViewById(R.id.pesaTV2);
        tallatv2 =view.findViewById(R.id.tallaTV2);
        imctv2 =view.findViewById(R.id.imcTV2);
        grasatv2 =view.findViewById(R.id.grasaTV2);
        musculotv2 =view.findViewById(R.id.musculoTV2);
        kcaltv2 =view.findViewById(R.id.kcalTV2);
        metaagetv2=view.findViewById(R.id.metaage2);
        grasavitv2 =view.findViewById(R.id.grasaviTV2);

        //eval3

        evaltit3tv =view.findViewById(R.id.evaltit3);
        pesatv3 =view.findViewById(R.id.pesaTV3);
        tallatv3 =view.findViewById(R.id.tallaTV3);
        imctv3 =view.findViewById(R.id.imcTV3);
        grasatv3 =view.findViewById(R.id.grasaTV3);
        musculotv3 =view.findViewById(R.id.musculoTV3);
        kcaltv3 =view.findViewById(R.id.kcalTV3);
        metaagetv3 =view.findViewById(R.id.metaage3);
        grasavitv3 =view.findViewById(R.id.grasaviTV3);

        //eval4

        evaltit4tv=view.findViewById(R.id.evaltit4);
        pesatv4 =view.findViewById(R.id.pesaTV4);
        tallatv4 =view.findViewById(R.id.tallaTV4);
        imctv4 =view.findViewById(R.id.imcTV4);
        grasatv4 =view.findViewById(R.id.grasaTV4);
        musculotv4 =view.findViewById(R.id.musculoTV4);
        kcaltv4 =view.findViewById(R.id.kcalTV4);
        metaagetv4 =view.findViewById(R.id.metaage4);
        grasavitv4 =view.findViewById(R.id.grasaviTV4);

        //eval5

        evaltit5tv=view.findViewById(R.id.evaltit5);
        pesatv5 =view.findViewById(R.id.pesaTV5);
        tallatv5 =view.findViewById(R.id.tallaTV5);
        imctv5 =view.findViewById(R.id.imcTV5);
        grasatv5 =view.findViewById(R.id.grasaTV5);
        musculotv5 =view.findViewById(R.id.musculoTV5);
        kcaltv5 =view.findViewById(R.id.kcalTV5);
        metaagetv5=view.findViewById(R.id.metaage5);
        grasavitv5 =view.findViewById(R.id.grasaviTV5);

        //eval6

        evaltit6tv=view.findViewById(R.id.evaltit6);
        pesatv6 =view.findViewById(R.id.pesaTV6);
        tallatv6 =view.findViewById(R.id.tallaTV6);
        imctv6 =view.findViewById(R.id.imcTV6);
        grasatv6 =view.findViewById(R.id.grasaTV6);
        musculotv6 =view.findViewById(R.id.musculoTV6);
        kcaltv6 =view.findViewById(R.id.kcalTV6);
        metaagetv6=view.findViewById(R.id.metaage6);
        grasavitv6 =view.findViewById(R.id.grasaviTV6);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String email = ""+ds.child("email").getValue();



                    //set data
                    emailTv.setText(email);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query2 = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name = ""+ds.child("Name").getValue();
                    String age = ""+ds.child("age").getValue();
                    String phone = ""+ds.child("celular").getValue();
                    String image = ""+ds.child("Profile Image").getValue();
                    //evalutation
                    String pesa =  ""+ds.child("PESO").getValue();
                    String talla = ""+ds.child("TALLA").getValue();
                    String imc =   ""+ds.child("IMC").getValue();
                    String grasa = ""+ds.child("%GRASA").getValue();
                    String musculo = ""+ds.child("%MUSCULO").getValue();
                    String kcal = ""+ds.child("KCAL").getValue();
                    String edadmeta = ""+ds.child("EDAD METABOL").getValue();
                    String visgras = ""+ds.child("GRASA VICERAL").getValue();

                    //evalutation2
                    String pesa2 =  ""+ds.child("Peso2").getValue();
                    String talla2 = ""+ds.child("TALLA").getValue();
                    String imc2 =   ""+ds.child("IMC2").getValue();
                    String grasa2 = ""+ds.child("Grasa2").getValue();
                    String musculo2 = ""+ds.child("Musculo2").getValue();
                    String kcal2 = ""+ds.child("Kcal2").getValue();
                    String edadmeta2 = ""+ds.child("Edad Metabol2").getValue();
                    String visgras2 = ""+ds.child("Grasa Viceral2").getValue();
                    String date2 = ""+ds.child("Date2").getValue();

                    //evalutation3
                    String pesa3 =  ""+ds.child("Peso3").getValue();
                    String talla3 = ""+ds.child("TALLA").getValue();
                    String imc3 =   ""+ds.child("IMC3").getValue();
                    String grasa3 = ""+ds.child("Grasa3").getValue();
                    String musculo3 = ""+ds.child("Musculo3").getValue();
                    String kcal3 = ""+ds.child("Kcal3").getValue();
                    String edadmeta3 = ""+ds.child("Edad Metabol3").getValue();
                    String visgras3 = ""+ds.child("Grasa Viceral3").getValue();
                    String date3 = ""+ds.child("Date3").getValue();

                    //evalutation4
                    String pesa4 =  ""+ds.child("Peso4").getValue();
                    String talla4 = ""+ds.child("TALLA").getValue();
                    String imc4 =   ""+ds.child("IMC4").getValue();
                    String grasa4 = ""+ds.child("Grasa4").getValue();
                    String musculo4 = ""+ds.child("Musculo4").getValue();
                    String kcal4 = ""+ds.child("Kcal4").getValue();
                    String edadmeta4 = ""+ds.child("Edad Metabol4").getValue();
                    String visgras4 = ""+ds.child("Grasa Viceral4").getValue();
                    String date4 = ""+ds.child("Date4").getValue();

                    //evalutation5
                    String pesa5 =  ""+ds.child("Peso5").getValue();
                    String talla5 = ""+ds.child("TALLA").getValue();
                    String imc5 =   ""+ds.child("IMC5").getValue();
                    String grasa5 = ""+ds.child("Grasa5").getValue();
                    String musculo5 = ""+ds.child("Musculo5").getValue();
                    String kcal5 = ""+ds.child("Kcal5").getValue();
                    String edadmeta5 = ""+ds.child("Edad Metabol5").getValue();
                    String visgras5 = ""+ds.child("Grasa Viceral5").getValue();
                    String date5 = ""+ds.child("Date5").getValue();

                    //evalutation6
                    String pesa6 =  ""+ds.child("Peso6").getValue();
                    String talla6 = ""+ds.child("TALLA").getValue();
                    String imc6 =   ""+ds.child("IMC6").getValue();
                    String grasa6 = ""+ds.child("Grasa6").getValue();
                    String musculo6 = ""+ds.child("Musculo6").getValue();
                    String kcal6 = ""+ds.child("Kcal6").getValue();
                    String edadmeta6 = ""+ds.child("Edad Metabol6").getValue();
                    String visgras6 = ""+ds.child("Grasa Viceral6").getValue();
                    String date6 = ""+ds.child("Date6").getValue();




                    //set data eval1
                    nameTv.setText(name);
                    agetv.setText(age);
                    phoneTv.setText(phone);
                    pesatv.setText(pesa);
                    tallatv.setText(talla);
                    imctv.setText(imc);
                    grasatv.setText(grasa);
                    musculotv.setText(musculo);
                    kcaltv.setText(kcal);
                    metaagetv.setText(edadmeta);
                    grasavitv.setText(visgras);


                    //eval2
                    pesatv2.setText(pesa2);
                    tallatv2.setText(talla2);
                    imctv2.setText(imc2);
                    grasatv2.setText(grasa2);
                    musculotv2.setText(musculo2);
                    kcaltv2.setText(kcal2);
                    metaagetv2.setText(edadmeta2);
                    grasavitv2.setText(visgras2);
                    evaltit2tv.setText(date2);

                    //eval3
                    pesatv3.setText(pesa3);
                    tallatv3.setText(talla3);
                    imctv3.setText(imc3);
                    grasatv3.setText(grasa3);
                    musculotv3.setText(musculo3);
                    kcaltv3.setText(kcal3);
                    metaagetv3.setText(edadmeta3);
                    grasavitv3.setText(visgras3);
                    evaltit3tv.setText(date3);

                    //eval4
                    pesatv4.setText(pesa4);
                    tallatv4.setText(talla4);
                    imctv4.setText(imc4);
                    grasatv4.setText(grasa4);
                    musculotv4.setText(musculo4);
                    kcaltv4.setText(kcal4);
                    metaagetv4.setText(edadmeta4);
                    grasavitv4.setText(visgras4);
                    evaltit4tv.setText(date4);

                    //eval5
                    pesatv5.setText(pesa5);
                    tallatv5.setText(talla5);
                    imctv5.setText(imc5);
                    grasatv5.setText(grasa5);
                    musculotv5.setText(musculo5);
                    kcaltv5.setText(kcal5);
                    metaagetv5.setText(edadmeta5);
                    grasavitv5.setText(visgras5);
                    evaltit5tv.setText(date5);

                    //eval6
                    pesatv6.setText(pesa6);
                    tallatv6.setText(talla6);
                    imctv6.setText(imc6);
                    grasatv6.setText(grasa6);
                    musculotv6.setText(musculo6);
                    kcaltv6.setText(kcal6);
                    metaagetv6.setText(edadmeta6);
                    grasavitv6.setText(visgras6);
                    evaltit6tv.setText(date6);


                    try {
                        // if image is received then set
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_add_photo).into(avatarIv);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new UserProfile())
                        .commit();


            }
        });

        return view;



    }
     



        }








/*
        };

        return v;

    }@Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}
*/


//---------------------------------------------------------------------------

/*final EditText mChildValueEditText = v.findViewById(R.id.childValueEditText);
        mAddButton = v.findViewById(R.id.addButton);
        mRemoveButton = v.findViewById(R.id.removeButton);
        mchildValueTextView = v.findViewById(R.id.childValueTextView);*/
// muserAge = v.findViewById(R.id.userAge);
//  muserName = v.findViewById(R.id.userName);
//  muserSex = v.findViewById(R.id.userSex);

//  mAuth = FirebaseAuth.getInstance();
      /*  firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference mRef = database.getReference("simCoder");

               /* mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String childValue = mChildValueEditText.getText().toString();
                        mRef.setValue(childValue);

                    }
                });
                mRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRef.removeValue();

                    }
                });

                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String childValue = String.valueOf(dataSnapshot.getValue());
                        mchildValueTextView.setText(childValue);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

//---------------------------------------------------------------------------

              /*  mAuth = FirebaseAuth.getInstance();
                firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
@Override
public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
final DatabaseReference mRef = database.getReference("Users");*/