package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.Comparator;

/**
 * Created by joseph on 2016-03-11.
 */
public class OrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order order1, Order order2) {
        return order1.getDate().compareTo(order2.getDate());
    }

}
