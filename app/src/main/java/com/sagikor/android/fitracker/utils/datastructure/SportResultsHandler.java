package com.sagikor.android.fitracker.utils.datastructure;

public interface SportResultsHandler {

    int getFemalesCubesResult(double score);

    int getMalesCubesResult(double score);

    int getFemalesAerobicResult(double score);

    int getMalesAerobicResult(double score);

    int getFemalesSitUpAbsResult(int score);

    int getMalesSitUpAbsResult(int score);

    int getFemalesStaticHandsResult(double score);

    int getMalesHandsResult(double score);

    int getFemalesJumpResult(int score);

    int getMalesJumpResult(int score);
}
