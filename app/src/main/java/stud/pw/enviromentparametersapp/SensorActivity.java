package stud.pw.enviromentparametersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import stud.pw.enviromentparametersapp.models.ParametersRecord;

public class SensorActivity extends AppCompatActivity {

    private ArrayList<ParametersRecord> parametersRecords;
    private int sensorId;
    Cartesian cartesianTemp;
    Line seriesTemp;
    Cartesian cartesianHumid;
    Line seriesHumid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Intent mIntent = getIntent();
        sensorId = mIntent.getIntExtra("sensorId", 0);

        DbMock dbMock = new DbMock();
        setTitle(dbMock.getSensorById(sensorId).getName());


        initializeCharts();
    }

    public void showDatePicker(View view) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Pair<Long, Long> dates = (Pair<Long, Long>) selection;
                Date start = new Date(dates.first);
                Date end = new Date(dates.second);

                updateCharts(start, end);
            }
        });

    }

    private void initializeCharts(){

        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime();
        cal.add(Calendar.DATE, -30);
        Date start  =cal.getTime();

        DbMock dbMock = new DbMock();
        parametersRecords = dbMock.getRecordsForSensorInRange(sensorId, start, end);

        //--- temperature chart ---
        AnyChartView anyChartViewTemp = findViewById(R.id.temperature_chart);
        APIlib.getInstance().setActiveAnyChartView(anyChartViewTemp);
        cartesianTemp = AnyChart.line();
        cartesianTemp.animation(true);
        cartesianTemp.title("Temperatures");

        List<DataEntry> seriesDataTemp = new ArrayList<>();
        for (ParametersRecord record : parametersRecords) {
            seriesDataTemp.add(new CustomDataEntryTemp(record));
        }

        seriesTemp = cartesianTemp.line(seriesDataTemp);
        seriesTemp.name("Temperature");
        seriesTemp.color("#4F5783");

        anyChartViewTemp.setChart(cartesianTemp);


        //--- humidity chart ---
        AnyChartView anyChartViewHumid = findViewById(R.id.humidity_chart);
        APIlib.getInstance().setActiveAnyChartView(anyChartViewHumid);
        cartesianHumid = AnyChart.line();
        cartesianHumid.animation(true);
        cartesianHumid.title("Humidity");

        List<DataEntry> seriesDataHumid = new ArrayList<>();
        for (ParametersRecord record : parametersRecords) {
            seriesDataHumid.add(new CustomDataEntryHumid(record));
        }

        seriesHumid = cartesianHumid.line(seriesDataHumid);
        seriesHumid.name("Humidity");
        seriesHumid.color("#4F5783");

        anyChartViewHumid.setChart(cartesianHumid);

    }

    private void updateCharts(Date start, Date end){
        DbMock dbMock = new DbMock();
        parametersRecords = dbMock.getRecordsForSensorInRange(sensorId, start, end);

        //--- temperature chart ---
        AnyChartView anyChartViewTemp = findViewById(R.id.temperature_chart);
        APIlib.getInstance().setActiveAnyChartView(anyChartViewTemp);

        List<DataEntry> seriesDataTemp = new ArrayList<>();
        for (ParametersRecord record : parametersRecords) {
            seriesDataTemp.add(new CustomDataEntryTemp(record));
        }

        cartesianTemp.removeAllSeries();
        seriesTemp = cartesianTemp.line(seriesDataTemp);
        seriesTemp.color("#4F5783");

        //--- humidity chart ---
        AnyChartView anyChartViewHumid = findViewById(R.id.humidity_chart);
        APIlib.getInstance().setActiveAnyChartView(anyChartViewHumid);

        List<DataEntry> seriesDataHumid = new ArrayList<>();
        for (ParametersRecord record : parametersRecords) {
            seriesDataHumid.add(new CustomDataEntryHumid(record));
        }

        cartesianHumid.removeAllSeries();
        seriesHumid = cartesianHumid.line(seriesDataHumid);
        seriesHumid.color("#4F5783");
    }


    private class CustomDataEntryTemp extends ValueDataEntry {
        CustomDataEntryTemp(ParametersRecord record) {
            super(record.getDateString(), record.getTemperature());
        }

    }

    private class CustomDataEntryHumid extends ValueDataEntry {
        CustomDataEntryHumid(ParametersRecord record) {
            super(record.getDateString(), record.getHumidity());
        }

    }
}