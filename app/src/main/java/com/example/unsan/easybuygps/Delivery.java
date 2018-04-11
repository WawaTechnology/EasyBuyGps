package com.example.unsan.easybuygps;

/**
 * Created by Unsan on 9/4/18.
 */

public class Delivery {
    String startTime;
    String deliveryTime;
    String deliveryDate;
    String photo;
    long timeval;
    String customer;
    String destinationAddress;

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    String startingAddress;
    String gpsDestinationAddress;
    String carLocation;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getGpsDestinationAddress() {
        return gpsDestinationAddress;
    }

    public void setGpsDestinationAddress(String gpsDestinationAddress) {
        this.gpsDestinationAddress = gpsDestinationAddress;
    }

    public boolean isReached() {
        return reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    boolean reached;


    String carNumber;
    String driverName;

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(String startingAddress) {
        this.startingAddress = startingAddress;
    }





    public Delivery(long timeval,String startTime, String deliveryTime,String deliveryDate, String photo, String customer, String destinationAddress, String startingAddress, String carNumber, String driverName,String gpsDestinationAddress) {
        this.timeval=timeval;
        this.startTime = startTime;
        this.deliveryTime = deliveryTime;
        this.deliveryDate=deliveryDate;
        this.photo = photo;

        this.customer = customer;
        this.destinationAddress = destinationAddress;
        this.startingAddress = startingAddress;

        this.carNumber = carNumber;
        this.driverName = driverName;
        this.gpsDestinationAddress=gpsDestinationAddress;

    }



    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }




    public Delivery(String startTime, String deliveryTime, String photo, long timeval, String customer) {
        this.startTime = startTime;
        this.deliveryTime = deliveryTime;
        this.photo = photo;
        this.timeval = timeval;
        this.customer = customer;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getTimeval() {
        return timeval;
    }

    public void setTimeval(long timeval) {
        this.timeval = timeval;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public Delivery()
    {

    }
}

