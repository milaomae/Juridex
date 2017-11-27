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

public class JustificativaFragment extends Fragment{
    private static final String TAG = "JustificativaFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_justificativa, container, false);
        return view;
    }
}
