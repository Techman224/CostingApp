package com.softeng.jobcosting.jobcostingapp.Database.stub;

/**
 * Created by joseph on 2016-03-11.
 */
public class Orders {

    private String date;

    public Orders(String date) {
        this.date = date;
    }

    public void edit(String date) {
        this.date = date;
    }

    public String getDate() { return date;}

}
