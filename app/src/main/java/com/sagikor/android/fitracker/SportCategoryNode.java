package com.sagikor.android.fitracker;


public class SportCategoryNode {
    private double cubesScore;
    private double aerobicScore;
    private double handsScore;
    private int absScore;
    private int jumpScore;
    private int cubesResult;
    private int absResult;
    private int aerobicResult;
    private int handsResult;
    private int jumpResult;


    public SportCategoryNode( double cubes, int cubesRes, double aerobic, int aerobicRes, int abs, int absRes,
                              int jump, int jumpRes, double hands, int handsRes ) {
        cubesScore = cubes;
        cubesResult = cubesRes;

        aerobicScore = aerobic;
        aerobicResult = aerobicRes;

        absScore = abs;
        absResult = absRes;

        jumpScore = jump;
        jumpResult = jumpRes;

        handsScore = hands;
        handsResult = handsRes;
    }

    public double getCubesScore() {
        return cubesScore;
    }

    public double getAerobicScore() {
        return aerobicScore;
    }

    public int getAbsScore() {
        return absScore;
    }

    public int getJumpScore() {
        return jumpScore;
    }

    public double getHandsScore() {
        return handsScore;
    }

    public int getCubesResult() {
        return cubesResult;
    }

    public int getAbsResult() {
        return absResult;
    }

    public int getAerobicResult() {
        return aerobicResult;
    }

    public int getHandsResult() {
        return handsResult;
    }

    public int getJumpResult() {
        return jumpResult;
    }

}
