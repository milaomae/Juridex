package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.lucas.juridex_v13.Profile.EditProfileActivity;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.ConfirmPasswordDialog;
import com.example.lucas.juridex_v13.Utils.FirebaseMethods;
import com.example.lucas.juridex_v13.Utils.MyTextView;
import com.example.lucas.juridex_v13.models.User;
import com.example.lucas.juridex_v13.models.UserAccountSettings;
import com.example.lucas.juridex_v13.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Lucas on 25/11/2017.
 */

public class FimJogoFragment extends Fragment{
    private static final String TAG = "FimJogoFragment";

    private TextView txtMensagem, txtResultado;
    private Button btnProximoTeste, btnVoltarTelaNiveis, btnReiniciarTeste;
    private MaterialDialog dialog;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;
    private UserSettings mUserSettings;

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
        mFirebaseMethods = new FirebaseMethods(getActivity());
        mUserSettings = new UserSettings();

        //update score usuário
    setupFirebaseAuth();

        //set textos
        setTxts();

        //cliques dos botões
        setClicks();

        return view;
    }

    public void saveDataUsuario(){
        final long score = (long) Common.getScore();


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.child(userID).getChildren()){
                    if(Common.getNivel().equals("cdc_easy")){
                         mFirebaseMethods.updateUserAccountSettings(ds.getValue(UserAccountSettings.class).getPhoto() , score , ds.getValue(UserAccountSettings.class).getTestes_easy()+1,
                                 ds.getValue(UserAccountSettings.class).getTestes_medium(), ds.getValue(UserAccountSettings.class).getTestes_hard());
                    }
                    if(Common.getNivel().equals("cdc_medium")){
                        mFirebaseMethods.updateUserAccountSettings(ds.getValue(UserAccountSettings.class).getPhoto() , score*2 , ds.getValue(UserAccountSettings.class).getTestes_easy(),
                                ds.getValue(UserAccountSettings.class).getTestes_medium() + 1, ds.getValue(UserAccountSettings.class).getTestes_hard());
                    }
                    if(Common.getNivel().equals("cdc_hard")){
                        mFirebaseMethods.updateUserAccountSettings(ds.getValue(UserAccountSettings.class).getPhoto() , score*3 , ds.getValue(UserAccountSettings.class).getTestes_easy(),
                                ds.getValue(UserAccountSettings.class).getTestes_medium(), ds.getValue(UserAccountSettings.class).getTestes_hard()+1);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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
                    //setar valores das mensagens
                    Common.cleanCommonVariaveis();

                    Common.setNivel("cdc_medium");
                    //redireciona para a página de questões, porém no nível médio
                    ((GameActivity)getActivity()).setViewPager(0);

                }
                else
                    if(Common.getNivel().equals("cdc_medium")){
                        //setar valores das mensagens
                        Common.cleanCommonVariaveis();

                        Common.setNivel("cdc_hard");
                        //redireciona para a página de questões, porém no nível médio
                        ((GameActivity)getActivity()).setViewPager(0);

                    }
                    else
                        if(Common.getNivel().equals("cdc_hard")){
                            //setar valores das mensagens
                            Common.cleanCommonVariaveis();

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
                                    Intent intent = new Intent(getActivity(), GameActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                              });                            
                            }                
                
                //Toast.makeText(getActivity(), "Próximo Teste (nível médio) ", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
        btnVoltarTelaNiveis.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view) {
              //setar valores das mensagens
              Common.cleanCommonVariaveis();
                //encerra o fragment e volta para a tela de níveis(Game Activity)

              Intent intent = new Intent(getActivity(), GameActivity.class);
              startActivity(intent);
              getActivity().finish();
                
                //Toast.makeText(getActivity(), "encerrou fragment", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
        btnReiniciarTeste.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view) {
              //setar valores das mensagens
              Common.cleanCommonVariaveis();
               //redireciona para a página de questões no mesmo nível atual
               ((GameActivity)getActivity()).setViewPager(0);
                
               // Toast.makeText(getActivity(), "encerrou fragment", Toast.LENGTH_SHORT ).show();
            }
        
        });
        
    }

    //firebase

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
                    saveDataUsuario();


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

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
    }



}
