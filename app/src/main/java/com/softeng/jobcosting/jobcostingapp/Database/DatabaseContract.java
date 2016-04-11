package com.softeng.jobcosting.jobcostingapp.Database;

import java.util.ArrayList;

/**
 * Created by Alan on 09/03/2016.
 */
public class DatabaseContract {
    private ArrayList<Table> tables;

    public DatabaseContract() {
        Table costs = new CostsTable();
        Table orders = new OrdersTable();

        tables = new ArrayList<Table>();
        tables.add(costs);
        tables.add(orders);
    }

    public Table[] getTables() {
        Table[] returnTables = {};
        return tables.toArray(returnTables);
    }
    public Table getTable(String name) {
        Table returnTable = null;

        for(Table table : tables) {
            if(table.getName().equalsIgnoreCase(name)) {
                returnTable = table;
            }
        }

        return returnTable;
    }
    public boolean hasTable(String tableName) {
        boolean has = false;
        for(Table table : tables) {
            if(table.getName().equalsIgnoreCase(tableName)) {
                has = true;
            }
        }
        return has;
    }
}
