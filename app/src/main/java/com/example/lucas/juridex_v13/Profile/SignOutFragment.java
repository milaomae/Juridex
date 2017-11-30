package com.example.lucas.juridex_v13.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Lucas on 26/11/2017.
 */

public class SignOutFragment extends Fragment {
    private static final String TAG = "SignOutFragment";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView txtSignOut, txtConfirmSignOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout, container, false);
        txtConfirmSignOut = (TextView) view.findViewById(R.id.txtConfirmSignOut);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Button btnConfirmSignOut = (Button) view.findViewById(R.id.btnConfirmSignOut);
        txtSignOut = (TextView) view.findViewById(R.id.txtSignOut);

        mProgressBar.setVisibility(View.GONE);
        txtSignOut.setVisibility(View.GONE);

        setupFirebaseAuth();

        btnConfirmSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to sign out.");
                mProgressBar.setVisibility(View.VISIBLE);
                txtSignOut.setVisibility(View.VISIBLE);

                //setta as variáveis do usuário quando ele desloga...
                Common.setScoreAntigo(0);
                Common.settEasy(0);
                Common.settHard(0);
                Common.settMedium(0);

                mAuth.signOut();
                getActivity().finish();
            }
        });

        return view;
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Log.d(TAG, "onAuthStateChanged: navigation back to login screen");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                // ...
            }
        };
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
