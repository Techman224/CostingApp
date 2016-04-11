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
        db.insert("Date", "01/01/2001");
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
                db.insert("Price", String.format("%.2f", price))) {
            result = db.query();
        }
        return result;
    }

    public boolean deleteOrder(int orderID) {
        boolean valid = false;

        db.setTable("Orders");

        if(db.delete("OrderID", String.valueOf(orderID))) {
            db.query();
            valid = true;
        }

        return valid;
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
//        db.select();

        if(db.where("OrderID", Integer.toString(orderID))) {
            result = db.query();

            if(result.equals("")) {
                System.out.println("Results are empty");
                result = null;
            }
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
        db.select();
        String strTable = db.query();
        String []orderTable = null;
        int []orderNums = null;

        if(strTable != null&& !strTable.equals("")) {
            orderTable = strTable.split("\n");
            String unfmtdNums = "";

            for (int i = 0; i < orderTable.length; i++) {
                unfmtdNums += orderTable[i].split(",")[0];
                if (i != orderTable.length - 1) {
                    unfmtdNums += ",";
                }
            }

            String[] unfmtdArray = unfmtdNums.split(",");
            orderNums = new int[unfmtdArray.length];

            for (int i = 0; i < orderNums.length; i++) {
                orderNums[i] = Integer.parseInt(unfmtdArray[i]);
            }
        }


        return orderNums;

    }

    public float getProfit(int orderID) {
        float profit = 0;

        //profit = getOrderTotal(orderID) - getTypeTotal(orderID, "PSTCharged");
        profit += getOrderTotal(orderID);
        profit -= (2 * getTypeTotal(orderID, "PSTCharged"));

        return profit;
    }

    public float getMargin(int orderID) {
        float margin = 0;

        //margin = getProfit(orderID) / getTypeTotal(orderID, "Board");
        margin += getProfit(orderID);
        margin /= getTypeTotal(orderID, "Board");

        return margin;
    }

    public float getOrderTotal(int orderID) {
        float total = 0;
        String query = null;
        String[] tokens = null;
        String[] trimmedTokens = null;
        String[] fieldsToReturn = {"Price"};

        db.setTable("Costs");

        if(db.where(fieldsToReturn, "OrderID", Integer.toString(orderID)))
        {
            query = db.query();
            tokens = query.split("\n");

            if(query.equals(""))
            {
                total = 0.00f;
            }
            else
            {
                for(int i = 0; i < tokens.length; i++)
                {
                    trimmedTokens = tokens[i].split(",");
                    total += Float.parseFloat(trimmedTokens[0]);
                }
            }
        }

        return total;
    }

    public float getTypeTotal(int orderID, String type)
    {
        float typeTotal = 0;
        String query = null;
        String[] tokens = null;
        String[] trimmedTokens = null;
        String[] fieldsToReturn = {"Price"};

        db.setTable("Costs");

        if(db.where(fieldsToReturn, "Type", type))
        {
            db.where(fieldsToReturn, "OrderID", Integer.toString(orderID));
            query = db.query();
            tokens = query.split("\n");

            if(query.equals(""))
            {
                typeTotal = 0.00f;
            }
            else
            {
                for(int i = 0; i < tokens.length; i++)
                {
                    trimmedTokens = tokens[i].split(",");
                    typeTotal += Float.parseFloat(trimmedTokens[0]);
                }
            }
        }

        return typeTotal;
    }

    public float getOverallTypeTotal(String type)
    {
        float overallTypeTotal = 0;
        int[] orderIDs = getOrderIDs();

        for(int i = 0; i < orderIDs.length; i++)
        {
            overallTypeTotal += getTypeTotal(orderIDs[i], type);
        }

        if(type.equals("Margin"))
        {
            overallTypeTotal /= orderIDs.length;
        }

        return overallTypeTotal;
    }

    public float getTotalSold()
    {
        float totalSold = 0;
        int[] orderIDs = getOrderIDs();

        for(int i = 0; i < orderIDs.length; i++)
        {
            totalSold += getTypeTotal(orderIDs[i], "Board");
            totalSold += getTypeTotal(orderIDs[i], "Accessories");
        }

        return totalSold;
    }
}