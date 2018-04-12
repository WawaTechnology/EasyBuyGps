package com.example.unsan.easybuygps;

import java.io.Serializable;

/**
 * Created by Unsan on 9/4/18.
 */

public class Customer implements Serializable {

    String ContactPerson;

    public Object getZip() {
        return Zip;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public void setZip(Object zip) {
        Zip = zip;
    }

    public long getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(long contactNumber) {
        ContactNumber = contactNumber;
    }



    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    Object Zip;
    String City;
    public Customer()
    {

    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }



    String Address;
    long ContactNumber;




    public Customer(String Address,String City,long ContactNumber,String ContactPerson,Object Zip)
    {
        this.Address=Address;
        this.City=City;
        this.ContactNumber=ContactNumber;
        this.ContactPerson=ContactPerson;
        this.Zip=Zip;

    }


}
