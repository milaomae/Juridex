package com.example.lucas.juridex_v13;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.example.lucas.juridex_v13.Utils.BottomNavigationViewHelper;

/**
 * Created by Lucas on 04/11/2017.
 */

public class InicioActivity extends AppCompatActivity{

  //  @BindView(R.id.toolbar)
  //  Toolbar toolbar;
    private static final String TAG = "InicioActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting.");


        setupBottomNavigationView();
    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

    }



}

