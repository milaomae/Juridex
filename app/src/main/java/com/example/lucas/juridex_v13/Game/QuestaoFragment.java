package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.lucas.juridex_v13.FirebaseMethods;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lucas on 25/11/2017.
 */

public class QuestaoFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "QuestaoFragment";

    private MyButton btnA, btnB, btnC, btnD;
    private MyTextView txtQuestion, txtQuestaoAtual, txtNivelAtual;
    private int numQuestaoLista;
    private MaterialDialog dialog;
    private int questoesCorretas, score;
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
        View view = inflater.inflate(R.layout.fragment_pergunta, container, false);


        //iniciar variáveis
        score = 0;
        questoesCorretas = 0;

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

        //if(nivel.equals("cdc_easy"))
        txtNivelAtual.setText("Fácil");

        setupFirebaseAuth();


        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        Button btnGeneric = (Button) view;
        
        if(btnGeneric.getText().equals(Common.getListaQuestoes().get(numQuestaoLista).getCorrect())){

            dialog = new MaterialDialog.Builder(getActivity())
                    .title("Você acertou!")
                    .content("")
                    .positiveText("Próxima")
                    .negativeText("Ver Justificativa")
                    .show();
        }
        else{

            dialog = new MaterialDialog.Builder(getActivity())
                    .title("Você errou!")
                    .content("")
                    .positiveText("Próxima")
                    .negativeText("Ver Justificativa")
                    .show();

        }

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //quando terminar as questoes da lista
                if(Common.getQuestoesJaLidas().size() != Common.getListaQuestoes().size()){
                    Log.d(TAG, "onClick: proxima pergunta");
                    score += 10;
                    questoesCorretas++;
                    setQuestions();

                }
                else{
                    Common.setScore(score);
                    Common.setAcertos(questoesCorretas);
                    ((GameActivity)getActivity()).setViewPager(2);}


            }
        });
        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               // justificativa = questaoF.getJustificativa();
                ((GameActivity)getActivity()).setViewPager(1);
                Toast.makeText(getActivity(), "justificativa", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void setQuestions(){
        //chossing random question
        Random gerador = new Random();
        boolean encontrou = false;
        numQuestaoLista = gerador.nextInt(Common.getListaQuestoes().size());
        while(!encontrou){
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

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
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
                //check if the user is logged in
                checkCurrentUser(user);
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // Read from the database
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: loading question and choosing one");
                            Common.setListaQuestoes(firebase.loadQuestions("cdc_easy", dataSnapshot));
                            setQuestions();

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

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
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
