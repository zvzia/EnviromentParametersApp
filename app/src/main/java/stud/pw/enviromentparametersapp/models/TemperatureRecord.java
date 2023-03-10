package stud.pw.enviromentparametersapp.models;

import java.util.Date;

public class TemperatureRecord {
    int id;
    float value;
    int sensorId;
    Date date;

    public TemperatureRecord(int id, float value, int sensorId, Date date) {
        this.id = id;
        this.value = value;
        this.sensorId = sensorId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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
        return String.valueOf(date.getHours()) + ":" +  String.valueOf(date.getMinutes()) + " " + String.valueOf(date.getDate()) + "." + String.valueOf(date.getMonth()) + "." + String.valueOf(date.getYear());
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
