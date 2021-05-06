package com.sagikor.android.fitracker.ui.statistics;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
import com.google.android.material.snackbar.Snackbar;
import com.sagikor.android.fitracker.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity implements
        StatisticsActivityContract.View {

    private BarChart averageGradesChart;
    private PieChart pieChart;
    private List<BarEntry> barEntryList;
    private List<String> labelsName;
    private StatisticsActivityContract.Presenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new StatisticsActivityPresenter();
        presenter.bind(this);
        setupColumnsChart();
        setupPieChart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
        presenter = null;
    }

    private void setupPieChart() {
        setPieChartSetting();
        setPieEntriesSetting();
    }

    private void setPieChartSetting() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(11f);
        pieChart.setHoleRadius(20f);

    }

    private void setPieEntriesSetting() {
        List<PieEntry> categories = new ArrayList<>();
        final String DONE = getString(R.string.done);
        final String UNDONE = getString(R.string.undone);
        final String SCHOOL_NAME = getString(R.string.school_name);
        presenter.calculateFinishedNoOfStudents();
        categories.add(new PieEntry(presenter.getFinishedNo(), DONE));
        categories.add(new PieEntry(presenter.getUnFinishedNo(), UNDONE));

        PieDataSet pieDataSet = new PieDataSet(categories, SCHOOL_NAME);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(3f);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
    }

    private void setupColumnsChart() {
        createLabelsName();
        createBarEntryList();
        createBarDataSet();
        setXAxisSetting();
        setGradesChartSetting();
    }

    private void createBarDataSet() {
        final String SCHOOL_NAME = getString(R.string.school_name);
        BarDataSet barDataSet = new BarDataSet(barEntryList, SCHOOL_NAME);
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.5f);
        averageGradesChart.setData(data);
    }

    private void setGradesChartSetting() {
        averageGradesChart.getDescription().setEnabled(false);
        averageGradesChart.animateY(2000);
        averageGradesChart.invalidate();
    }

    private void setXAxisSetting() {
        XAxis xAxis = averageGradesChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsName.size());
        xAxis.setLabelRotationAngle(315);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(13f);
    }

    private void createBarEntryList() {
        presenter.calculateGradesAverage();
        barEntryList = new ArrayList<>();
        barEntryList.add(new BarEntry(0, presenter.getAerobicGradesAverage()));
        barEntryList.add(new BarEntry(1, presenter.getAbsGradesAverage()));
        barEntryList.add(new BarEntry(2, presenter.getHandsGradeAverage()));
        barEntryList.add(new BarEntry(3, presenter.getCubesGradeAverage()));
        barEntryList.add(new BarEntry(4, presenter.getJumpGradesAverage()));
    }

    private void createLabelsName() {
        final String AEROBIC = getString(R.string.aerobic);
        final String ABS = getString(R.string.abs);
        final String CUBES = getString(R.string.cubes);
        final String HANDS = getString(R.string.hands);
        final String JUMP = getString(R.string.jump);

        labelsName = new ArrayList<>();
        labelsName.add(AEROBIC);
        labelsName.add(ABS);
        labelsName.add(CUBES);
        labelsName.add(HANDS);
        labelsName.add(JUMP);
    }

    private void bindViews() {
        averageGradesChart = findViewById(R.id.avg_grades_chart);
        pieChart = findViewById(R.id.finished_chart);
    }

    @Override
    public void popMessage(String message, msgType type) {
        int backgroundColor;
        switch (type) {
            case success:
                backgroundColor = getColor(R.color.colorPrimary);
                break;
            case alert:
                backgroundColor = getColor(R.color.alert);
                break;
            case dangerous:
                backgroundColor = getColor(R.color.red);
                break;
            default:
                backgroundColor = getColor(R.color.black);
        }
        View contextView = findViewById(R.id.statistics_activity_root);
        Snackbar.make(contextView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)
                .show();
    }
}
