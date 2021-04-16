package com.sagikor.android.fitracker.utils.datastructure;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SportResults implements SportResultsHandler {

    List<SportCategoryNode> femaleGradesArrayList;
    List<SportCategoryNode> malesGradeArrayList;
    public static final int WORST_RESULT = 30;
    public static final String AEROBIC = "aerobic";
    public static final String ABS = "abs";
    public static final String JUMP = "jump";
    public static final String CUBES = "cubes";
    public static final String HANDS = "hands";


    public SportResults(InputStream femaleGrades, InputStream maleGrades) {
        femaleGradesArrayList = new ArrayList<>();
        malesGradeArrayList = new ArrayList<>();

        BufferedReader bfFemale = new BufferedReader(new InputStreamReader(femaleGrades));
        BufferedReader bfMale = new BufferedReader(new InputStreamReader(maleGrades));

        populateGradesList(femaleGradesArrayList, bfFemale);
        populateGradesList(malesGradeArrayList, bfMale);
    }

    private void populateGradesList(List<SportCategoryNode> list, BufferedReader bf) {
        String line = "";
        final int hands = 0;
        final int jump = 1;
        final int aerobic = 2;
        final int cubes = 3;
        final int abs = 4;
        final int grade = 5;
        try {
            bf.readLine();//skipping the headers
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(",");
                list.add(new SportCategoryNode.Builder()
                        .result(Integer.parseInt(tokens[grade]))
                        .handsScore(Double.parseDouble(tokens[hands]))
                        .absScore(Integer.parseInt(tokens[abs]))
                        .jumpScore(Integer.parseInt(tokens[jump]))
                        .cubesScore(Double.parseDouble(tokens[cubes]))
                        .aerobicScore(Double.parseDouble(tokens[aerobic]))
                        .build()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getFemalesCubesResult(double score) {
        return getCubesResult(femaleGradesArrayList, score);
    }

    @Override
    public int getMalesCubesResult(double score) {
        return getCubesResult(malesGradeArrayList, score);
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

    @Override
    public int getFemalesAerobicResult(double score) {
        return getAerobicResult(femaleGradesArrayList, score);
    }

    @Override
    public int getMalesAerobicResult(double score) {
        return getAerobicResult(malesGradeArrayList, score);
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

    @Override
    public int getFemalesSitUpAbsResult(int score) {
        return getAbsResult(femaleGradesArrayList, score);
    }

    @Override
    public int getMalesSitUpAbsResult(int score) {
        return getAbsResult(malesGradeArrayList, score);
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

    @Override
    public int getFemalesStaticHandsResult(double score) {
        return getHandsResult(femaleGradesArrayList, score);
    }

    @Override
    public int getMalesHandsResult(double score) {
        return getHandsResult(malesGradeArrayList, score);
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

    @Override
    public int getFemalesJumpResult(int score) {
        return getJumpResult(femaleGradesArrayList, score);
    }

    @Override
    public int getMalesJumpResult(int score) {
        return getJumpResult(malesGradeArrayList, score);
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
