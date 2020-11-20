package com.sagikor.android.fitracker;

import android.os.Parcel;
import android.os.Parcelable;


public class Student implements Parcelable {
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
    private double totalScoreWithoutAerobic;


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
        private double totalScoreWithoutAerobic;


        Builder( String name ) {
            this.name = name;
        }

        Builder studentGender( String gender ) {
            this.gender = gender;
            return this;
        }

        Builder studentClass( String studentClass ) {
            this.studentClass = studentClass;
            return this;
        }

        Builder phoneNumber( String phoneNumber ) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        Builder key( String key ) {
            this.key = key;
            return this;
        }

        Builder userId( String userId ) {
            this.userId = userId;
            return this;
        }

        Builder updatedDate( String updatedDate ) {
            this.updatedDate = updatedDate;
            return this;
        }

        Builder aerobicScore( double aerobicScore ) {
            this.aerobicScore = aerobicScore;
            return this;
        }

        public Builder aerobicResult( double aerobicResult ) {
            this.aerobicResult = aerobicResult;
            return this;
        }

        public Builder cubesScore( double cubesScore ) {
            this.cubesScore = cubesScore;
            return this;
        }

        Builder cubesResult( double cubesResult ) {
            this.cubesResult = cubesResult;
            return this;
        }

        Builder absScore( double absScore ) {
            this.absScore = absScore;
            return this;
        }

        Builder absResult( double absResult ) {
            this.absResult = absResult;
            return this;
        }

        Builder jumpScore( double jumpScore ) {
            this.jumpScore = jumpScore;
            return this;
        }

        Builder jumpResult( double jumpResult ) {
            this.jumpResult = jumpResult;
            return this;
        }

        Builder handsScore( double handsScore ) {
            this.handsScore = handsScore;
            return this;
        }

        Builder handsResult( double handsResult ) {
            this.handsResult = handsResult;
            return this;
        }

        Builder totalScore( double totalScore ) {
            this.totalScore = totalScore;
            return this;
        }

        Builder totalScoreWithoutAerobic( double totalScoreWithoutAerobic ) {
            this.totalScoreWithoutAerobic = totalScoreWithoutAerobic;
            return this;
        }

        Student build() {
            Student student = new Student();
            student.setName( this.name );
            student.setStudentClass( this.studentClass );
            student.setGender( this.gender );
            student.setKey( this.key );
            student.setUserId( this.userId );
            student.setPhoneNumber( this.phoneNumber );
            student.setAerobicScore( this.aerobicScore );
            student.setCubesScore( this.cubesScore );
            student.setAbsScore( this.absScore );
            student.setJumpScore( this.jumpScore );
            student.setHandsScore( this.handsScore );
            student.setAbsResult( this.absResult );
            student.setAerobicResult( this.aerobicResult );
            student.setJumpResult( this.jumpResult );
            student.setHandsResult( this.handsResult );
            student.setCubesResult( this.cubesResult );
            student.setTotalScore( this.totalScore );
            student.setTotScoreWithoutAerobic( this.totalScoreWithoutAerobic );
            student.setUpdateDate( this.updatedDate );
            return student;
        }

    }

    private Student() {

    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getGender() {
        return gender;
    }

    void setGender( String gender ) {
        this.gender = gender;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    void setUpdateDate( String date ) {
        this.updatedDate = date;
    }

    void setAerobicResult( double aerobicResult ) {
        this.aerobicResult = aerobicResult;
    }

    void setCubesResult( double cubesResult ) {
        this.cubesResult = cubesResult;
    }

    void setAbsResult( double absResult ) {
        this.absResult = absResult;
    }

    void setJumpResult( double jumpResult ) {
        this.jumpResult = jumpResult;
    }

    void setHandsResult( double handsResult ) {
        this.handsResult = handsResult;
    }

    void setStudentClass( String studentClass ) {
        this.studentClass = studentClass;
    }

    public double getAerobicScore() {
        return aerobicScore;
    }

    void setAerobicScore( double aerobicScore ) {
        this.aerobicScore = aerobicScore;
    }

    public double getCubesScore() {
        return cubesScore;
    }

    void setCubesScore( double cubesScore ) {
        this.cubesScore = cubesScore;
    }

    public double getAbsScore() {
        return absScore;
    }

    void setAbsScore( double absScore ) {
        this.absScore = absScore;
    }

    public double getJumpScore() {
        return jumpScore;
    }

    void setJumpScore( double jumpScore ) {
        this.jumpScore = jumpScore;
    }

    public double getHandsScore() {
        return handsScore;
    }

    void setHandsScore( double handsScore ) {
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

    public void setUserId( String userId ) {
        this.userId = userId;
    }


    public double getTotalScore() {
        return totalScore;
    }

    void setTotalScore( double totalScore ) {
        this.totalScore = totalScore;
    }

    public double getTotScoreWithoutAerobic() {
        return totalScoreWithoutAerobic;
    }

    void setTotScoreWithoutAerobic( double totalScoreWithoutAerobic ) {
        this.totalScoreWithoutAerobic = totalScoreWithoutAerobic;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    boolean isFinished() {
        final int MISSING = - 1;
        return aerobicScore != MISSING && jumpScore != MISSING && absScore != MISSING
                && handsScore != MISSING && cubesScore != MISSING;


    }

    public String toString() {
        return "\nStudent name: " + name + "\nClass: " + studentClass + "\nPhone: " + "\'" + phoneNumber + "\'"
                + " \nGender: " + gender
                + "\nAerobic Score: " + aerobicScore
                + "\ncubesScore: " + cubesScore + "\nabsScore: " + absScore + "\njumpScore: " + jumpScore
                + "\nHandsScore: " + handsScore + "\nTotalScore: " + totalScore
                + "\nKey: " + key;
    }

    String[] toArray() {
        String aerobicScoreString = String.valueOf( aerobicScore );
        String aerobicResultString = String.valueOf( aerobicResult );
        String absScoreString = String.valueOf( absScore );
        String absResultString = String.valueOf( absResult );
        String handsScoreString = String.valueOf( handsScore );
        String handsResultString = String.valueOf( handsResult );
        String jumpScoreString = String.valueOf( jumpScore );
        String jumpResultString = String.valueOf( jumpResult );
        String cubesScoreString = String.valueOf( cubesScore );
        String cubesResultString = String.valueOf( cubesResult );
        String totalScoreString = String.valueOf( totalScore );
        return new String[]{
                name, studentClass, aerobicScoreString, aerobicResultString,
                absScoreString, absResultString, handsScoreString, handsResultString,
                cubesScoreString, cubesResultString, jumpScoreString, jumpResultString,
                totalScoreString
        };
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
        gender = in.readString();
        key = in.readString();
        userId = in.readString();
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
        dest.writeString( gender );
        dest.writeString( key );
        dest.writeString( userId );
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
