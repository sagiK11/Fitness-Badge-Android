package com.sagiKor1193.android.fitracker;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Student implements Parcelable {
    private String name, studentClass, phoneNumber, updatedDate;
    private double aerobicScore, aerobicResult, cubesScore, cubesResult, absScore, absResult;
    private double jumpScore, jumpResult, handsScore, handsResult, totalScore, totalScoreWithoutAerobic;

    public static class Builder {
        private String name, studentClass, phoneNumber, updatedDate;
        private double aerobicScore, aerobicResult, cubesScore, cubesResult, absScore, absResult;
        private double jumpScore, jumpResult, handsScore, handsResult, totalScore, totalScoreWithoutAerobic;

        Builder( String name ) {
            this.name = name;
        }

        Builder studentClass( String studentClass ) {
            this.studentClass = studentClass;
            return this;
        }

        Builder phoneNumber( String phoneNumber ) {
            this.phoneNumber = phoneNumber;
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
            student.setUpdateDate( Utility.getTodayDate() );
            return student;
        }

    }

    Student() {

    }

    String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    String getStudentClass() {
        return studentClass;
    }

    String getUpdatedDate() {
        return updatedDate;
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

    double getAerobicScore() {
        return aerobicScore;
    }

    void setAerobicScore( double aerobicScore ) {
        this.aerobicScore = aerobicScore;
    }

    double getCubesScore() {
        return cubesScore;
    }

    void setCubesScore( double cubesScore ) {
        this.cubesScore = cubesScore;
    }

    double getAbsScore() {
        return absScore;
    }

    void setAbsScore( double absScore ) {
        this.absScore = absScore;
    }

    double getJumpScore() {
        return jumpScore;
    }

    void setJumpScore( double jumpScore ) {
        this.jumpScore = jumpScore;
    }

    double getHandsScore() {
        return handsScore;
    }

    void setHandsScore( double handsScore ) {
        this.handsScore = handsScore;
    }

    double getAerobicResult() {
        return aerobicResult;
    }

    double getCubesResult() {
        return cubesResult;
    }

    double getAbsResult() {
        return absResult;
    }

    double getJumpResult() {
        return jumpResult;
    }

    double getHandsResult() {
        return handsResult;
    }

    double getTotalScore() {
        return totalScore;
    }

    void setTotalScore( double totalScore ) {
        this.totalScore = totalScore;
    }

    double getTotScoreWithoutAerobic() {
        return totalScoreWithoutAerobic;
    }

    void setTotScoreWithoutAerobic( double totalScoreWithoutAerobic ) {
        this.totalScoreWithoutAerobic = totalScoreWithoutAerobic;
    }


    String getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber( String phoneNumber ) {
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
