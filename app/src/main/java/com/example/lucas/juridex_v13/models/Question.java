package com.example.lucas.juridex_v13.models;

/**
 * Created by Lucas on 26/11/2017.
 */

public class Question {

    private String answera;
    private String answerb;
    private String answerc;
    private String answerd;
    private String correct;
    private String justificativa;
    private String question;

    public Question(String answera, String answerb, String answerc, String answerd, String correct, String justificativa, String question) {
        this.answera = answera;
        this.answerb = answerb;
        this.answerc = answerc;
        this.answerd = answerd;
        this.correct = correct;
        this.justificativa = justificativa;
        this.question = question;
    }

    public Question() {
    }

    public String getAnswera() {
        return answera;
    }

    public void setAnswera(String answera) {
        this.answera = answera;
    }

    public String getAnswerb() {
        return answerb;
    }

    public void setAnswerb(String answerb) {
        this.answerb = answerb;
    }

    public String getAnswerc() {
        return answerc;
    }

    public void setAnswerc(String answerc) {
        this.answerc = answerc;
    }

    public String getAnswerd() {
        return answerd;
    }

    public void setAnswerd(String answerd) {
        this.answerd = answerd;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answera='" + answera + '\'' +
                ", answerb='" + answerb + '\'' +
                ", answerc='" + answerc + '\'' +
                ", answerd='" + answerd + '\'' +
                ", correct='" + correct + '\'' +
                ", justificativa='" + justificativa + '\'' +
                ", Question='" + question + '\'' +
                '}';
    }
}
