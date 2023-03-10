package stud.pw.enviromentparametersapp;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import stud.pw.enviromentparametersapp.models.Sensor;
import stud.pw.enviromentparametersapp.models.TemperatureRecord;

public class DbMock {
    private final ArrayList<Sensor> sensorsList;
    private final ArrayList<TemperatureRecord> tempRecords;

    public DbMock() {
        sensorsList = new ArrayList<>();
        sensorsList.add(new Sensor(1, "Salon"));
        sensorsList.add(new Sensor(2, "Sypialnia"));
        sensorsList.add(new Sensor(3, "Kuchnia"));

        tempRecords = new ArrayList<>();
        tempRecords.add(new TemperatureRecord(1, 21.0F, 1, getDate(2023, 0, 1, 12, 30)));
        tempRecords.add(new TemperatureRecord(2, 21.5F, 1, getDate(2023, 0, 2, 12, 30 )));
        tempRecords.add(new TemperatureRecord(3, 21.4F, 1, getDate(2023, 0, 3, 12, 30 )));
        tempRecords.add(new TemperatureRecord(4, 21.6F, 1, getDate(2023, 0, 4, 12, 30 )));
        tempRecords.add(new TemperatureRecord(5, 21.2F, 1, getDate(2023, 0, 5, 12, 30 )));

        tempRecords.add(new TemperatureRecord(8, 21.0F, 1, getDate(2023, 2, 1, 12, 30)));
        tempRecords.add(new TemperatureRecord(9, 21.3F, 1, getDate(2023, 2, 2, 12, 30)));
        tempRecords.add(new TemperatureRecord(10, 21.2F, 1, getDate(2023, 2, 3, 12, 30)));

        tempRecords.add(new TemperatureRecord(6, 21.3F, 2, getDate(2023, 0, 5, 12, 30 )));
        tempRecords.add(new TemperatureRecord(7, 21.1F, 3, getDate(2023, 0, 5, 12, 30 )));
    }

    public ArrayList<Sensor> getSensorsList() {
        return sensorsList;
    }

    public ArrayList<TemperatureRecord> getTempRecords() {
        return tempRecords;
    }

    public ArrayList<TemperatureRecord> getLastTemperatures(){
        ArrayList<TemperatureRecord> lastTempRecords = new ArrayList<>();
        lastTempRecords.add(new TemperatureRecord(5, 21.2F, 1, getDate(2023, 0, 5, 12, 30 )));
        lastTempRecords.add(new TemperatureRecord(6, 21.3F, 2, getDate(2023, 0, 5, 12, 30 )));
        lastTempRecords.add(new TemperatureRecord(7, 21.1F, 3, getDate(2023, 0, 5, 12, 30 )));

        return lastTempRecords;
    }

    public String getLastUpdateDateString(){
        Date lastDate = new Date(2023, 1, 5, 12, 30 );
        String dateString = String.valueOf(lastDate.getHours()) + ":" +  String.valueOf(lastDate.getMinutes()) + " " + String.valueOf(lastDate.getDate()) + "." + String.valueOf(lastDate.getMonth()) + "." + String.valueOf(lastDate.getYear());
        return dateString;
    }

    public ArrayList<TemperatureRecord> getTemperaturesForSensor(int id){
        ArrayList<TemperatureRecord> sensorTempRecords = new ArrayList<>();

        for (TemperatureRecord record:tempRecords) {
            if(record.getSensorId() == id){
                sensorTempRecords.add(record);
            }
        }
        return sensorTempRecords;
    }

    public ArrayList<TemperatureRecord> getTemperaturesForSensorInRange(int id, Date start, Date end){
        ArrayList<TemperatureRecord> sensorTempRecords = new ArrayList<>();

        for (TemperatureRecord record:tempRecords) {
            if(record.getSensorId() == id && record.getDate().after(start) && record.getDate().before(end)){
                sensorTempRecords.add(record);
            }
        }
        return sensorTempRecords;
    }

    private static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal.getTime();
    }
}
