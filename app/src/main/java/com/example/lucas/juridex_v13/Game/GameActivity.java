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
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.FirebaseMethods;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.SectionsStatePagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.example.lucas.juridex_v13.Utils.BottomNavigationViewHelper;

/**
 * Created by Lucas on 25/11/2017.
 */

public class GameActivity extends AppCompatActivity{
    private static final String TAG = "GameActivity";
    private static final int ACTIVITY_NUM = 0;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private ImageButton btnFacilUm, btnMedioUm, btnDifilUm;
    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    private DataFromActivityToFragment nivel;
    private String nivelstring;

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

        firebaseMethods = new FirebaseMethods(GameActivity.this);

        setupFirebaseAuth();


        setupBottomNavigationView();
        setupFragments();


        btnFacilUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivelstring = "cdc_easy";
                setViewPager(0);
            }
        });

        btnMedioUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivelstring = "cdc_medium";
                setViewPager(1);
            }
        });

        btnDifilUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nivelstring = "cdc_hard";
                setViewPager(2);
            }
        });

        final Runnable r = new Runnable() {
            public void run() {
                nivel.sendData(nivelstring);
            }
        };

    }

    private void setupFragments(){
        QuestaoFacilFragment fragmentQ = new QuestaoFacilFragment();
        nivel = (DataFromActivityToFragment) fragmentQ;
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(fragmentQ, "questaoF"); //fragment 0
        pagerAdapter.addFragment(new QuestaoFacilFragment(), "questaoM"); //fragment 1
        pagerAdapter.addFragment(new QuestaoFacilFragment(), "questaoH"); //fragment 2
        pagerAdapter.addFragment(new JustificativaFragment(), "justificativa"); //fragment 3
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

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference("questions");

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

    //transferir dados para o fragment
    public interface DataFromActivityToFragment {
        void sendData(String data);
    }
}
