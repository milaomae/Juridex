package com.example.lucas.juridex_v13.Profile;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.Permissions;
import com.example.lucas.juridex_v13.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lucas on 26/11/2017.
 */

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private TextView changeProfilePhoto;
    private CircleImageView profile_photo;
    private ProgressBar mProgressBar;

    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);


        changeProfilePhoto = view.findViewById(R.id.changeProfilePhoto);
        profile_photo = view.findViewById(R.id.profile_photo);
        //mProgressBar = view.findViewById(R.id.loginRequestLoadingProgressbar);

        changeProfilePhoto.setVisibility(View.INVISIBLE);
        setProfileImage();

        //se as permissões estiverem habilitadas
        if(checkPermissionsArray(Permissions.PERMISSIONS)){
            changeProfilePhoto.setVisibility(View.VISIBLE);
        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }

        //back arrow for navigating back to "Profile Activity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        changeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AccountSettingsActivity)getActivity()).setViewPager(2);


            }
        });

        return view;
    }


    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                getActivity(),
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

        int permissionRequest = ActivityCompat.checkSelfPermission(getActivity(), permission);

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
