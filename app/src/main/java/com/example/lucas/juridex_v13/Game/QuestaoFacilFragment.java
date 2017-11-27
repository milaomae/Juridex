package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lucas.juridex_v13.Common.Common;
import com.example.lucas.juridex_v13.FirebaseMethods;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
import com.example.lucas.juridex_v13.Utils.MyTextView;
import com.example.lucas.juridex_v13.Utils.SectionsStatePagerAdapter;
import com.example.lucas.juridex_v13.models.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sql.CommonDataSource;

/**
 * Created by Lucas on 25/11/2017.
 */

public class QuestaoFacilFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "QuestaoFacilFragment";

    private Button btnA, btnB, btnC, btnD;
    private MyTextView txtQuestion;
    private MyTextView txtNivelAtual, txtPerguntaInfo;

    private int totalQuestoes;
    private MaterialDialog dialog;
    private String justificativa;
    private int numero;
    private Question questaoF;
    private CountDownTimer mCountDown;
    private int index, score, correctAnswer, questaoAtual;

    private SectionsStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

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
        mAuth = FirebaseAuth.getInstance();
        firebase = new FirebaseMethods(getActivity());
        setRetainInstance(true);

        index = 1;
        score = 0;
        correctAnswer = 0;
        questaoAtual = 0;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

        mViewPager = view.findViewById(R.id.container);
        mRelativeLayout = view.findViewById(R.id.tela_questao_container);

        btnA = view.findViewById(R.id.btnA);
        btnB = view.findViewById(R.id.btnB);
        btnC = view.findViewById(R.id.btnC);
        btnD = view.findViewById(R.id.btnD);

        txtQuestion = view.findViewById(R.id.txtQuestion);
        txtNivelAtual = view.findViewById(R.id.txtnivelAtual);
        txtPerguntaInfo = view.findViewById(R.id.txtperguntaInfo);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        setupFirebaseAuth();
        setupFragments();

        return view;
    }
    @Override
    public void onClick(View view) {
        mCountDown.cancel();
        if(index < totalQuestoes){
            Button clickedButton = (Button) view;

            //alternativa correta
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrect())){
                score += 10;
                correctAnswer++;
                showQuestion(++index);
                dialog = new MaterialDialog.Builder(getActivity())
                        .title("Você acertou!")
                        .content("")
                        .positiveText("Próxima")
                        .negativeText("Ver justificativa")
                        .show();

            }
            //alternativa errada
            else{
                dialog = new MaterialDialog.Builder(getActivity())
                        .title("Você errou!")
                        .content("")
                        .positiveText("Próxima")
                        .negativeText("Ver justificativa")
                        .show();
            }


            dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();


                }
            });
            dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    justificativa = questaoF.getJustificativa();
                    setViewPager(0);
                    Toast.makeText(getActivity(), "justificativa", Toast.LENGTH_SHORT ).show();
                }
            });

        }
    }




    @Override
    public void onResume() {
        super.onResume();

        totalQuestoes = Common.questionList.size();
        mCountDown = new CountDownTimer(10000, 3000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(++index);
    }

    public void showQuestion(int i){
        if(index < totalQuestoes){
            questaoAtual++;
            txtPerguntaInfo.setText(questaoAtual + " / " + totalQuestoes);

            txtQuestion.setText(Common.questionList.get(index).getQuestion());
            btnA.setText(Common.questionList.get(index).getAnswera());
            btnB.setText(Common.questionList.get(index).getAnswerb());
            btnC.setText(Common.questionList.get(index).getAnswerc());
            btnD.setText(Common.questionList.get(index).getAnswerd());

            mCountDown.start(); //start timer
        }
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebaseAuth");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("questions");
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

    public int gerator(){
        Random gerador = new Random();
        return gerador.nextInt();
    }


    public void setQuestion(final Question question, int num, int totalQuestoes){
        Log.d(TAG, "setQuestion: settings question " + question +  "\n" + num +  "\n"  +totalQuestoes);
        //initialize components
        txtQuestion.setText(question.getQuestion());
        btnA.setText(question.getAnswera());
        btnB.setText(question.getAnswerb());
        btnC.setText(question.getAnswerc());
        btnD.setText(question.getAnswerd());
        txtPerguntaInfo.setText(num + " / " + totalQuestoes);

        numero++;
    }

    private void setupFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new JustificativaFragment(justificativa), "justificativa"); //fragment 0
        Log.d(TAG, "setupFragments: "+ pagerAdapter.getFragmentNumber(new JustificativaFragment()) );
    }

    private void setViewPager(int fragmentNumber){
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setupViewPager: navigation to fragment #:" + fragmentNumber);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }



}
