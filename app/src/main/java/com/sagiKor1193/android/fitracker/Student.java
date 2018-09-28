package com.sagiKor1193.android.fitracker;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Student implements Parcelable {

    private final String TAG = "Student";

    private String sName;
    private String sClass;
    private String sPhoneNumber;

    private double aerobicScore;
    private double aerobicResult;

    private double cubesScore;
    private double cubesResult;

    private double absScore;
    private double absResult;

    private double jumpScore;
    private double jumpResult;

    private double handsScore;
    private double handsResult;

    private double totalScore;
    private double totScoreWithoutAerobic;



    public Student(){

    }
    
    public Student(String sName, String sClass, double aerobicScore, double cubesScore, double absScore,
                   double jumpScore, double handsScore){
        this.sName = sName;
        this.sClass = sClass;
        this.aerobicScore = aerobicScore;
        this.cubesScore = cubesScore;
        this.absScore = absScore;
        this.jumpScore = jumpScore;
        this.handsScore = handsScore;
    }
    public Student(String sName, String sClass, String sPhone){
        this.sName = sName;
        this.sClass = sClass;
        Log.d(TAG, "student phone: " + sPhone);
        this.sPhoneNumber = sPhone;
        this.aerobicScore = -1;
        this.cubesScore = -1;
        this.absScore = -1;
        this.handsScore = -1;
        this.jumpScore = -1;
    }


    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getSClass() {
        return sClass;
    }

    public void setAeroRes(double _aerobicResult) {
        this.aerobicResult = _aerobicResult;
    }

    public void setCubesRes(double cubesResult) {
        this.cubesResult = cubesResult;
    }

    public void setAbsRes(double absResult) {
        this.absResult = absResult;
    }

    public void setJumpRes(double jumpResult) {
        this.jumpResult = jumpResult;
    }

    public void setHandsRes(double handsResult) {
        this.handsResult = handsResult;
    }

    public void setSClass(String _sClass) {
        this.sClass = _sClass;
    }

    public double getAeroScore() {
        return aerobicScore;
    }

    public void setAeroScore(double _aeroScore) {
        this.aerobicScore = _aeroScore;
    }

    public double getCubesScore() {
        return cubesScore;
    }

    public void setCubesScore(double _cubesScore) {
        this.cubesScore = _cubesScore;
    }

    public double getAbsScore() {
        return absScore;
    }

    public void setAbsScore(double _absScore) {
        this.absScore = _absScore;
    }

    public double getJumpScore() {
        return jumpScore;
    }

    public void setJumpScore(double _jumpScore) {
        this.jumpScore = _jumpScore;
    }

    public double getHandsScore() {
        return handsScore;
    }

    public void setHandsScore(double _handsScore) {
        this.handsScore = _handsScore;
    }

    public double getAeroRes() {
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

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getTotScoreWithoutAerobic() {
        return totScoreWithoutAerobic;
    }

    public void setTotScoreWithoutAerobic(double totScoreWithoutAerobic) {
        this.totScoreWithoutAerobic = totScoreWithoutAerobic;
    }


    public String getPhoneNumber() {
        return sPhoneNumber;
    }

    public void setPhoneNumber(String sPhoneNumber) {
        this.sPhoneNumber = sPhoneNumber;
    }

    public String toString(){
        return "\nStudent name: "  + sName + "\nClass: " + sClass+ "\nPhone: " +"\'"+ sPhoneNumber  +"\'"
                + "\nAerobic Score: " + aerobicScore
                + "\ncubesScore: " + cubesScore + "\nabsScore: "+absScore+ "\njumpScore: " + jumpScore
                + "\nHandsScore: " + handsScore + "\nTotalScore: " + totalScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Student(Parcel in) {
        sName = in.readString();
        sClass = in.readString();
        aerobicScore = in.readDouble();
        aerobicResult = in.readDouble();
        cubesScore = in.readDouble();
        cubesResult = in.readDouble();
        absScore = in.readDouble();
        absResult = in.readDouble();
        jumpScore = in.readDouble();
        jumpResult = in.readDouble();
        handsScore = in.readDouble();
        handsResult = in.readDouble();
        totalScore = in.readDouble();
        sPhoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sName);
        dest.writeString(sClass);
        dest.writeDouble(aerobicScore);
        dest.writeDouble(aerobicResult);
        dest.writeDouble(cubesScore);
        dest.writeDouble(cubesResult);
        dest.writeDouble(absScore);
        dest.writeDouble(absResult);
        dest.writeDouble(jumpScore);
        dest.writeDouble(jumpResult);
        dest.writeDouble(handsScore);
        dest.writeDouble(handsResult);
        dest.writeDouble(totalScore);
        dest.writeString(sPhoneNumber);
    }
}
