package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.Comparator;

/**
 * Created by joseph on 2016-03-11.
 */
public class CostComparator implements Comparator<Cost> {

    public enum attribute {OrderID, Store, Description, Type, Price}

    private attribute sortingBy = attribute.OrderID;

    @Override
    public int compare(Cost cost1, Cost cost2) {
        if (sortingBy == attribute.OrderID)     return Integer.compare(cost1.getOrderID(), cost2.getOrderID());
        else if (sortingBy == attribute.Store)  return cost1.getStore().compareTo(cost2.getStore());
        else if (sortingBy == attribute.Description)    return cost1.getDescription().compareTo(cost2.getDescription());
        else if (sortingBy == attribute.Type)   return cost1.getType().compareTo(cost2.getType());
        else if (sortingBy == attribute.Price)  return Double.compare(cost1.getPrice(), cost2.getPrice());

        throw new RuntimeException("Unreachable code");
    }

    public void setSortingBy(attribute sortBy) {
        this.sortingBy = sortBy;
    }
}
