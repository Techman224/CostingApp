package com.softeng.jobcosting.jobcostingapp.Database;

/**
 * Created by Alan on 11/04/2016.
 */
public class CostsTable extends Table {
    public CostsTable() {
        super ("Costs",
                new TableColumn ("CostID", ColumnType.INTEGER, true, "PRIMARY KEY", "AUTOINCREMENT"),
                new TableColumn("OrderID", ColumnType.INTEGER, true),
                new TableColumn("Store", ColumnType.TEXT, false),
                new TableColumn("Description", ColumnType.TEXT, false),
                new TableColumn("Type", ColumnType.TEXT, false),
                new TableColumn("Price", ColumnType.REAL, true)
        );
    }

    public String create() {
        String createQuery = "";

        createQuery += "CREATE TABLE IF NOT EXISTS " + super.getName() + " (";

        TableColumn[] cols = super.getColumns();
        for(int j=0;j < cols.length;j++) {
            createQuery += cols[j].getName() + " " + cols[j].getType();
            if (cols[j].hasFlag("PRIMARY KEY")) {
                createQuery += " PRIMARY KEY";
            } else if (cols[j].isRequired()) {
                createQuery += " NOT NULL";
            }

            for(String flag : cols[j].getFlags()) {
                if(!flag.equalsIgnoreCase("PRIMARY KEY")) {
                    createQuery += " " + flag;
                }
            }

            if(j < cols.length - 1) {
                createQuery += ", ";
            }
        }

        //+ "FOREIGN KEY " + Costs.COLUMN_NAME_ORDER_ID + " REFERENCES " + Orders.TABLE_NAME + "(" + Orders.COLUMN_NAME_ORDER_ID + ")"
        createQuery += ");";

        return createQuery;
    }

    public String drop() {
        return "DROP TABLE IF EXISTS " + super.getName() + ";";
    }
}