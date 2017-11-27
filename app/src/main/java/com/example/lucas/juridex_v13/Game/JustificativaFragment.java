package com.example.lucas.juridex_v13.Game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.MyTextView;

/**
 * Created by Lucas on 25/11/2017.
 */

public class JustificativaFragment extends Fragment{
    private static final String TAG = "JustificativaFragment";
    private String justificativa;

    private MyTextView just;
    private Button btnVoltar;

    @SuppressLint("ValidFragment")
    public JustificativaFragment(String justificativa) {
        this.justificativa = justificativa;
    }

    public JustificativaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: create justificativa fragment");
        View view = inflater.inflate(R.layout.fragment_justificativa, container, false);

        just = view.findViewById(R.id.txtJustificativa);
        just.setText(justificativa);

        btnVoltar = view.findViewById(R.id.btnVoltarJustificativa);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
