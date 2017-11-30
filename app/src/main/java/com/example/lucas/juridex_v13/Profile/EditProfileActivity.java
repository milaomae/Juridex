package com.example.lucas.juridex_v13.Profile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.ConfirmPasswordDialog;
import com.example.lucas.juridex_v13.Utils.FirebaseMethods;
import com.example.lucas.juridex_v13.Utils.Permissions;
import com.example.lucas.juridex_v13.Utils.SectionsStatePagerAdapter;
import com.example.lucas.juridex_v13.Utils.UniversalImageLoader;
import com.example.lucas.juridex_v13.models.User;
import com.example.lucas.juridex_v13.models.UserAccountSettings;
import com.example.lucas.juridex_v13.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by comae on 29/11/2017.
 */

public class EditProfileActivity extends AppCompatActivity  implements
    ConfirmPasswordDialog.OnConfirmPasswordListener{

        @Override
        public void onConfirmPassword(String password) {
            Log.d(TAG, "onConfirmPassword: got the password: " + password);

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(mAuth.getCurrentUser().getEmail(), password);

            ///////////////////// Prompt the user to re-provide their sign-in credentials
            mAuth.getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "User re-authenticated.");

                                ///////////////////////check to see if the email is not already present in the database
                                mAuth.fetchProvidersForEmail(changeEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                        if(task.isSuccessful()){
                                            try{
                                                if(task.getResult().getProviders().size() == 1){
                                                    Log.d(TAG, "onComplete: that email is already in use.");
                                                    Toast.makeText(EditProfileActivity.this, "That email is already in use", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Log.d(TAG, "onComplete: That email is available.");

                                                    //////////////////////the email is available so update it
                                                    mAuth.getCurrentUser().updateEmail(changeEmail.getText().toString())
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.d(TAG, "User email address updated.");
                                                                        Toast.makeText(EditProfileActivity.this, "email updated", Toast.LENGTH_SHORT).show();
                                                                        mFirebaseMethods.updateEmail(changeEmail.getText().toString());
                                                                    }
                                                                }
                                                            });
                                                }
                                            }catch (NullPointerException e){
                                                Log.e(TAG, "onComplete: NullPointerException: "  +e.getMessage() );
                                            }
                                        }
                                    }
                                });





                            }else{
                                Log.d(TAG, "onComplete: re-authentication failed.");
                            }

                        }
                    });
        }


        private static final String TAG = "EditProfileFragment";

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;
    private UserSettings mUserSettings;

    private TextView changeProfilePhoto;
    private CircleImageView profile_photo;
    private ProgressBar mProgressBar;
    private EditText changeEmail, changeUsername;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editprofile);

        mFirebaseMethods = new FirebaseMethods(EditProfileActivity.this);

        //se as permissões estiverem habilitadas
        if(checkPermissionsArray(Permissions.PERMISSIONS)){
            Log.d(TAG, "onCreate: tem permissão");
        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relLayout1);

        changeProfilePhoto = findViewById(R.id.changeProfilePhoto);
        profile_photo = findViewById(R.id.profile_photo);
        changeEmail = findViewById(R.id.changeEmail);
        changeUsername = findViewById(R.id.changeUsername);
        //mProgressBar = view.findViewById(R.id.loginRequestLoadingProgressbar);

        setupFragments();
        //setProfileImage();
        setupFirebaseAuth();

        //back arrow for navigating back to "Profile Activity"
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                Intent intent = new Intent(EditProfileActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              setViewPager(0);
            }
        });

        ImageView saveChanges = (ImageView) findViewById(R.id.saveChanges);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
            }
        });

    }



    private void saveProfileSettings(){
        final String displayUsername = changeUsername.getText().toString();
        final String displayEmail = changeEmail.getText().toString();
        final String displayPhoto = profile_photo.toString();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //case1 the user made a change to their username
                if(mUserSettings.getUser().getUsername() != null) {
                    if (!mUserSettings.getUser().getUsername().equals(displayUsername)) {
                        checkIfUsernameExists(displayUsername);
                    }
                }
                else
                    checkIfUsernameExists(displayUsername);
                //case 2 if the user made a change to their email
                if(!mUserSettings.getUser().getEmail().equals(displayEmail)){
                    //Reautentica
                        //confirma a senha e email
                        FragmentManager fm = getSupportFragmentManager();

                    ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
                    dialog.show(fm, getString(R.string.confirm_password_dialog));
                    //dialog.setTargetFragment(fm, 1);

                    //veifica se email já existe no bd
                        //fetchProvideForEmail(String email)
                    //troca o email
                        //submit the new email to the databse and authentication

                }

                if(!mUserSettings.getSettings().getPhoto().equals(displayPhoto)){
                    //update profile_photo
                    mFirebaseMethods.updateUserAccountSettings(displayPhoto, 0, 0, 0, 0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());

        mUserSettings = userSettings;

        UserAccountSettings settings = new UserAccountSettings();
        User user = new User();

        UniversalImageLoader.setImage(settings.getPhoto(), profile_photo, null, "");

        changeUsername.setText(user.getUsername().toString());
        changeEmail.setText(user.getEmail().toString());

    }

    private void setupFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new GalleryFragment(), "galeria"); //fragment 0


    }

    public void setViewPager(int fragmentNumber){
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setupViewPager: navigation to fragment #:" + fragmentNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                EditProfileActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(EditProfileActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }

   // private void setProfileImage(){
   //     Log.d(TAG, "setProfileImage: setting profile image.");
   //     String imgUrl = "pbs.twimg.com/profile_images/520912344486920192/ATAJ_Rr7_400x400.jpeg";
   //     UniversalImageLoader.setImage(imgUrl, profile_photo, null, "https://");
   // }

    //firebase


    private void checkIfUsernameExists(final String username){
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //add username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(EditProfileActivity.this, "Saved username", Toast.LENGTH_SHORT).show();

                }
                else{
                    for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                        if(singleSnapshot.exists()){
                            Log.d(TAG, "onDataChange: FOUND A MATCH " + singleSnapshot.getValue(User.class).getUsername());
                            Toast.makeText(EditProfileActivity.this, "That username already exits", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

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
        this.finish();
    }


}
