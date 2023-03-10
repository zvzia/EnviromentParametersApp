package stud.pw.enviromentparametersapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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

import stud.pw.enviromentparametersapp.models.TemperatureRecord;

public class SensorActivity extends AppCompatActivity {

    private ArrayList<TemperatureRecord> temperatureRecords;
    private int sensorId;
    private AnyChartView anyChartView;
    private Date start = null;
    private Date end = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Intent mIntent = getIntent();
        sensorId = mIntent.getIntExtra("sensorId", 0);

        setTitle(String.valueOf(sensorId));


        /*Calendar cal = Calendar.getInstance();
        Date end = cal.getTime();
        cal.add(Calendar.DATE, -30);
        Date start =cal.getTime();*/

        //showChart();
    }

    public void showDatePicker(View view) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Pair<Long, Long> dates = (Pair<Long, Long>) selection;
                start = new Date(dates.first);
                end = new Date(dates.second);

                showChart();
            }
        });

    }

    private void showChart(){
        if(start == null && end == null){
            Calendar cal = Calendar.getInstance();
            end = cal.getTime();
            cal.add(Calendar.DATE, -30);
            start =cal.getTime();
        }
        DbMock dbMock = new DbMock();
        temperatureRecords = dbMock.getTemperaturesForSensorInRange(sensorId, start, end);

        anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        //cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        //cartesian.title("Wykres odczytów");
        cartesian.yAxis(0).title("Temperatura");
        cartesian.xAxis(0).labels().padding(1d, 1d, 1d, 1d);

        List<DataEntry> seriesData = new ArrayList<>();
        for (TemperatureRecord record : temperatureRecords) {
            seriesData.add(new CustomDataEntry(record));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        //series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


        /*cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);*/

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(TemperatureRecord record) {
            super(record.getDateString(), record.getValue());
        }

    }
}