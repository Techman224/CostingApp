package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.ArrayList;

/**
 * Created by joseph on 2016-03-10.
 */
public class SimpleStub {

    // The primary key is the index
    private ArrayList<Orders> orders;
    private ArrayList<Costs> costs;

    public SimpleStub() {
        orders = new ArrayList<Orders>();
        costs = new ArrayList<Costs>();
    }


    private class Orders {

        private String date;

        public Orders(String date) {
            this.date = date;
        }

        public void edit(String date) {
            this.date = date;
        }

        public String getDate() { return date;}
    }

    private class Costs {

        private int orderID;
        private String store, description, type;
        private double price;

        public Costs(int orderID, String store, String description, String type, double price) {
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
}
