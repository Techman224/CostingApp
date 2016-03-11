package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.Date;

/**
 * Created by joseph on 2016-03-11.
 */
public class Order {

    private Date date;

    public Order(Date date) {
        this.date = date;
    }

    public void edit(Date date) {
        this.date = date;
    }

    public Date getDate() { return date;}

}
