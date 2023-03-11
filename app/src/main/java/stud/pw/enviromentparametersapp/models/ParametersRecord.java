package stud.pw.enviromentparametersapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParametersRecord {
    int id;
    float temperature;
    int humidity;
    int sensorId;
    Date date;

    public ParametersRecord(int id, float temperature, int humidity, int sensorId, Date date) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.sensorId = sensorId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
