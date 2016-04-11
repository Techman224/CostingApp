package com.softeng.jobcosting.jobcostingapp.Database;

import java.util.ArrayList;

/**
 * Created by Alan on 26/03/2016.
 */
public class QueryBuilder {
    private DatabaseContract contract;

    private QueryType type;
    private String table;
    private ArrayList<String> fields;
    private ArrayList<String> values;
    private ArrayList<String> conditions;
    private boolean sortFlag;
    private String sortField;
    private boolean ascending;
    private boolean groupFlag;
    private String groups;

    public QueryBuilder() {
        contract = new DatabaseContract();

        type = null;
        table = null;
        fields = new ArrayList<String>();
        values = new ArrayList<String>();
        conditions = new ArrayList<String>();
        sortFlag = false;
        sortField = null;
        ascending = true;
        groupFlag = false;
        groups = null;
    }

    public boolean setType(String word) {
        boolean isValid = false;

        for(QueryType queryType : QueryType.values()) {
            if(queryType.name().replace("_"," ").equalsIgnoreCase(word)) {
                type = queryType;
                isValid = true;
            }
        }

        return isValid;
    }
    public QueryType getType() {
        return type;
    }

    public boolean setTable(String tableName) {
        boolean isValid = false;

        if(contract.hasTable(tableName)) {
            table = tableName;
            isValid = true;
        }

        return isValid;
    }
    public String getTable() {
        return table;
    }

    public boolean addColumn(String column) {
        boolean isValid = false;

        if(table != null) {
            if (contract.getTable(table).hasColumnNamed(column)) {
                fields.add(column);
                isValid = true;
            }
        }

        return isValid;
    }

    public boolean addValue(String value) {
        boolean isValid = false;

        if(fields.size() > values.size()) {
            switch(contract.getTable(table).getColumn(fields.get(values.size())).getTypeObject()) {
                case TEXT:
                    values.add("'" + value + "'");
                    isValid = true;
                    break;
                default:
                    values.add(value);
                    isValid = true;
                    break;
            }
        }

        return isValid;
    }

    public boolean addCondition(String column, String value) {
        boolean isValid = false;

        if(table != null) {
            if(contract.getTable(table).hasColumnNamed(column)) {
                switch(contract.getTable(table).getColumn(column).getTypeObject()) {
                    case TEXT:
                        value = "'" + value + "'";
                        break;
                }
            }
            conditions.add(column + "=" + value);
            isValid = true;
        }

        return isValid;
    }

    public boolean sortInAscending(String column, boolean ascend) {
        boolean isValid = false;

        if(table != null) {
            if (contract.getTable(table).hasColumnNamed(column)) {
                sortFlag = true;
                sortField = column;
                ascending = ascend;
                isValid = true;
            }
        }

        return isValid;
    }
    public boolean sortInDescending(String column, boolean descend) {
        boolean isValid = false;

        if(table != null) {
            if (contract.getTable(table).hasColumnNamed(column)) {
                sortFlag = true;
                sortField = column;
                ascending = !descend;

                isValid = true;
            }
        }

        return isValid;
    }

    public String build() {
        String query = null;

        if(type != null) {
            query = type.getFormat();

            if(type.hasTable() && table != null) {
                query = query.replace("[table]", table);
            }

            if(type.hasFields() && type.hasValues() && type.hasConditions()) {
                if(fields.size() > 0 && values.size() > 0 && fields.size() == values.size()) {
                    String replaceStr = "";

                    for(int j=0;j < fields.size();j++) {
                        replaceStr += fields.get(j) + "=" + values.get(j);
                        if (j < fields.size() - 1) {
                            replaceStr += ",";
                        }
                    }

                    query = query.replace("[fields]=[values]",replaceStr);
                }
                else {
                    //Error: You are attempting to set an uneven number of fields to values
                }
            }
            else {
                if (type.hasFields()) {
                    if(fields.size() > 0) {
                        String replaceStr = "";

                        for (int j = 0; j < fields.size(); j++) {
                            replaceStr += fields.get(j);
                            if (j < fields.size() - 1) {
                                replaceStr += ",";
                            }
                        }

                        query = query.replace("[fields]", replaceStr);
                    }
                    else if(type == QueryType.SELECT) {
                        query = query.replace("([fields])", "*");
                    }
                }
                if (type.hasValues() && values.size() > 0) {
                    String replaceStr = "";

                    for (int j = 0; j < values.size(); j++) {
                        replaceStr += values.get(j);
                        if (j < values.size() - 1) {
                            replaceStr += ",";
                        }
                    }

                    query = query.replace("[values]", replaceStr);
                }
            }

            if(type.hasConditions() && conditions.size() > 0) {
                String replaceStr = "";

                for(int j=0;j < conditions.size();j++) {
                    replaceStr += conditions.get(j);
                    if(j < conditions.size() - 1) {
                        replaceStr += ",";
                    }
                }

                query = query.replace("[conditions]",replaceStr);
            }

            if(sortFlag) {
                query += "ORDER BY " + sortField;
                query += (ascending) ? "ASC" : "DESC";
            }

            if(query.contains("[") || query.contains("]")) {
                if(type == QueryType.SELECT && query.contains("[conditions]")) {
                    query = query.substring(0, query.indexOf("WHERE"));
                }
                //Error: The values provided didn't properly match the SQL format
            }
        }
        else {
            //Error: There is no query started or an illegal type was given
        }

        System.out.println(query);

        type = null;
        fields = new ArrayList<String>();
        values = new ArrayList<String>();
        conditions = new ArrayList<String>();
        sortFlag = false;
        sortField = null;
        ascending = true;
        groupFlag = false;
        groups = null;

        return query;
    }
}