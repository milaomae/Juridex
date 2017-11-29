package com.example.lucas.juridex_v13.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.MyTextView;

/**
 * Created by Lucas on 25/11/2017.
 */

public class JustificativaFragment extends Fragment{
    private static final String TAG = "JustificativaFragment";

    private MyTextView txtJustificativa;
    private Button btnVoltarJustificativa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_justificativa, container, false);

        txtJustificativa = view.findViewById(R.id.txtJustificativa);
        btnVoltarJustificativa = view.findViewById(R.id.btnVoltarJustificativa);

        if(Common.getQuestaoAtual() != null)
            txtJustificativa.setText(Common.getQuestaoAtual().getJustificativa());

        btnVoltarJustificativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity)getActivity()).setViewPager(0);
                onDestroyView();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: destroying fragment justificativa");
    }
}
