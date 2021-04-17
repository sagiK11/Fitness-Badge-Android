package com.sagikor.android.fitracker.utils.datastructure;


public class SportCategoriesEntry {
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

        SportCategoriesEntry build() {
            SportCategoriesEntry sportCategoriesEntry = new SportCategoriesEntry();
            sportCategoriesEntry.setResult(this.result);
            sportCategoriesEntry.setAerobicScore(this.aerobicScore);
            sportCategoriesEntry.setAbsScore(this.absScore);
            sportCategoriesEntry.setJumpScore(this.jumpScore);
            sportCategoriesEntry.setCubesScore(this.cubesScore);
            sportCategoriesEntry.setHandsScore(this.handsScore);
            return sportCategoriesEntry;
        }
    }

    public SportCategoriesEntry() {
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
