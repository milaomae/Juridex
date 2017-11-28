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

public class FimJogoFragment extends Fragment{
    private static final String TAG = "FimJogoFragment";

    private MyTextView txtMensagem, result;
    private Button btnProximoTeste, btnVoltarTelaNiveis, btnReiniciarTeste;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fimteste, container, false);
        Log.d(TAG, "onCreateView: " + Common.getScore());

        //iniciar variaveis
        txtMensagem = view.findViewById(R.id.txtMensagem);
        result = view.findViewById(R.id.result);
        btnProximoTeste = view.findViewById(R.id.btnProximoTeste);
        btnVoltarTelaNiveis = view.findViewById(R.id.btnVoltarJustificativa);
        btnReiniciarTeste = view.findViewById(R.id.btnReiniciarTeste);

        return view;
    }

    public void setClicks(){

    }
}
