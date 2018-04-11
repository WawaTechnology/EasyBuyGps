package com.example.unsan.easybuygps;

import java.io.Serializable;

/**
 * Created by Unsan on 9/4/18.
 */

public class CustomerNode implements Serializable {

    String restaurantName;
    Customer customer;
    public CustomerNode()
    {

    }
    public CustomerNode(String restaurantName,Customer customer)
    {
        this.restaurantName=restaurantName;
        this.customer=customer;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
