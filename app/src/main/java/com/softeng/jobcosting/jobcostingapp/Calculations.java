package com.softeng.jobcosting.jobcostingapp;

public class Calculations {
    private Database db;

    public Calculations() {
        db = GlobalDatabase.getDB();
    }

    public String newOrder() {
        db.setTable("Orders");
        db.insert("Date", "Date()");
        return db.query();
    }

    public String newItem(String store, String description, String type, String price) {
        String result = null;

        //if all inserts were successful, returns the result of the query method
        if(db.insert("Store", store) &&
                db.insert("Description", description) &&
                db.insert("Type", type) &&
                db.insert("Price", price)) {
            result = db.query();
        }

        return result;
    }

    public void editItem(String field, String newValue, int costID) {
        boolean success = db.update(field, newValue, "CostID", Integer.toString(costID));
    }

    public String getItems(int orderID) {
        boolean success = db.where("OrderID", Integer.toString(orderID));
        return db.query();
    }

    /*public float getProfit() {

    }

    public float getMargin() {

    }

    public float getTotal() {

    }*/
}