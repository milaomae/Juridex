package com.example.lucas.juridex_v13.Common;

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
