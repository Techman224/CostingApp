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

    public String newItem(String orderID, String store, String description, String type, String price) {
        String result = null;

        db.setTable("Costs");

        //if all inserts were successful, returns the result of the query method
        if (db.update("Store", store, "OrderID", orderID) &&
                db.update("Description", description, "OrderID", orderID) &&
                db.update("Type", type, "OrderID", orderID) &&
                db.update("Price", price, "OrderID", orderID)) {
            result = db.query();
        }

        return result;
    }

    public void editItem(String field, String newValue, int costID) {
        db.setTable("Costs");
        boolean success = db.update(field, newValue, "CostID", Integer.toString(costID));
    }

    public String getItems(int orderID) {
        db.setTable("Costs");
        boolean success = db.where("OrderID", Integer.toString(orderID));
        return db.query();
    }

    public int[] getOrderIDs()  {
        return null;
    }

    /*public float getProfit() {

    }

    public float getMargin() {

    }

    public float getTotal() {

    }*/
}