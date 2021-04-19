package com.sagikor.android.fitracker.data.model;


public class Student {
    private String name;
    private String gender;
    private String studentClass;
    private String phoneNumber;
    private String updatedDate;
    private String key;
    private String userId;
    private double aerobicScore;
    private double aerobicResult;
    private double jumpScore;
    private double jumpResult;
    private double cubesScore;
    private double cubesResult;
    private double handsScore;
    private double handsResult;
    private double absScore;
    private double absResult;
    private double totalScore;
    private boolean isAerobicWalking;
    private boolean isPushUpHalf;

    public static class Builder {
        private String name;
        private String gender;
        private String studentClass;
        private String phoneNumber;
        private String updatedDate;
        private String key;
        private String userId;
        private double aerobicScore;
        private double aerobicResult;
        private double jumpScore;
        private double jumpResult;
        private double cubesScore;
        private double cubesResult;
        private double handsScore;
        private double handsResult;
        private double absScore;
        private double absResult;
        private double totalScore;
        private boolean isAerobicWalking;
        private boolean isPushUpHalf;

        public Builder(String name) {
            this.name = name;
        }

        public Builder studentGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder studentClass(String studentClass) {
            this.studentClass = studentClass;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder updatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public Builder aerobicScore(double aerobicScore) {
            this.aerobicScore = aerobicScore;
            return this;
        }

        public Builder aerobicResult(double aerobicResult) {
            this.aerobicResult = aerobicResult;
            return this;
        }

        public Builder cubesScore(double cubesScore) {
            this.cubesScore = cubesScore;
            return this;
        }

        public Builder cubesResult(double cubesResult) {
            this.cubesResult = cubesResult;
            return this;
        }

        public Builder absScore(double absScore) {
            this.absScore = absScore;
            return this;
        }

        public Builder absResult(double absResult) {
            this.absResult = absResult;
            return this;
        }

        public Builder jumpScore(double jumpScore) {
            this.jumpScore = jumpScore;
            return this;
        }

        public Builder jumpResult(double jumpResult) {
            this.jumpResult = jumpResult;
            return this;
        }

        public Builder handsScore(double handsScore) {
            this.handsScore = handsScore;
            return this;
        }

        public Builder handsResult(double handsResult) {
            this.handsResult = handsResult;
            return this;
        }

        public Builder totalScore(double totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public Builder isAerobicWalking(boolean isWalking) {
            this.isAerobicWalking = isWalking;
            return this;
        }

        public Builder isPushUpHalf(boolean isPushUpHalf) {
            this.isPushUpHalf = isPushUpHalf;
            return this;
        }

        public Student build() {
            Student student = new Student();
            student.setName(this.name);
            student.setStudentClass(this.studentClass);
            student.setGender(this.gender);
            student.setKey(this.key);
            student.setUserId(this.userId);
            student.setPhoneNumber(this.phoneNumber);
            student.setAerobicScore(this.aerobicScore);
            student.setCubesScore(this.cubesScore);
            student.setAbsScore(this.absScore);
            student.setJumpScore(this.jumpScore);
            student.setHandsScore(this.handsScore);
            student.setAbsResult(this.absResult);
            student.setAerobicResult(this.aerobicResult);
            student.setJumpResult(this.jumpResult);
            student.setHandsResult(this.handsResult);
            student.setCubesResult(this.cubesResult);
            student.setTotalScore(this.totalScore);
            student.setUpdateDate(this.updatedDate);
            student.setAerobicWalking(this.isAerobicWalking);
            student.setPushUpHalf(this.isPushUpHalf);
            return student;
        }

    }

    private Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUpdateDate(String date) {
        this.updatedDate = date;
    }

    public void setAerobicResult(double aerobicResult) {
        this.aerobicResult = aerobicResult;
    }

    public void setCubesResult(double cubesResult) {
        this.cubesResult = cubesResult;
    }

    public void setAbsResult(double absResult) {
        this.absResult = absResult;
    }

    public void setJumpResult(double jumpResult) {
        this.jumpResult = jumpResult;
    }

    public void setHandsResult(double handsResult) {
        this.handsResult = handsResult;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public double getAerobicScore() {
        return aerobicScore;
    }

    public void setAerobicScore(double aerobicScore) {
        this.aerobicScore = aerobicScore;
    }

    public double getCubesScore() {
        return cubesScore;
    }

    public void setCubesScore(double cubesScore) {
        this.cubesScore = cubesScore;
    }

    public double getAbsScore() {
        return absScore;
    }

    public void setAbsScore(double absScore) {
        this.absScore = absScore;
    }

    public double getJumpScore() {
        return jumpScore;
    }

    public void setJumpScore(double jumpScore) {
        this.jumpScore = jumpScore;
    }

    public double getHandsScore() {
        return handsScore;
    }

    public void setHandsScore(double handsScore) {
        this.handsScore = handsScore;
    }

    public double getAerobicResult() {
        return aerobicResult;
    }

    public double getCubesResult() {
        return cubesResult;
    }

    public double getAbsResult() {
        return absResult;
    }

    public double getJumpResult() {
        return jumpResult;
    }

    public double getHandsResult() {
        return handsResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isAerobicWalking() {
        return isAerobicWalking;
    }

    public void setAerobicWalking(boolean isAerobicWalking) {
        this.isAerobicWalking = isAerobicWalking;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isPushUpHalf() {
        return isPushUpHalf;
    }

    public void setPushUpHalf(boolean pushUpHalf) {
        this.isPushUpHalf = pushUpHalf;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isFinished() {
        final int MISSING = -1;
        return aerobicScore != MISSING && jumpScore != MISSING && absScore != MISSING
                && handsScore != MISSING && cubesScore != MISSING;
    }

    public String asCSV() {
        StringBuilder sb = new StringBuilder();
        return sb.append(name).append(",").append(studentClass).append(",").append(aerobicScore)
                .append(",").append(aerobicResult).append(",").append(absScore)
                .append(",").append(absResult).append(",").append(handsScore).append(",")
                .append(handsResult).append(",").append(cubesScore).append(",")
                .append(cubesResult).append(",").append(jumpScore).append(",")
                .append(jumpResult).append(",").append(totalScore).toString();
    }

}
