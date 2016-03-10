package com.softeng.jobcosting.jobcostingapp.Database;

import android.provider.BaseColumns;

/**
 * Created by Alan on 09/03/2016.
 */
public final class CostsOrdersContract {
    /*
     * The query to create the Orders table
     */
    public static final String SQL_CREATE_ORDERS =
            "CREATE TABLE IF NOT EXISTS " + OrdersEntry.TABLE_NAME + " ("
                    + OrdersEntry.COLUMN_NAME_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + OrdersEntry.COLUMN_NAME_DATE + " DATE NOT NULL"
            + ");";
    /*
     * The query to create the Costs table
     */
    public static final String SQL_CREATE_COSTS =
            "CREATE TABLE IF NOT EXISTS " + CostsEntry.TABLE_NAME + " ("
                    + CostsEntry.COLUMN_NAME_COST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CostsEntry.COLUMN_NAME_ORDER_ID + " INTEGER NOT NULL, "
                    + CostsEntry.COLUMN_NAME_STORE + "TEXT, "
                    + CostsEntry.COLUMN_NAME_DESCRIPTION + "TEXT, "
                    + CostsEntry.COLUMN_NAME_TYPE + "TEXT, "
                    + CostsEntry.COLUMN_NAME_PRICE + "REAL NOT NULL, "
                    + "FOREIGN KEY " + CostsEntry.COLUMN_NAME_ORDER_ID + " REFERENCES " + OrdersEntry.TABLE_NAME + "(" + OrdersEntry.COLUMN_NAME_ORDER_ID + ")"
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

    public CostsOrdersContract() {
        //Do Nothing (implemented so that nothing happens)
    }

    /*
     * The schema for the Costs table
     */
    public static abstract class CostsEntry implements BaseColumns {
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
    public static abstract class OrdersEntry implements BaseColumns {
        public static final String TABLE_NAME = "Orders";
        public static final String COLUMN_NAME_ORDER_ID = "OrderID";
        public static final String COLUMN_NAME_DATE = "Date";
    }
}
