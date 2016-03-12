package com.softeng.jobcosting.jobcostingapp.Database.stub;

import com.softeng.jobcosting.jobcostingapp.Database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by joseph on 2016-03-10.
 * Indexes will change when you delete
 */
public class SimpleStub implements Database {

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

    public Cost addCost(int orderId, String store, String description, String type, double price) {
        boolean added = false;
        Cost newCost = null;

        if (orders.get(orderId) == null) {
            added = false;
        }
        else {
            newCost = new Cost(orderId,store,description,type,price);
            costs.add(newCost);
        }

        return newCost;
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

        OrderComparator comparator = new OrderComparator();
        Collections.sort(sortedOrders,comparator);

        return sortedOrders;
    }

    public ArrayList<Cost> sortCosts(String attribute) {
        boolean invalidAttribute = false;
        ArrayList<Cost> sortedCosts = new ArrayList<Cost>();
        CostComparator comparator = new CostComparator();

        if (attribute.equals("orderid"))   comparator.setSortingBy(CostComparator.attribute.OrderID);
        else if (attribute.equals("store")) comparator.setSortingBy(CostComparator.attribute.Store);
        else if (attribute.equals("description")) comparator.setSortingBy(CostComparator.attribute.Description);
        else if (attribute.equals("type")) comparator.setSortingBy(CostComparator.attribute.Type);
        else if (attribute.equals("price")) comparator.setSortingBy(CostComparator.attribute.Price);
        else invalidAttribute = true;

        if (!invalidAttribute) {
            for (Cost cost : costs) {
                sortedCosts.add(cost);
            }

            Collections.sort(sortedCosts,comparator);
        }
        else {
            sortedCosts = null;
        }

        return sortedCosts;
    }


}
