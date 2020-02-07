package com.fchw.noexcusas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class UserProfile extends Fragment {
    private
            //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    //storage
    StorageReference storageReference;
    //path where images of user profile and cover will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";


    private AdView mAdView;

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceuid;

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

    FloatingActionButton fab;

    // progress dialog
    ProgressDialog pd;

    // permissions constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    //arrays of permissions to be requested
    String cameraPermissions[];
    String storagePermissions[];

    //uri of picked image
    Uri image_uri;

    //for checking profile or cover photo
    String profileOrCoverPhoto;


public UserProfile(){

}


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_user_profile, container, false);



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceuid = firebaseDatabase.getReference("Users");
        databaseReference = firebaseDatabase.getReference("UsersSheets");

        storageReference = getInstance().getReference(); //firebase storage reference

        //init arrays of permissions
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //init views
        avatarIv =view.findViewById(R.id.avatarIv);
        nameTv =view.findViewById(R.id.nameTV);
        agetv=view.findViewById(R.id.ageTV);
        emailTv =view.findViewById(R.id.emailTV);
        phoneTv =view.findViewById(R.id.phoneTV);
        fab = view.findViewById(R.id.fab);

        //init progress dialog

        pd = new ProgressDialog(getActivity());

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

        Query query = databaseReferenceuid.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checkc until requiered data get
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String image = ""+ds.child("image").getValue();



                    //set data

                   /*Picasso.get()
                            .load(image) // web image url
                            .fit().centerInside()
                            .rotate(90)                    //if you want to rotate by 90 degrees
                            .error(R.drawable.ic_default_img_white)
                            .placeholder(R.drawable.ic_default_img_white)
                            .into(avatarIv);*/


                    Glide.with(UserProfile.this)
                            .load(image) // Uri of the picture
                            .into(avatarIv);


                  /*  try {
                        // if image is received then set
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e){
                        //if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.ic_default_img_white).into(avatarIv);

                    }*/


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
                    String email = ""+ds.child("email").getValue();

                    String name = ""+ds.child("Name").getValue();
                    String age = ""+ds.child("age").getValue();
                    String phone = ""+ds.child("celular").getValue();
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
                    emailTv.setText(email);
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



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //fab button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });






        return view;



    }

    private boolean checkStoragePermission(){
        //check if storage permission is enabled or not
        //return true if enabled
        // return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        //request runtime storage permission
        requestPermissions(storagePermissions, STORAGE_REQUEST_CODE);
    }



    private boolean checkCameraPermission(){
        //check if storage permission is enabled or not
        //return true if enabled
        // return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result;
    }

    private void requestCameraPermission(){
        //request runtime storage permission
        requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE);
    }



    private void showEditProfileDialog() {
    /* Show dialog containing options
    * 1) Edit Profile Picture
    * 2) Edit Cover Photo (I personally will not use the rest)
    * 3) Edit Name
    * 4) Edit Phone  */

    //options to show in dialog
        String options [] = {getString(R.string.editpropic)};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle(getString(R.string.chooseAction));
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // handle dialog item clicks
                if (which == 0) {
                    //Edit Profile clicked
                    pd.setMessage(getString(R.string.updatingPhoto));
                    profileOrCoverPhoto = "image";//i.e. changing profile picture, make sure to assign same value
                    showImagePicDialog();
                }
                else if (which == 1) {
                    // Edit Cover clicked
                    pd.setMessage("Updating Cover Picture");
                    profileOrCoverPhoto = "cover";//i.e. changing cover, make sure to assign same value
                    showImagePicDialog();
                }
                else if (which == 2) {
                    // Edit Name clicked
                }
                else if (which == 3) {
                    // Edit Phone clicked
                }


            }
        });
        // create and show dialog
        builder.create().show();

    }

    private void showImagePicDialog() {
        //show dialog containing options Camera and Gallery to pick the image

        String options [] = {(getString(R.string.camera)),(getString(R.string.gallery))};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle(getString(R.string.choosefrom));
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // handle dialog item clicks
                if (which == 0) {
                    //Camera  clicked

                    if (!checkCameraPermission()){
                        requestCameraPermission();

                    }
                    else {
                        pickFromCamera();
                    }
                }
                else if (which == 1) {
                    // Gallery clicked

                    if (!checkStoragePermission()){
                        requestStoragePermission();

                    } else {
                        pickFromGallery();
                    }
                }


            }
        });
        // create and show dialog
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*This method called when the user press Allow or Deny from permission request dialog
        *here we will handle permission cases (allowed & denied )  */

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                //picking from camera, first check if camera and storage permissions allowed or not
                if (grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        //permissions enabled
                        pickFromCamera();
                    }
                    else {
                        //permissions denied
                        Toast.makeText(getActivity(), (getString(R.string.cameraPermission)), Toast.LENGTH_SHORT).show();

                    }
                }


            }
            break;

            case STORAGE_REQUEST_CODE:{


                //picking from gallery, first check if  storage permissions allowed or not
                if (grantResults.length >0){
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        //permissions enabled
                        pickFromGallery();
                    }
                    else {
                        //permissions denied
                        Toast.makeText(getActivity(), (getString(R.string.storagePermission)), Toast.LENGTH_SHORT).show();

                    }
                }
            }
            break;
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*This method will be called after picking image from Camera or Gallery*/
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //image is picked from gallery, get uri of image
                image_uri = data.getData();

                uploadProfileCoverPhoto(image_uri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //image is picked from camera, get uri of image

                uploadProfileCoverPhoto(image_uri);




            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(Uri uri) {
    //show progress
        pd.show();
    /*Instead of creating separate function for Profile Picture and Cover Photo
    *I'm doing work for both in same function
    *
    * To add check i'll add a string variable and assign it value "image" when user clicks
    * "Edit Profile Pic", and assign it value "cover" when user clicks "Edit Cover Photo"
    * Here: image is the key in each user containing url of user's profile picture
    *       cover is the key in each user containing url of user's cover photo */


    /*The parameter "image_uri" contains the uri of image picked either from camera or gallery
    * We will use UID (***I will use email)of the currently signed in user as name of the image so there will be only one image
    * profile and one image for cover for each user*/


    //path and name of image to be stored in firebase storage
        String filePathAndName = storagePath+ ""+ profileOrCoverPhoto +"_"+user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // image is uploaded to storage, now get it's url and store in user's database
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        //Check if image is uploaded or not and url is received
                        if(uriTask.isSuccessful()){
                            //image uploaded
                            //add/update url in user's database
                            HashMap<String, Object> results = new HashMap<>();
                            /*First Parameter is profileOrCoverPhoto that has value "image" or "cover"
                            which are keys in user's database where url of image will be saved in one
                            of them
                            Second Parameter contains the url of the image stored in firebase storage, this
                            url will be saved as value against key "image" or "cover"*/
                            results.put(profileOrCoverPhoto, downloadUri.toString());

                            databaseReferenceuid.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //url in database of user is added successfully
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), getString(R.string.imageUpdated),Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //error adding url in databse of user
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), getString(R.string.erroruploadimage),Toast.LENGTH_SHORT).show();

                                        }
                                    });

                        }
                        else {
                            //error
                            pd.dismiss();
                            Toast.makeText(getActivity(),"Some error occured", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //there were some error(s), get and show error message, dismiss progress dialog
                        pd.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



    }

    private void pickFromCamera() {
    // Intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        image_uri = getActivity() .getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {
    //pick from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    }
}




