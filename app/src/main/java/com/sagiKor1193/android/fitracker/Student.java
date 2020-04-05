package com.sagiKor1193.android.fitracker;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Student implements Parcelable {

    private String name, studentClass, phoneNumber, updatedDate;
    private double aerobicScore, aerobicResult, cubesScore, cubesResult, absScore, absResult;
    private double jumpScore, jumpResult, handsScore, handsResult, totalScore, totalScoreWithoutAerobic;


    Student() {

    }


    String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdateDate( String date ) {
        this.updatedDate = date;
    }

    public void setAerobicResult( double aerobicResult ) {
        this.aerobicResult = aerobicResult;
    }

    public void setCubesResult( double cubesResult ) {
        this.cubesResult = cubesResult;
    }

    public void setAbsResult( double absResult ) {
        this.absResult = absResult;
    }

    public void setJumpResult( double jumpResult ) {
        this.jumpResult = jumpResult;
    }

    public void setHandsResult( double handsResult ) {
        this.handsResult = handsResult;
    }

    public void setStudentClass( String studentClass ) {
        this.studentClass = studentClass;
    }

    public double getAerobicScore() {
        return aerobicScore;
    }

    public void setAerobicScore( double aerobicScore ) {
        this.aerobicScore = aerobicScore;
    }

    public double getCubesScore() {
        return cubesScore;
    }

    public void setCubesScore( double cubesScore ) {
        this.cubesScore = cubesScore;
    }

    public double getAbsScore() {
        return absScore;
    }

    public void setAbsScore( double absScore ) {
        this.absScore = absScore;
    }

    public double getJumpScore() {
        return jumpScore;
    }

    public void setJumpScore( double jumpScore ) {
        this.jumpScore = jumpScore;
    }

    public double getHandsScore() {
        return handsScore;
    }

    public void setHandsScore( double handsScore ) {
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

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore( double totalScore ) {
        this.totalScore = totalScore;
    }

    public double getTotScoreWithoutAerobic() {
        return totalScoreWithoutAerobic;
    }

    public void setTotScoreWithoutAerobic( double totalScoreWithoutAerobic ) {
        this.totalScoreWithoutAerobic = totalScoreWithoutAerobic;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return "\nStudent name: " + name + "\nClass: " + studentClass + "\nPhone: " + "\'" + phoneNumber + "\'"
                + "\nAerobic Score: " + aerobicScore
                + "\ncubesScore: " + cubesScore + "\nabsScore: " + absScore + "\njumpScore: " + jumpScore
                + "\nHandsScore: " + handsScore + "\nTotalScore: " + totalScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Student( Parcel in ) {
        name = in.readString();
        studentClass = in.readString();
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
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString( name );
        dest.writeString( studentClass );
        dest.writeDouble( aerobicScore );
        dest.writeDouble( aerobicResult );
        dest.writeDouble( cubesScore );
        dest.writeDouble( cubesResult );
        dest.writeDouble( absScore );
        dest.writeDouble( absResult );
        dest.writeDouble( jumpScore );
        dest.writeDouble( jumpResult );
        dest.writeDouble( handsScore );
        dest.writeDouble( handsResult );
        dest.writeDouble( totalScore );
        dest.writeString( phoneNumber );
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel( Parcel in ) {
            return new Student( in );
        }

        @Override
        public Student[] newArray( int size ) {
            return new Student[ size ];
        }
    };
}
