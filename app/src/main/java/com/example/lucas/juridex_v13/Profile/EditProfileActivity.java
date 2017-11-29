package com.example.lucas.juridex_v13.Profile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.Permissions;
import com.example.lucas.juridex_v13.Utils.SectionsStatePagerAdapter;
import com.example.lucas.juridex_v13.Utils.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by comae on 29/11/2017.
 */

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileFragment";

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    private TextView changeProfilePhoto;
    private CircleImageView profile_photo;
    private ProgressBar mProgressBar;

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editprofile);

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
        //mProgressBar = view.findViewById(R.id.loginRequestLoadingProgressbar);

        setupFragments();
        setProfileImage();


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

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgUrl = "pbs.twimg.com/profile_images/520912344486920192/ATAJ_Rr7_400x400.jpeg";
        UniversalImageLoader.setImage(imgUrl, profile_photo, null, "https://");
    }


}
