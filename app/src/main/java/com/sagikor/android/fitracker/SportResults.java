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
    static final String GIRLS = "girls";
    static final String BOYS = "boys";


    SportResults() {
        girlsGradesArrayList = new ArrayList<>();
        boysGradeArrayList = new ArrayList<>();
        populateGradesList(girlsGradesArrayList, GIRLS);
        populateGradesList(boysGradeArrayList, BOYS);
    }

    private void populateGradesList(List<SportCategoryNode> list, String gender) {
        final int RECORDS_NUMBER = 66;
        int result = 100;
        boolean isBoysList = gender.equals(BOYS);
        double cubesScore = isBoysList ? 8.8 : 9.9;
        double aerobicScore = isBoysList ? 5.55 : 8.05;
        int absScore = isBoysList ? 97 : 77;
        int jumpScore = isBoysList ? 285 : 236;
        double handsScore = isBoysList ? 28 : 1.3;

        for (int i = 0; i < RECORDS_NUMBER; i++, result--, absScore--, jumpScore -= 2) {
            list.add(new SportCategoryNode.Builder()
                    .result(result)
                    .cubesScore(cubesScore)
                    .aerobicScore(aerobicScore)
                    .absScore(absScore)
                    .jumpScore(jumpScore)
                    .handsScore(handsScore)
                    .build()
            );
            final int FIRST_PART = 15;
            final int SECOND_PART = 25;
            final double SECONDS_IN_MINUTE = 0.6;
            final double MINUTES_OFFSET = 0.4;
            final double CUBES_INCREASE = 0.1;
            final double HANDS_INCREASE = 0.03;
            final double AEROBIC_FIRST_INCREASE = 0.03;
            final double AEROBIC_SECOND_INCREASE = 0.08;
            final double AEROBIC_THIRD_INCREASE = 0.16;

            cubesScore += (i % 2) != 0 ? CUBES_INCREASE : 0;
            aerobicScore += i < FIRST_PART ? AEROBIC_FIRST_INCREASE :
                    (i < SECOND_PART ? AEROBIC_SECOND_INCREASE : AEROBIC_THIRD_INCREASE);
            handsScore -= isBoysList ? ((i % 2) != 0 ? 1 : 0) : HANDS_INCREASE;
            //Aerobic score is based on time.
            if (aerobicScore - Math.floor(aerobicScore) >= SECONDS_IN_MINUTE) {
                aerobicScore -= -MINUTES_OFFSET;
            }
            //For females hands score is based on time.
            if (!isBoysList && handsScore - Math.floor(handsScore) >= SECONDS_IN_MINUTE) {
                handsScore -= MINUTES_OFFSET;
            }
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
