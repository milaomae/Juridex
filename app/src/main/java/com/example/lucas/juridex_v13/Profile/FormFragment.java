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
import android.widget.ImageView;

import com.example.lucas.juridex_v13.R;

/**
 * Created by comae on 30/11/2017.
 */

public class FormFragment extends Fragment {
    private static final String TAG = "FormFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_sugestoes, container, false);

        //back arrow for navigating back to "Profile Activity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
