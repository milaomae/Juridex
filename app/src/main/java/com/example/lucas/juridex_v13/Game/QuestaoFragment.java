package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.Utils.FirebaseMethods;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.MyButton;
import com.example.lucas.juridex_v13.Utils.MyTextView;
import com.example.lucas.juridex_v13.models.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by Lucas on 25/11/2017.
 */

public class QuestaoFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "QuestaoFragment";

    private MyButton btnA, btnB, btnC, btnD;
    private MyTextView txtQuestion, txtQuestaoAtual, txtNivelAtual;
    private int numQuestaoLista;
    private MaterialDialog dialog, dialog2;
    private int questoesCorretas, score = 0;
    private ProgressBar mProgressBar;
    private TextView mPleaseWait;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseMethods firebase;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: createView");
        View view = inflater.inflate(R.layout.fragment_pergunta, container, false);

        //iniciar variáveis
        mAuth = FirebaseAuth.getInstance();
        firebase = new FirebaseMethods(getActivity());

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

        btnA = (MyButton) view.findViewById(R.id.btnA);
        btnB = (MyButton) view.findViewById(R.id.btnB);
        btnC = (MyButton) view.findViewById(R.id.btnC);
        btnD = (MyButton) view.findViewById(R.id.btnD);
        txtQuestion = (MyTextView) view.findViewById(R.id.txtQuestion);
        txtQuestaoAtual = (MyTextView) view.findViewById(R.id.txtperguntaAtual);
        txtNivelAtual = (MyTextView) view.findViewById(R.id.txtnivelAtual);
        mProgressBar = view.findViewById(R.id.perguntaRequestLoadingProgressbar);
        mPleaseWait = view.findViewById(R.id.pleaseWait);

        if(Common.getNivel().equals("cdc_easy")){
            txtNivelAtual.setText("Fácil");}
        else
            if(Common.getNivel().equals("cdc_medium")){
                txtNivelAtual.setText("Médio");}
            else
                if(Common.getNivel().equals("cdc_medium")){
                    txtNivelAtual.setText("Difícil");}


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: onActivity created");
        super.onActivityCreated(savedInstanceState);
        setupFirebaseAuth();

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: on resume do fragment");



    }


    @Override
    public void onClick(View view) {
        Button btnGeneric = (Button) view;

        //acertou
        if (btnGeneric.getText().equals(Common.getListaQuestoes().get(numQuestaoLista).getCorrect())) {
            score += 10;
            questoesCorretas++;

            dialog = new MaterialDialog.Builder(getActivity())
                    .title("Você acertou!")
                    .titleColorRes(R.color.acertou)
                    .content("")
                    .positiveText("Próxima")
                    .negativeText("Ver Justificativa")
                    .show();
        } else {

            dialog = new MaterialDialog.Builder(getActivity())
                    .title("Você errou!")
                    .titleColorRes(R.color.errou)
                    .content("")
                    .positiveText("Próxima")
                    .negativeText("Ver Justificativa")
                    .show();

        }
        Common.setAlternativaEscolhida(btnGeneric.getText().toString());

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.setFoiTelaJustificativa(false);
                //quando terminar as questoes da lista
                if (Common.getQuestoesJaLidas().size() != Common.getListaQuestoes().size()) {
                    Log.d(TAG, "onClick: proxima pergunta");
                    setQuestions();

                } else {
                    score += Common.getScore();
                    questoesCorretas += Common.getAcertos();
                    Common.setScore(score);
                    Common.setAcertos(questoesCorretas);
                    ((GameActivity) getActivity()).setViewPager(2);

                }

                dialog.dismiss();
            }

        });

        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // justificativa = questaoF.getJustificativa();
                Common.setScore(score);
                Common.setAcertos(questoesCorretas);
                //((GameActivity) getActivity()).setViewPager(1);
                dialog2 = new MaterialDialog.Builder(getActivity())
                        .title("Justificativa")
                        .content(Common.getQuestaoAtual().getJustificativa())
                        .positiveText("Fechar")
                        .show();
                    dialog2.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog2.dismiss();
                        }
                    });

            }
        });
    }

    public void setQuestions(){
        //chossing random question;

        boolean encontrou;
        encontrou = false;

        while(!encontrou){
            numQuestaoLista = randomNumber(Common.getListaQuestoes().size());
            Log.d(TAG, "setQuestions: gera numero randomico " + numQuestaoLista);
            if(!Common.getQuestoesJaLidas().contains(numQuestaoLista)) {
                txtQuestaoAtual.setText((Common.getQuestoesJaLidas().size() + 1) + "  /  " + Common.getListaQuestoes().size());
                Common.getQuestoesJaLidas().add(numQuestaoLista);

                Question question = Common.getListaQuestoes().get(numQuestaoLista);
                Common.setQuestaoAtual(question);

                //initialize components
                txtQuestion.setText(question.getQuestion());
                btnA.setText(question.getAnswera());
                btnB.setText(question.getAnswerb());
                btnC.setText(question.getAnswerc());
                btnD.setText(question.getAnswerd());
                encontrou = true;
        }
        }

        mProgressBar.setVisibility(View.GONE);
        mPleaseWait.setVisibility(View.GONE);

        }


    public int randomNumber(int i){
        Random gerador = new Random();
        return gerador.nextInt(i);
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final String nivel = "";



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // Read from the database


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: loading question and choosing one");
                Common.setListaQuestoes(firebase.loadQuestions(Common.getNivel(), dataSnapshot));
                Common.setQuestoesTotais(Common.getListaQuestoes().size());
                setQuestions();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
