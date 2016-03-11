package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by joseph on 2016-03-11.
 */
public class Order implements Comparable<Order> {

    private Date date;

    public Order(Date date) {
        this.date = date;
    }

    public void edit(Date date) {
        this.date = date;
    }


    @Override
    public int compareTo(Order another) {
        return getDate().compareTo(another.getDate());
    }


    public Date getDate() { return date;}


}
