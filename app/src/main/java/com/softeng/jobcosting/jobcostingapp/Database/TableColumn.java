package com.softeng.jobcosting.jobcostingapp.Database;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by Alan on 11/04/2016.
 */
public class TableColumn {
    private String name;
    private ColumnType type;
    private boolean required;
    private ArrayList<String> flags;

    public TableColumn(String colName, ColumnType colType, boolean colRequired, String... attributes) {
        name = colName;
        type = colType;
        required = colRequired;
        flags = new ArrayList<String>();
        flags.addAll(Arrays.asList(attributes));
    }

    public String getName() {
        return name;
    }
    public ColumnType getTypeObject() { return type; }
    public String getType() {
        return type.name();
    }
    public boolean isRequired() {
        return required;
    }

    public String[] getFlags() {
        String[] returnArray = {};
        return flags.toArray(returnArray);
    }
    public boolean hasFlag(String flag) {
        flags.contains(flag);
    }
}
