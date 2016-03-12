package com.softeng.jobcosting.jobcostingapp.BusinessLogic;

import com.softeng.jobcosting.jobcostingapp.Database.Database;
import com.softeng.jobcosting.jobcostingapp.Database.GlobalDatabase;

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

    public String newItem(int orderID, String store, String description, String type, float price) {
        String result = null;

        db.setTable("Costs");

        //if all inserts were successful, returns the result of the query method
        if (db.insert("OrderID", Integer.toString(orderID)) &&
                db.insert("Store", store) &&
                db.insert("Description", description) &&
                db.insert("Type", type) &&
                db.insert("Price", Float.toString(price))) {
            result = db.query();
        }
        return result;
    }

    public String editItem(String field, String newValue, int costID) {
        String result = null;

        db.setTable("Costs");

        if(db.update(field, newValue, "CostID", Integer.toString(costID)))
        {
            result = db.query();
        }

        return result;
    }

    public String getItems(int orderID) {
        String result = null;

        db.setTable("Costs");

        if(db.where("OrderID", Integer.toString(orderID)))
        {
            result = db.query();
        }

        return result;
    }

    public String getDate(int orderID)
    {
        String result = null;

        db.setTable("Orders");

        if(db.where("OrderID", Integer.toString(orderID)))
        {
            result = db.query();

            if(result.length() > 2)
            {
                result = result.substring(2);
            }
        }

        return result;
    }

    public int[] getOrderIDs()  {

        db.setTable("Orders");
//        db.select();
        String []orderTable = db.query().split("\n");
        String unfmtdNums = "";

        for(int i = 0; i < orderTable.length; i++)  {
            unfmtdNums += orderTable[i].split(",")[0];
            if(i != orderTable.length-1)   {
                unfmtdNums += ",";
            }
        }

        String[] unfmtdArray = unfmtdNums.split(",");
        int[] orderNums = new int[unfmtdArray.length];

        for(int i = 0; i < orderNums.length; i++)   {
            orderNums[i] = Integer.parseInt(unfmtdArray[i]);
        }



        return orderNums;

    }

    public float getProfit(int orderID) {
        float profit = 0;

        profit = getTotal(orderID) - getPSTCharged(orderID);

        return profit;
    }

    public float getMargin(int orderID) {
        float margin = 0;

        margin = getProfit(orderID) / getBoardTotal(orderID);

        return margin;
    }

    public float getTotal(int orderID) {
        float total = 0;
        String query = null;
        String[] tokens = null;
        String[] fieldsToReturn = {"Price"};

        db.setTable("Costs");

        if(db.where(fieldsToReturn, "OrderID", Integer.toString(orderID)))
        {
            query = db.query();
            tokens = query.split("\n");

            for(int i = 0; i < tokens.length; i++)
            {
                total += Float.parseFloat(tokens[i]);
            }
        }

        return total;
    }

    private float getPSTCharged(int orderID)
    {
        float PSTCharged = 0;
        String query = null;
        String[] tokens = null;
        String[] fieldsToReturn = {"Price"};

        db.setTable("Costs");

        if(db.where(fieldsToReturn, "Type", "PSTCharged"))
        {
            query = db.query();
            tokens = query.split("\n");

            for(int i = 0; i < tokens.length; i++)
            {
                PSTCharged += Float.parseFloat(tokens[i]);
            }
        }

        return PSTCharged;
    }

    private float getBoardTotal(int orderID)
    {
        float boardTotal = 0;
        String query = null;
        String[] tokens = null;
        String[] fieldsToReturn = {"Price"};

        db.setTable("Costs");

        if(db.where(fieldsToReturn, "Type", "Board"))
        {
            query = db.query();
            tokens = query.split(",");

            for(int i = 0; i < tokens.length; i++)
            {
                boardTotal += Float.parseFloat(tokens[i]);
            }
        }

        return boardTotal;
    }
}