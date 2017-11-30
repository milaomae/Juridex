package com.example.lucas.juridex_v13.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.Game.GameActivity;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.FirebaseMethods;
import com.example.lucas.juridex_v13.Utils.UniversalImageLoader;
import com.example.lucas.juridex_v13.models.User;
import com.example.lucas.juridex_v13.models.UserAccountSettings;
import com.example.lucas.juridex_v13.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.example.lucas.juridex_v13.Utils.BottomNavigationViewHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucas on 25/11/2017.
 */

public class ProfileActivity extends AppCompatActivity{
    private static final String TAG = "ProfileActivity";

    private static final int ACTIVITY_NUM = 1;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    private ProgressBar mProgressBar;

    private TextView txtNome, txtExperiencia, txtQtdTotalTestes, txtFacil, txtMedio, txtDificil;
    private CircleImageView mProfilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }

        txtNome = findViewById(R.id.txtNome);
        txtExperiencia = findViewById(R.id.txtExperiencia);
        txtQtdTotalTestes = findViewById(R.id.txtQtdTotalTestes);
        txtFacil = findViewById(R.id.txtFacil);
        txtMedio = findViewById(R.id.txtMedio);
        txtDificil = findViewById(R.id.txtDificil);



        mFirebaseMethods = new FirebaseMethods(ProfileActivity.this);

        setupActivityWidgets();
        setupBottomNavigationView();
        setupToolBar();
        setProfileImage();

        setupFirebaseAuth();
    }

    private void setupActivityWidgets(){
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProfilePhoto = findViewById(R.id.profileImage);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setupToolBar(){
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigation toolbar profile menu");
                Intent intent = new Intent(ProfileActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgUrl = "pbs.twimg.com/profile_images/520912344486920192/ATAJ_Rr7_400x400.jpeg";
        UniversalImageLoader.setImage(imgUrl, mProfilePhoto, null, "https://");
    }

    public void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());

        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getPhoto(), mProfilePhoto, null, "");

        txtNome.setText(user.getUsername());
        txtExperiencia.setText(String.valueOf(settings.getScore()));
        txtQtdTotalTestes.setText(String.valueOf(settings.getTestes_easy() + settings.getTestes_medium() + settings.getTestes_hard()));
        txtFacil.setText(String.valueOf(settings.getTestes_easy()));
        txtMedio.setText(String.valueOf(settings.getTestes_medium()));
        txtDificil.setText(String.valueOf(settings.getTestes_hard()));


    }

    //firebase

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
   }
