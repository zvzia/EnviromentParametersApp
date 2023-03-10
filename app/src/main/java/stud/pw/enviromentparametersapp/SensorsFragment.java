package stud.pw.enviromentparametersapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import stud.pw.enviromentparametersapp.models.Sensor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorsFragment extends Fragment {

    ArrayList<Sensor> sensorsList;
    ListView list;
    private SensorsListAdapter adapter;
    public SensorsFragment() {
        // Required empty public constructor
    }

    public static SensorsFragment newInstance() {
        SensorsFragment fragment = new SensorsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DbMock dbMock = new DbMock();
        sensorsList = dbMock.getSensorsList();

        list = (ListView) view.findViewById(R.id.sensorsList);
        adapter = new SensorsListAdapter(getActivity(), sensorsList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sensor sensor = sensorsList.get(i);

                Intent mIntent = new Intent(getActivity(), SensorActivity.class);
                mIntent.putExtra("sensorId", sensor.getId());
                startActivity(mIntent);
            }
        });

    }
}