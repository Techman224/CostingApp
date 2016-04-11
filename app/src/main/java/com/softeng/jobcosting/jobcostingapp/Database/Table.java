package com.softeng.jobcosting.jobcostingapp.Database;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by Alan on 11/04/2016.
 */
public abstract class Table {
    private String tableName;
    private ArrayList<TableColumn> columns;

    public Table(String name, TableColumn... tableCols) {
        tableName = name;
        columns.addAll(Arrays.asList(tableCols));
    }

    public String getName() {
        return tableName;
    }

    public void addColumn(TableColumn newCol) {
        if(!columns.contains(newCol)) {
            columns.add(newCol);
        }
    }
    public void removeColumn(TableColumn col) {
        if(columns.contains(col)) {
            columns.remove(col);
        }
    }
    public TableColumn[] getColumns() {
        TableColumn[] returnColumns = {};
        return columns.toArray(returnColumns);
    }
    public TableColumn getColumn(String name) {
        TableColumn returnCol = null;

        hasColumnNamed(name);
        for(TableColumn col : columns) {
            if(col.getName().equalsIgnoreCase(name)) {
                returnCol = col;
            }
        }

        return returnCol;
    }
    public boolean hasColumn(TableColumn col) {
        return columns.contains(col);
    }
    public boolean hasColumnNamed(String name) {
        boolean has = false;
        for(TableColumn col : columns) {
            if(col.getName().equalsIgnoreCase(name)) {
                has = true;
            }
        }
        return has;
    }

    public abstract String create();
    public abstract String drop();
}
