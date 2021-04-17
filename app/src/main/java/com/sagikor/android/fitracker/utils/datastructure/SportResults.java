package com.sagikor.android.fitracker.utils.datastructure;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SportResults implements SportResultsHandler {

    List<SportCategoriesEntry> femaleGradesList;
    List<SportCategoriesEntry> malesGradeList;
    public static final int WORST_RESULT = 30;
    public static final String AEROBIC = "aerobic";
    public static final String ABS = "abs";
    public static final String JUMP = "jump";
    public static final String CUBES = "cubes";
    public static final String HANDS = "hands";


    public SportResults(InputStream femaleGrades, InputStream maleGrades) {
        femaleGradesList = new ArrayList<>();
        malesGradeList = new ArrayList<>();

        populateGradesList(femaleGradesList, new InputStreamReader(femaleGrades));
        populateGradesList(malesGradeList, new InputStreamReader(maleGrades));
    }

    private void populateGradesList(List<SportCategoriesEntry> list, InputStreamReader isr) {
        String line;
        final int hands = 0;
        final int jump = 1;
        final int aerobic = 2;
        final int cubes = 3;
        final int abs = 4;
        final int grade = 5;
        try (BufferedReader bf = new BufferedReader(isr)) {
            bf.readLine();//skipping the headers
            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split(",");
                list.add(new SportCategoriesEntry.Builder()
                        .result(Integer.parseInt(tokens[grade]))
                        .handsScore(Double.parseDouble(tokens[hands]))
                        .absScore(Integer.parseInt(tokens[abs]))
                        .jumpScore(Integer.parseInt(tokens[jump]))
                        .cubesScore(Double.parseDouble(tokens[cubes]))
                        .aerobicScore(Double.parseDouble(tokens[aerobic]))
                        .build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getFemalesCubesResult(double score) {
        return getCubesResult(femaleGradesList, score);
    }

    @Override
    public int getMalesCubesResult(double score) {
        return getCubesResult(malesGradeList, score);
    }
    
    @Override
    public int getFemalesAerobicResult(double score) {
        return getAerobicResult(femaleGradesList, score);
    }

    @Override
    public int getMalesAerobicResult(double score) {
        return getAerobicResult(malesGradeList, score);
    }

    @Override
    public int getFemalesSitUpAbsResult(int score) {
        return getAbsResult(femaleGradesList, score);
    }

    @Override
    public int getMalesSitUpAbsResult(int score) {
        return getAbsResult(malesGradeList, score);
    }
    
    @Override
    public int getFemalesStaticHandsResult(double score) {
        return getHandsResult(femaleGradesList, score);
    }

    @Override
    public int getMalesHandsResult(double score) {
        return getHandsResult(malesGradeList, score);
    }
    
    @Override
    public int getFemalesJumpResult(int score) {
        return getJumpResult(femaleGradesList, score);
    }

    @Override
    public int getMalesJumpResult(int score) {
        return getJumpResult(malesGradeList, score);
    }

    private int getAerobicResult(List<SportCategoriesEntry> list, double score) {
        SportCategoriesEntry entry;
        boolean emptyField = score == 0;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (entry.getAerobicScore() >= score) {
                return emptyField ? WORST_RESULT : entry.getResult();
            }
        }
        return WORST_RESULT;
    }

    private int getAbsResult(List<SportCategoriesEntry> list, int score) {
        SportCategoriesEntry entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (score >= entry.getAbsScore()) {
                return entry.getResult();
            }
        }
        return WORST_RESULT;
    }

    private int getHandsResult(List<SportCategoriesEntry> list, double score) {
        SportCategoriesEntry entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (score >= entry.getHandsScore()) {
                return entry.getResult();
            }
        }
        return WORST_RESULT;
    }

    private int getCubesResult(List<SportCategoriesEntry> list, double score) {
        SportCategoriesEntry entry;
        boolean emptyField = score == 0;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (entry.getCubesScore() >= score) {
                return emptyField ? WORST_RESULT : entry.getResult();
            }
        }
        return WORST_RESULT;
    }

    public int getJumpResult(List<SportCategoriesEntry> list, int score) {

        SportCategoriesEntry entry;
        for (int i = 0; i < list.size(); i++) {
            entry = list.get(i);
            if (score >= entry.getJumpScore()) {
                return entry.getResult();
            }
        }
        return WORST_RESULT;
    }

}
