package com.sagikor.android.fitracker;

import java.util.ArrayList;
import java.util.List;


public class SportResults {

    List<SportCategoryNode> girlsGradesArrayList;
    List<SportCategoryNode> boysGradeArrayList;
    static final int WORST_RESULT = 30;
    static final String AEROBIC = "aerobic";
    static final String ABS = "abs";
    static final String JUMP = "jump";
    static final String CUBES = "cubes";
    static final String HANDS = "hands";
    private final int RECORDS_NUMBER = 45;

    SportResults() {
        girlsGradesArrayList = new ArrayList<>();
        boysGradeArrayList = new ArrayList<>();
        populateGirlsGradesList();
        populateBoysGradesList();
    }

    private void populateBoysGradesList() {
        int result = 100;
        double cubesScore = 8.8;
        double aerobicScore = 5.55;
        int absScore = 97;
        int jumpScore = 285;
        int handsScore = 28;

        for (int i = 0; i < RECORDS_NUMBER; i++) {
            boysGradeArrayList.add(new SportCategoryNode.Builder()
                    .result(result)
                    .cubesScore(cubesScore)
                    .aerobicScore(aerobicScore)
                    .absScore(absScore)
                    .jumpScore(jumpScore)
                    .handsScore(handsScore)
                    .build()
            );
            result--;
            cubesScore += (i % 2) != 0 ? 0.1 : 0;
            aerobicScore += 0.03;
            absScore -= 1;
            handsScore -= (i % 2) != 0 ? 1 : 0;
            jumpScore -= 2;
        }
    }

    private void populateGirlsGradesList() {
        int result = 100;
        double cubesScore = 9.9;
        double aerobicScore = 08.05;
        int absScore = 77;
        int jumpScore = 236;
        double handsScore = 1.30;

        for (int i = 0; i < RECORDS_NUMBER; i++) {
            girlsGradesArrayList.add(new SportCategoryNode.Builder()
                    .result(result)
                    .cubesScore(cubesScore)
                    .aerobicScore(aerobicScore)
                    .absScore(absScore)
                    .jumpScore(jumpScore)
                    .handsScore(handsScore)
                    .build()
            );
            result--;
            cubesScore += (i % 2) != 0 ? 0.01 : 0;
            aerobicScore += 0.03;
            absScore -= 1;
            handsScore -= 0.03;
            jumpScore -= 2;

        }
    }

    public int getGirlsCubesResult(double score) {
        return getCubesResult(girlsGradesArrayList, score);
    }

    public int getBoysCubesResult(double score) {
        return getCubesResult(boysGradeArrayList, score);
    }

    private int getCubesResult(List<SportCategoryNode> arrayList, double score) {
        SportCategoryNode node;
        boolean emptyField = score == 0;
        for (int i = 0; i < arrayList.size(); i++) {
            node = arrayList.get(i);
            if (node.getCubesScore() >= score) {
                return emptyField ? WORST_RESULT : node.getResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsAerobicResult(double score) {
        return getAerobicResult(girlsGradesArrayList, score);
    }

    public int getBoysAerobicResult(double score) {
        return getAerobicResult(boysGradeArrayList, score);
    }

    private int getAerobicResult(List<SportCategoryNode> arrayList, double score) {
        SportCategoryNode node;
        boolean emptyField = score == 0;
        for (int i = 0; i < arrayList.size(); i++) {
            node = arrayList.get(i);
            if (node.getAerobicScore() >= score) {
                return emptyField ? WORST_RESULT : node.getResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsSitUpAbsResult(int score) {
        return getAbsResult(girlsGradesArrayList, score);
    }

    public int getBoysSitUpAbsResult(int score) {
        return getAbsResult(boysGradeArrayList, score);
    }

    private int getAbsResult(List<SportCategoryNode> arrayList, int score) {
        SportCategoryNode node;
        for (int i = 0; i < arrayList.size(); i++) {
            node = arrayList.get(i);
            if (score >= node.getAbsScore()) {
                return node.getResult();
            }
        }
        return WORST_RESULT;
    }


    public int getGirlsStaticHandsResult(double score) {
        return getHandsResult(girlsGradesArrayList, score);
    }

    public int getBoysHandsResult(double score) {
        return getHandsResult(boysGradeArrayList, score);
    }

    private int getHandsResult(List<SportCategoryNode> ArrayList, double score) {
        SportCategoryNode node;
        for (int i = 0; i < ArrayList.size(); i++) {
            node = ArrayList.get(i);
            if (score >= node.getHandsScore()) {
                return node.getResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsJumpResult(int score) {
        return getJumpResult(girlsGradesArrayList, score);
    }

    public int getBoysJumpResult(int score) {
        return getJumpResult(boysGradeArrayList, score);
    }

    public int getJumpResult(List<SportCategoryNode> arrayList, int score) {

        SportCategoryNode node;
        for (int i = 0; i < arrayList.size(); i++) {
            node = arrayList.get(i);
            if (score >= node.getJumpScore()) {
                return node.getResult();
            }
        }
        return WORST_RESULT;
    }
}
