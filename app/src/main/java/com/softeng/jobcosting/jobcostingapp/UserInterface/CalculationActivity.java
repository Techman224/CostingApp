package com.softeng.jobcosting.jobcostingapp.UserInterface;

import com.softeng.jobcosting.jobcostingapp.Database.Database;
import com.softeng.jobcosting.jobcostingapp.Database.GlobalDatabase;

public class CalculationActivity {
    public Database db;

    public CalculationActivity() {
        db = GlobalDatabase.getDB();
    }

    public String newOrder() {
        db.setTable("Orders");
        db.insert("Date", "Date()");
        return db.query();
    }

    public String newItem(String store, String description, String type, String price) {
        String result = null;

        db.setTable("Costs");

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
        db.setTable("Costs");
        boolean success = db.update(field, newValue, "CostID", Integer.toString(costID));
    }

    public String getItems(int orderID) {
        db.setTable("Costs");
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