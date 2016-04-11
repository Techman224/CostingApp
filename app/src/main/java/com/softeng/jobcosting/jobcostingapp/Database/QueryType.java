package com.softeng.jobcosting.jobcostingapp.Database;

/**
 * Created by Alan on 28/03/2016.
 */
public enum QueryType {
    SELECT("SELECT ([fields]) FROM [table] WHERE ([conditions])"),
    INSERT_INTO("INSERT INTO [table]([fields]) VALUES ([values])"),
    UPDATE("UPDATE [table] SET ([fields]=[values]) WHERE ([conditions])"),
    DELETE("DELETE FROM  [table] WHERE ([conditions])");

    private String format;

    private QueryType(String format) {
        if(format != null) {
            this.format = format;
        }
    }

    public String getFormat() {
        return format;
    }

    public boolean hasTable() {
        return format.contains("[table]");
    }
    public boolean hasFields() {
        return format.contains("[fields]");
    }
    public boolean hasConditions() {
        return format.contains("[conditions]");
    }
    public boolean hasValues() {
        return format.contains("[values]");
    }
}
