package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.Profile.EditProfileFragment;
import com.example.lucas.juridex_v13.Profile.SignOutFragment;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.SectionsStatePagerAdapter;
import com.example.lucas.juridex_v13.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.example.lucas.juridex_v13.Utils.BottomNavigationViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Lucas on 25/11/2017.
 */

public class GameActivity extends AppCompatActivity{
    private static final String TAG = "GameActivity";
    private static final int ACTIVITY_NUM = 0;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageButton btnFacilUm, btnMedioUm, btnDifilUm;
    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d(TAG, "onCreate: started");
        mViewPager = findViewById(R.id.container);
        mRelativeLayout = findViewById(R.id.relLayout2);

        btnFacilUm =  findViewById(R.id.btnfacilUm);
        btnMedioUm =  findViewById(R.id.btnmedioUm);
        btnDifilUm =  findViewById(R.id.btndificilUm);

        initImageLoader();

        setupFirebaseAuth();
        setupBottomNavigationView();
        setupFragments();

        btnFacilUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.setNivel("cdc_easy");
                setViewPager(0);
            }
        });

        btnMedioUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.setNivel("cdc_medium");
                setViewPager(0);
            }
        });

    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(GameActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new QuestaoFragment(), "questao1"); //fragment 0
        pagerAdapter.addFragment(new JustificativaFragment(), "justificativa"); //fragment 1
        pagerAdapter.addFragment(new FimJogoFragment(), "fim"); //fragment 2
    }

    public void setViewPager(int fragmentNumber){
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setupViewPager: navigation to fragment #:" + fragmentNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(GameActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);
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
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(GameActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
