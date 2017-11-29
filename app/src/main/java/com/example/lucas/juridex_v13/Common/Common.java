package com.example.lucas.juridex_v13.Common;

import android.content.Intent;

import com.example.lucas.juridex_v13.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comae on 28/11/2017.
 */

public class Common {

    public static int acertos;
    public static int questoesTotais;
    public static List<Question> listaQuestoes = new ArrayList<>();
    public static int score;
    public static String nivel;
    public static int qtdTestesFaceis, qtdTestesMedios, qtdTestesDificeis;
    public static Question questaoAtual;
    public static List<Integer> questoesJaLidas = new ArrayList<>();

    public static List<Integer> getQuestoesJaLidas() {
        return questoesJaLidas;
    }

    public static void setQuestoesJaLidas(List<Integer> questoesJaLidas) {
        Common.questoesJaLidas = questoesJaLidas;
    }

    public static Question getQuestaoAtual() {
        return questaoAtual;
    }

    public static void setQuestaoAtual(Question questaoAtual) {
        Common.questaoAtual = questaoAtual;
    }

    public static String getNivel() {
        return nivel;
    }

    public static void setNivel(String nivel) {
        Common.nivel = nivel;
    }

    public static int getQtdTestesFaceis() {
        return qtdTestesFaceis;
    }

    public static void setQtdTestesFaceis(int qtdTestesFaceis) {
        Common.qtdTestesFaceis = qtdTestesFaceis;
    }

    public static int getQtdTestesMedios() {
        return qtdTestesMedios;
    }

    public static void setQtdTestesMedios(int qtdTestesMedios) {
        Common.qtdTestesMedios = qtdTestesMedios;
    }

    public static int getQtdTestesDificeis() {
        return qtdTestesDificeis;
    }

    public static void setQtdTestesDificeis(int qtdTestesDificeis) {
        Common.qtdTestesDificeis = qtdTestesDificeis;
    }

    public static int getAcertos() {
        return acertos;
    }

    public static void setAcertos(int acertos) {
        Common.acertos = acertos;
    }

    public static int getQuestoesTotais() {
        return questoesTotais;
    }

    public static void setQuestoesTotais(int questoesTotais) {
        Common.questoesTotais = questoesTotais;
    }

    public static List<Question> getListaQuestoes() {
        return listaQuestoes;
    }

    public static void setListaQuestoes(List<Question> listaQuestoes) {
        Common.listaQuestoes = listaQuestoes;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Common.score = score;
    }
}
