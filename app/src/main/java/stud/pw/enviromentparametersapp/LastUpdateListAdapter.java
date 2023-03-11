package stud.pw.enviromentparametersapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;

import stud.pw.enviromentparametersapp.models.Sensor;
import stud.pw.enviromentparametersapp.models.ParametersRecord;

public class LastUpdateListAdapter extends ArrayAdapter<Sensor> {
    ArrayList<Sensor> sensors;
    ArrayList<ParametersRecord> records;
    Context mContext;

    public LastUpdateListAdapter(@NonNull Context context, ArrayList<Sensor> sensors, ArrayList<ParametersRecord> records) {
        super(context, R.layout.home_list_element, sensors);
        this.sensors = sensors;
        this.records = records;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sensor sensor = getItem(position);
        ParametersRecord parametersRecord = records.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_element, parent, false);
        }

        // Lookup view for data population
        TextView sensorName = (TextView) convertView.findViewById(R.id.sensorName);
        TextView temperature = (TextView) convertView.findViewById(R.id.temperature);
        TextView humidity = (TextView) convertView.findViewById(R.id.humidity);

        // Populate the data into the template view using the data object
        sensorName.setText(sensor.getName());
        temperature.setText(String.valueOf(parametersRecord.getTemperature()) + "°C");
        humidity.setText(String.valueOf(parametersRecord.getHumidity()) + "%");
        // Return the completed view to render on screen
        return convertView;
    }


}
