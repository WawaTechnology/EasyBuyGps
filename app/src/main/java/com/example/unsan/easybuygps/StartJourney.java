package com.example.unsan.easybuygps;

/**
 * Created by Unsan on 9/4/18.
 */

public class StartJourney {
    String carNumber;
    String customerAddress;

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public long getTimvalue() {
        return timvalue;
    }

    public void setTimvalue(long timvalue) {
        this.timvalue = timvalue;
    }

    long timvalue;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    String date;

    public StartJourney(String carNumber, String date, String startTime, String startPosition,String customerAddress, boolean finished,long timevalue) {
        this.carNumber = carNumber;
        this.date = date;
        this.startTime = startTime;
        this.startPosition = startPosition;
        this.customerAddress=customerAddress;
        this.finished = finished;
        this.timvalue=timevalue;
    }

    String startTime;
    String startPosition;
    boolean finished;

}
