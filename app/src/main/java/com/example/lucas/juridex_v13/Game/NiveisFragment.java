package com.example.lucas.juridex_v13.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lucas.juridex_v13.R;

/**
 * Created by Lucas on 25/11/2017.
 */

public class NiveisFragment extends Fragment{
    private static final String TAG = "NiveisFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_niveis, container, false);
        return view;
    }
}
