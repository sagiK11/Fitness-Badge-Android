package com.sagikor.android.fitracker.utils;


public class SportCategoryNode {
    private double cubesScore;
    private double aerobicScore;
    private double handsScore;
    private int absScore;
    private int jumpScore;
    private int result;

    public static class Builder {
        private double cubesScore;
        private double aerobicScore;
        private double handsScore;
        private int absScore;
        private int jumpScore;
        private int result;

        Builder result(int result) {
            this.result = result;
            return this;
        }

        Builder cubesScore(double cubesScore) {
            this.cubesScore = cubesScore;
            return this;
        }

        Builder aerobicScore(double aerobicScore) {
            this.aerobicScore = aerobicScore;
            return this;
        }

        Builder handsScore(double handsScore) {
            this.handsScore = handsScore;
            return this;
        }

        Builder absScore(int absScore) {
            this.absScore = absScore;
            return this;
        }

        Builder jumpScore(int jumpScore) {
            this.jumpScore = jumpScore;
            return this;
        }

        SportCategoryNode build() {
            SportCategoryNode sportCategoryNode = new SportCategoryNode();
            sportCategoryNode.setResult(this.result);
            sportCategoryNode.setAerobicScore(this.aerobicScore);
            sportCategoryNode.setAbsScore(this.absScore);
            sportCategoryNode.setJumpScore(this.jumpScore);
            sportCategoryNode.setCubesScore(this.cubesScore);
            sportCategoryNode.setHandsScore(this.handsScore);
            return sportCategoryNode;
        }
    }

    public SportCategoryNode() {
    }

    public double getCubesScore() {
        return cubesScore;
    }

    public void setCubesScore(double score) {
        this.cubesScore = score;
    }

    public double getAerobicScore() {
        return aerobicScore;
    }

    public void setAerobicScore(double score) {
        this.aerobicScore = score;
    }

    public int getAbsScore() {
        return absScore;
    }

    public void setAbsScore(int score) {
        this.absScore = score;
    }

    public int getJumpScore() {
        return jumpScore;
    }

    public void setJumpScore(int score) {
        this.jumpScore = score;
    }

    public double getHandsScore() {
        return handsScore;
    }

    void setHandsScore(double score) {
        this.handsScore = score;
    }

    public int getResult() {
        return result;
    }

    void setResult(int result) {
        this.result = result;
    }
}
