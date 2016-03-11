package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by joseph on 2016-03-10.
 */
public class SimpleStub {

    // The primary key is the index
    private ArrayList<Order> orders;   //The orders table (OrderID, Date)
    private ArrayList<Cost> costs;     //The costs table (CostID, OrderID, Store, Description, Type, Price)

    public SimpleStub() {
        orders = new ArrayList<Order>();
        costs = new ArrayList<Cost>();
    }

    public void addOrder(Date date) {
        Order newOrder = new Order(date);
        orders.add(newOrder);


    }

    public boolean deleteOrder(int index) {
        boolean deleted = false;

        if (orders.get(index) != null) {
            orders.remove(index);
            deleted = true;
        }

        return deleted;
    }

    public boolean addCost(int orderId, String store, String description, String type, double price) {
        boolean added = false;

        if (orders.get(orderId) == null) {
            added = false;
        }
        else {
            Cost newCost = new Cost(orderId,store,description,type,price);
            costs.add(newCost);
        }

        return added;
    }

    public boolean deleteCost(int index) {
        boolean deleted = false;

        if (costs.get(index) == null) {
            orders.remove(index);
            deleted = true;
        }

        return deleted;
    }

    public ArrayList<Order> sortOrders() {
        ArrayList<Order> sortedOrders = new ArrayList<Order>();
        for (Order order : orders) {
            sortedOrders.add(order);
        }

        Collections.sort(sortedOrders);

        return sortedOrders;
    }


}
