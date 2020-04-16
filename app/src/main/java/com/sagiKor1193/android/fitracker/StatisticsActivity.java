package com.sagiKor1193.android.fitracker;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
    private final String TAG = "StatisticsActivity";
    private BarChart averageGradesChart;
    private PieChart pieChart;
    private float aerobicGradesAverage, absGradesAverage, handsGradeAverage;
    private float cubesGradeAverage, jumpGradesAverage;
    private List<BarEntry> barEntryList;
    private List<String> labelsName;
    private final int FINISHED_INDEX = 0, UNFINISHED_INDEX = 1;

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
        categories.add( new PieEntry( finishedArray[ FINISHED_INDEX ], "סיימו" ) );
        categories.add( new PieEntry( finishedArray[ UNFINISHED_INDEX ], "לא - סיימו" ) );

        PieDataSet pieDataSet = new PieDataSet( categories, "תיכון מקיף בית ירח" );
        pieDataSet.setSliceSpace( 3f );
        pieDataSet.setSelectionShift( 3f );
        pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );

        PieData data = new PieData( pieDataSet );
        data.setValueTextSize( 12f );
        data.setValueTextColor( Color.WHITE );

        pieChart.setData( data );
    }


    private void getFinishedStudentNum( float[] finishedArray ) {
        float finishedStudentsCounter = 0, unFinishedStudentsCounter = 0;

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
        BarDataSet barDataSet = new BarDataSet( barEntryList, "תיכון מקיף בית ירח" );
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
        labelsName = new ArrayList<>();
        labelsName.add( 0, "אירובי" );
        labelsName.add( 1, "בטן" );
        labelsName.add( 2, "ידיים" );
        labelsName.add( 3, "קוביות" );
        labelsName.add( 4, "קפיצה לרוחק" );
    }

    private void calculateGradesAverage() {
        int aerobicGradesCounter = 0, absGradesCounter = 0, jumpGradesCounter = 0;
        int handsGradesCounter = 0, cubesGradesCounter = 0;
        float sumAerobic = 0, sumAbs = 0, sumJump = 0, sumHands = 0, sumCubes = 0;
        final int MISSING = - 1;
        for ( Student student : MainActivity.studentList ) {
            if ( student.getAerobicResult() != MISSING ) {
                aerobicGradesCounter++;
                sumAerobic += student.getAerobicResult();
            }
            if ( student.getAerobicResult() != MISSING ) {
                absGradesCounter++;
                sumAbs += student.getAbsResult();
            }
            if ( student.getAerobicResult() != MISSING ) {
                jumpGradesCounter++;
                sumJump += student.getJumpResult();
            }
            if ( student.getAerobicResult() != MISSING ) {
                handsGradesCounter++;
                sumHands += student.getHandsResult();
            }
            if ( student.getAerobicResult() != MISSING ) {
                cubesGradesCounter++;
                sumCubes += student.getCubesResult();
            }
        }
        aerobicGradesAverage = aerobicGradesCounter != 0 ? sumAerobic / aerobicGradesCounter : 0;
        absGradesAverage = absGradesCounter != 0 ? sumAbs / absGradesCounter : 0;
        handsGradeAverage = handsGradesCounter != 0 ? sumHands / handsGradesCounter : 0;
        jumpGradesAverage = jumpGradesCounter != 0 ? sumJump / jumpGradesCounter : 0;
        cubesGradeAverage = cubesGradesCounter != 0 ? sumCubes / cubesGradesCounter : 0;
    }

    private void linkObjects() {
        averageGradesChart = findViewById( R.id.avg_grades_chart );
        pieChart = findViewById( R.id.finished_chart );

    }


}
