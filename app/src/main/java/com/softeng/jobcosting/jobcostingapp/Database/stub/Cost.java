package com.softeng.jobcosting.jobcostingapp.Database.stub;

/**
 * Created by joseph on 2016-03-11.
 */
public class Cost {

    private int orderID;
    private String store, description, type;
    private double price;

    public Cost(int orderID, String store, String description, String type, double price) {
        this.orderID = orderID;
        this.store = store;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public void edit(int orderID, String store, String description, String type, double price) {
        this.orderID = orderID;
        this.store = store;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public int getOrderID() { return orderID;}
    public String getStore() { return store;}
    public String getDescription() { return description;}
    public String getType() { return type;}
    public double getPrice() {return price;}

}