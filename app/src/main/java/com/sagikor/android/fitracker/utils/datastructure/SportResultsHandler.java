package com.sagikor.android.fitracker.utils.datastructure;

public interface SportResultsHandler {

    int getFemalesCubesResult(double score);

    int getMalesCubesResult(double score);

    int getFemalesAerobicResult(double score, boolean isWalking);

    int getMalesAerobicResult(double score);

    int getFemalesAbsResult(double score);

    int getMalesSitUpAbsResult(int score);

    int getFemalesHandsResult(double score, boolean isPushUpHalf);

    int getMalesHandsResult(double score);

    int getFemalesJumpResult(int score);

    int getMalesJumpResult(int score);
}
