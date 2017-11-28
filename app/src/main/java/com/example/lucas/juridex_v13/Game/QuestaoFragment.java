package com.example.lucas.juridex_v13.Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.lucas.juridex_v13.FirebaseMethods;
import com.example.lucas.juridex_v13.Login.LoginActivity;
import com.example.lucas.juridex_v13.R;
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

    private Button btnA, btnB, btnC, btnD;
    private MyTextView txtQuestion, txtQuestaoAtual, txtNivelAtual;
    private List<Question> questoes;
    private int questaoLista;
    private MaterialDialog dialog;
    private List<Integer> questoesJaLidas;
    private int questoesCorretas, score, questaoAtual;

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
        questoes = new ArrayList<>();
        questoesJaLidas = new ArrayList<>();

        //limpar listas
        questoes.clear();
        questoesJaLidas.clear();

        //iniciar variáveis
        score = 0;
        questaoAtual = 0;
        questoesCorretas = 0;

        mAuth = FirebaseAuth.getInstance();
        firebase = new FirebaseMethods(getActivity());


        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

        btnA = (Button) view.findViewById(R.id.btnA);
        btnB = (Button) view.findViewById(R.id.btnB);
        btnC = (Button) view.findViewById(R.id.btnC);
        btnD = (Button) view.findViewById(R.id.btnD);
        txtQuestion = (MyTextView) view.findViewById(R.id.txtQuestion);
        txtQuestaoAtual = (MyTextView) view.findViewById(R.id.txtperguntaAtual);
        txtNivelAtual = (MyTextView) view.findViewById(R.id.txtnivelAtual);

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

        if(btnGeneric.getText().equals(questoes.get(questaoLista).getCorrect())){
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
                if(questoesJaLidas.size() != questoes.size()){
                    setQuestions();
                }
                else
                    ((GameActivity)getActivity()).setViewPager(2);


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
        int qtdQuestao = 0;

        questaoLista = gerador.nextInt(questoes.size());

        if(!questoesJaLidas.contains(questaoLista)) {
            qtdQuestao++;
            txtQuestaoAtual.setText(qtdQuestao + "  /  " + questoes.size());
            questoesJaLidas.add(questaoLista);

            Question question = questoes.get(questaoLista);

            //initialize components
            txtQuestion.setText(question.getQuestion());
            btnA.setText(question.getAnswera());
            btnB.setText(question.getAnswerb());
            btnC.setText(question.getAnswerc());
            btnD.setText(question.getAnswerd());
        }


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
                            questoes = firebase.loadQuestions("cdc_easy", dataSnapshot);
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
