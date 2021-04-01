package com.sagikor.android.fitracker.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SportResults {

    List<SportCategoryNode> girlsGradesArrayList;
    List<SportCategoryNode> boysGradeArrayList;
    public static final int WORST_RESULT = 30;
    public static final String AEROBIC = "aerobic";
    public static final String ABS = "abs";
    public static final String JUMP = "jump";
    public static final String CUBES = "cubes";
    public static final String HANDS = "hands";
    public static final String GIRLS = "girls";
    public static final String BOYS = "boys";


    public SportResults() {
        girlsGradesArrayList = new ArrayList<>();
        boysGradeArrayList = new ArrayList<>();
        populateGradesList(girlsGradesArrayList, GIRLS);
        populateGradesList(boysGradeArrayList, BOYS);
    }



    private void populateGradesList(List<SportCategoryNode> list, String gender) {
        final int RECORDS_NUMBER = 66;
        int result = 100;
        boolean isBoysList = gender.equals(BOYS);
        BigDecimal cubesScore = BigDecimal.valueOf(isBoysList ? 8.8 : 9.9);
        BigDecimal aerobicScore = BigDecimal.valueOf(isBoysList ? 5.55 : 8.05);
        int absScore = isBoysList ? 97 : 77;
        int jumpScore = isBoysList ? 285 : 236;
        BigDecimal handsScore = BigDecimal.valueOf(isBoysList ? 28 : 1.3);

        for (int i = 0; i < RECORDS_NUMBER; i++, result--, absScore--, jumpScore -= 2) {
            list.add(new SportCategoryNode.Builder()
                    .result(result)
                    .cubesScore(cubesScore.doubleValue())
                    .aerobicScore(aerobicScore.doubleValue())
                    .absScore(absScore)
                    .jumpScore(jumpScore)
                    .handsScore(handsScore.doubleValue())
                    .build());
            cubesScore = cubesIncrease(i, cubesScore);
            aerobicScore = aerobicIncrease(i, aerobicScore);
            handsScore = handsIncrease(i, handsScore, isBoysList);
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

    private BigDecimal cubesIncrease(int i, BigDecimal currentScore) {
        final BigDecimal CUBES_INCREASE = new BigDecimal("0.1");
        final BigDecimal ZERO = new BigDecimal("0");
        return currentScore.add((i % 2) != 0 ? CUBES_INCREASE : ZERO);
    }

    private BigDecimal aerobicIncrease(int i , BigDecimal currentScore){
        final BigDecimal AEROBIC_FIRST_INCREASE = new BigDecimal("0.03");
        final BigDecimal AEROBIC_SECOND_INCREASE = new BigDecimal("0.08");
        final BigDecimal AEROBIC_THIRD_INCREASE = new BigDecimal("0.16");
        final BigDecimal SECONDS_IN_MINUTE = new BigDecimal("0.6");
        final BigDecimal MINUTES_OFFSET = new BigDecimal("0.4");
        final int FIRST_PART = 15;
        final int SECOND_PART = 25;
        BigDecimal result = currentScore.add(i < FIRST_PART ? AEROBIC_FIRST_INCREASE :
                (i < SECOND_PART ? AEROBIC_SECOND_INCREASE : AEROBIC_THIRD_INCREASE));
        //Aerobic score is based on time.
        if (result.doubleValue() - Math.floor(result.doubleValue())
                >= SECONDS_IN_MINUTE.doubleValue()) {
            result = result.add(MINUTES_OFFSET);
        }
        return result;
    }
    private BigDecimal handsIncrease(int i , BigDecimal currentScore, boolean isBoysList){
        final BigDecimal SECONDS_IN_MINUTE = new BigDecimal("0.6");
        final BigDecimal MINUTES_OFFSET = new BigDecimal("0.4");
        final BigDecimal HANDS_FIRST_INCREASE = new BigDecimal("0.03");
        final BigDecimal HANDS_SECOND_INCREASE = new BigDecimal("0.01");
        final BigDecimal ONE = new BigDecimal("1");
        final BigDecimal ZERO = new BigDecimal("0");
        final int FIRST_PART = 15;
        BigDecimal result = currentScore.subtract(isBoysList ? ((i % 2) != 0 ? ONE : ZERO) :
                (i < FIRST_PART ? HANDS_FIRST_INCREASE : HANDS_SECOND_INCREASE));

        //For females hands score is based on time.
        if (!isBoysList && currentScore.doubleValue() - Math.floor(currentScore.doubleValue())
                >= SECONDS_IN_MINUTE.doubleValue()) {
            result = currentScore.subtract(MINUTES_OFFSET);
        }
        return result;

    }

}
