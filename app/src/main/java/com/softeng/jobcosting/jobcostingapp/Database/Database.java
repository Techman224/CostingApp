package com.softeng.jobcosting.jobcostingapp.Database;

import com.softeng.jobcosting.jobcostingapp.Database.stub.Order;
import com.softeng.jobcosting.jobcostingapp.Database.stub.Cost;

import java.util.ArrayList;
import java.util.Date;

public interface Database {

	public void addOrder(Date date);
	public boolean deleteOrder(int index);
	public Cost addCost(int orderId, String store, String description, String type, double price);
	public boolean deleteCost(int index);
	public ArrayList<Order> sortOrders();
	public ArrayList<Cost> sortCosts(String attribute);

}