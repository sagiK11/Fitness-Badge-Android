package com.sagikor.android.fitracker;


import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {

    private BarChart averageGradesChart;
    private PieChart pieChart;
    private float aerobicGradesAverage;
    private float absGradesAverage;
    private float handsGradeAverage;
    private float cubesGradeAverage;
    private float jumpGradesAverage;
    private List<BarEntry> barEntryList;
    private List<String> labelsName;
    private static final int FINISHED_INDEX = 0;
    private static final int UNFINISHED_INDEX = 1;
    private static final int MISSING = - 1;


    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_statistics );

        linkObjects();
        setupColumnsChart();
        setupPieChart();

    }

    private void setupPieChart() {
        setPieChartSetting();
        setPieEntriesSetting();
    }

    private void setPieChartSetting() {

        pieChart.setUsePercentValues( true );
        pieChart.getDescription().setEnabled( false );
        pieChart.setExtraOffsets( 5, 10, 5, 5 );
        pieChart.setDragDecelerationFrictionCoef( 0.95f );
        pieChart.setDrawHoleEnabled( true );
        pieChart.setHoleColor( Color.BLACK );
        pieChart.setTransparentCircleRadius( 11f );
        pieChart.setHoleRadius( 20f );

    }

    private void setPieEntriesSetting() {
        List<PieEntry> categories = new ArrayList<>();
        float[] finishedArray = new float[ 2 ];
        getFinishedStudentNum( finishedArray );
        final String DONE = getResources().getString( R.string.done );
        final String UNDONE = getResources().getString( R.string.undone );
        final String SCHOOL_NAME = getResources().getString( R.string.school_name );

        categories.add( new PieEntry( finishedArray[ FINISHED_INDEX ], DONE ) );
        categories.add( new PieEntry( finishedArray[ UNFINISHED_INDEX ], UNDONE ) );


        PieDataSet pieDataSet = new PieDataSet( categories, SCHOOL_NAME );
        pieDataSet.setSliceSpace( 3f );
        pieDataSet.setSelectionShift( 3f );
        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );

        PieData data = new PieData( pieDataSet );
        data.setValueTextSize( 12f );
        data.setValueTextColor( Color.WHITE );

        pieChart.setData( data );
    }

    private void getFinishedStudentNum( float[] finishedArray ) {
        float finishedStudentsCounter = 0;
        float unFinishedStudentsCounter = 0;

        for ( Student student : MainActivity.studentList ) {
            if ( student.isFinished() ) {
                finishedStudentsCounter++;
            } else {
                unFinishedStudentsCounter++;
            }
        }
        finishedArray[ FINISHED_INDEX ] = finishedStudentsCounter;
        finishedArray[ UNFINISHED_INDEX ] = unFinishedStudentsCounter;
    }

    private void setupColumnsChart() {
        createLabelsName();
        createBarEntryList();
        createBarDataSet();
        setXAxisSetting();
        setGradesChartSetting();
    }

    private void createBarDataSet() {
        final String SCHOOL_NAME = getResources().getString( R.string.school_name );
        BarDataSet barDataSet = new BarDataSet( barEntryList, SCHOOL_NAME );
        barDataSet.setColors( ColorTemplate.LIBERTY_COLORS );
        BarData data = new BarData( barDataSet );
        data.setBarWidth( 0.5f );
        averageGradesChart.setData( data );
    }

    private void setGradesChartSetting() {
        averageGradesChart.getDescription().setEnabled( false );
        averageGradesChart.animateY( 2000 );
        averageGradesChart.invalidate();
    }

    private void setXAxisSetting() {
        XAxis xAxis = averageGradesChart.getXAxis();
        xAxis.setValueFormatter( new IndexAxisValueFormatter( labelsName ) );
        xAxis.setPosition( XAxis.XAxisPosition.BOTTOM );
        xAxis.setGranularity( 1f );
        xAxis.setLabelCount( labelsName.size() );
        xAxis.setLabelRotationAngle( 315 );
        xAxis.setDrawAxisLine( false );
        xAxis.setTextSize( 13f );
    }

    private void createBarEntryList() {
        calculateGradesAverage();
        barEntryList = new ArrayList<>();
        barEntryList.add( new BarEntry( 0, aerobicGradesAverage ) );
        barEntryList.add( new BarEntry( 1, absGradesAverage ) );
        barEntryList.add( new BarEntry( 2, handsGradeAverage ) );
        barEntryList.add( new BarEntry( 3, cubesGradeAverage ) );
        barEntryList.add( new BarEntry( 4, jumpGradesAverage ) );
    }

    private void createLabelsName() {
        final String AEROBIC = getResources().getString( R.string.aerobic );
        final String ABS = getResources().getString( R.string.abs );
        final String CUBES = getResources().getString( R.string.cubes );
        final String HANDS = getResources().getString( R.string.hands );
        final String JUMP = getResources().getString( R.string.jump );

        labelsName = new ArrayList<>();
        labelsName.add( AEROBIC );
        labelsName.add( ABS );
        labelsName.add( CUBES );
        labelsName.add( HANDS );
        labelsName.add( JUMP );
    }

    private void calculateGradesAverage() {
        calculateAerobicAverage();
        calculateAbsAverage();
        calculateCubesAverage();
        calculateHandsAverage();
        calculateJumpAverage();
    }

    private void linkObjects() {
        averageGradesChart = findViewById( R.id.avg_grades_chart );
        pieChart = findViewById( R.id.finished_chart );

    }

    private void calculateAerobicAverage() {
        int aerobicGradesCounter = 0;
        float sumAerobic = 0;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getAerobicResult() != MISSING ) {
                aerobicGradesCounter++;
                sumAerobic += student.getAerobicResult();
            }
        }
        aerobicGradesAverage = aerobicGradesCounter != 0 ? sumAerobic / aerobicGradesCounter : 0;
    }

    private void calculateHandsAverage() {
        int handsGradesCounter = 0;
        float sumHands = 0;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getHandsResult() != MISSING ) {
                handsGradesCounter++;
                sumHands += student.getHandsResult();
            }
        }
        handsGradeAverage = handsGradesCounter != 0 ? sumHands / handsGradesCounter : 0;
    }


    private void calculateCubesAverage() {
        int cubesGradesCounter = 0;
        float sumCubes = 0;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getCubesResult() != MISSING ) {
                cubesGradesCounter++;
                sumCubes += student.getCubesResult();
            }
        }
        cubesGradeAverage = cubesGradesCounter != 0 ? sumCubes / cubesGradesCounter : 0;
    }


    private void calculateJumpAverage() {
        int jumpGradesCounter = 0;
        float sumJump = 0;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getJumpResult() != MISSING ) {
                jumpGradesCounter++;
                sumJump += student.getJumpResult();
            }
        }
        jumpGradesAverage = jumpGradesCounter != 0 ? sumJump / jumpGradesCounter : 0;
    }


    private void calculateAbsAverage() {
        int absGradesCounter = 0;
        float sumAbs = 0;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getAbsResult() != MISSING ) {
                absGradesCounter++;
                sumAbs += student.getAbsResult();
            }
        }
        absGradesAverage = absGradesCounter != 0 ? sumAbs / absGradesCounter : 0;
    }

}
