package com.example.lucas.juridex_v13.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.MyTextView;

/**
 * Created by Lucas on 25/11/2017.
 */

public class FimJogoFragment extends Fragment{
    private static final String TAG = "FimJogoFragment";

    private TextView txtMensagem, txtResultado;
    private Button btnProximoTeste, btnVoltarTelaNiveis, btnReiniciarTeste;
    private MaterialDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fimteste, container, false);
        Log.d(TAG, "onCreateView: " + Common.getScore());

        //iniciar variaveis
        txtMensagem = view.findViewById(R.id.txtMensagem);
        txtResultado = view.findViewById(R.id.txtResultado);
        btnProximoTeste = view.findViewById(R.id.btnProximoTeste);
        btnVoltarTelaNiveis = view.findViewById(R.id.btnVoltarTelaNiveis);
        btnReiniciarTeste = view.findViewById(R.id.btnReiniciarTeste);
        
        //set textos
        setTxts();
        
        //cliques dos botões
        setClicks();
        
        //setar valores das mensagens
        
        

        return view;
    }

    public void limpaCommonVariaveis(){
        Common.setListaQuestoes(null);
        Common.setQuestaoAtual(null);
        Common.setAcertos(0);
        Common.setNivel(null);
        Common.setQtdTestesDificeis(0);
        Common.setQtdTestesFaceis(0);
        Common.setQtdTestesMedios(0);
        Common.setQuestoesJaLidas(null);
        Common.setQuestoesTotais(0);
        Common.setScore(0);
    }
    
    public void setTxts(){
        if(Common.getAcertos() == Common.getQuestoesTotais())
            txtMensagem.setText("UOU"); //acertou tudo
        else    
            if(Common.getAcertos() >= (Common.getQuestoesTotais()/2))
                txtMensagem.setText("Esta bom, mas pode melhorar!"); //acertou metade ou mais que a metade
            else
                txtMensagem.setText("Poxa, está quase lá!"); //acertou menos que a metade

        txtResultado.setText(Common.getAcertos() + "  /  " + Common.getQuestoesTotais());
        
    }

    public void setClicks(){
        
        btnProximoTeste.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view) {
                //settar variável comum referente ao nível
                if(Common.getNivel().equals("cdc_easy")){
                    Common.setNivel("cdc_medium");
                    //redireciona para a página de questões, porém no nível médio
                    ((GameActivity)getActivity()).setViewPager(0);
                }
                else
                    if(Common.getNivel().equals("cdc_medium")){
                        Common.setNivel("cdc_hard");
                        //redireciona para a página de questões, porém no nível médio
                        ((GameActivity)getActivity()).setViewPager(0);
                    }
                    else
                        if(Common.getNivel().equals("cdc_hard")){
                            //dialog, esperar próximos níveis
                             dialog = new MaterialDialog.Builder(getActivity())
                                .title("Você já fez todos os testes!")
                                .content("Aguarde novos e envie sugestões (:")
                                .positiveText("Fechar")
                                .show();
                            
                            dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                dialog.dismiss();
                                //encerra o fragment e volta para a tela de níveis(Game Activity)
                                getActivity().getFragmentManager().popBackStack();
                                }
                              });                            
                            }                
                
                Toast.makeText(getActivity(), "Próximo Teste (nível médio) ", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
        btnVoltarTelaNiveis.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view) {
               
                //encerra o fragment e volta para a tela de níveis(Game Activity)
                getActivity().getFragmentManager().popBackStack();
                
                Toast.makeText(getActivity(), "encerrou fragment", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
        btnReiniciarTeste.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view) {
               //redireciona para a página de questões no mesmo nível atual
               ((GameActivity)getActivity()).setViewPager(0);                
                
                Toast.makeText(getActivity(), "encerrou fragment", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
    }
    
   
}
