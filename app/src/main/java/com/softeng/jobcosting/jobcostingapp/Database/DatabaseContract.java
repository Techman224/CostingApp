package com.softeng.jobcosting.jobcostingapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Alan on 09/03/2016.
 */
public final class DatabaseContract {
    /*
     * The query to create the Orders table
     */
    public static final String SQL_CREATE_ORDERS =
            "CREATE TABLE IF NOT EXISTS " + Orders.TABLE_NAME + " ("
                    + Orders.COLUMN_NAME_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Orders.COLUMN_NAME_DATE + " DATE NOT NULL"
            + ");";
    /*
     * The query to create the Costs table
     */
    public static final String SQL_CREATE_COSTS =
            "CREATE TABLE IF NOT EXISTS " + Costs.TABLE_NAME + " ("
                    + Costs.COLUMN_NAME_COST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Costs.COLUMN_NAME_ORDER_ID + " INTEGER NOT NULL, "
                    + Costs.COLUMN_NAME_STORE + "TEXT, "
                    + Costs.COLUMN_NAME_DESCRIPTION + "TEXT, "
                    + Costs.COLUMN_NAME_TYPE + "TEXT, "
                    + Costs.COLUMN_NAME_PRICE + "REAL NOT NULL, "
                    + "FOREIGN KEY " + Costs.COLUMN_NAME_ORDER_ID + " REFERENCES " + Orders.TABLE_NAME + "(" + Orders.COLUMN_NAME_ORDER_ID + ")"
            + ");";
    /*
     * The query to drop (delete) the Orders table
     */
    public static final String SQL_DROP_ORDERS =
            "DROP TABLE IF EXISTS " + OrdersEntry.TABLE_NAME + ";";
    /*
     * The query to drop (delete) the Costs table
     */
    public static final String SQL_DROP_COSTS =
            "DROP TABLE IF EXISTS " + CostsEntry.TABLE_NAME + ";";

    public DatabaseContract() {
        //Do Nothing (implemented so that nothing happens)
    }

    /*
     * The schema for the Costs table
     */
    public static abstract class Costs implements BaseColumns {
        public static final String TABLE_NAME = "Costs";
        public static final String COLUMN_NAME_COST_ID = "CostID";
        public static final String COLUMN_NAME_ORDER_ID = "OrderID";
        public static final String COLUMN_NAME_STORE = "Store";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_PRICE = "Price";
    }

    /*
     * The schema for the Orders table
     */
    public static abstract class Orders implements BaseColumns {
        public static final String TABLE_NAME = "Orders";
        public static final String COLUMN_NAME_ORDER_ID = "OrderID";
        public static final String COLUMN_NAME_DATE = "Date";
    }
}
