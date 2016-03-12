package com.softeng.jobcosting.jobcostingapp.Database;


import com.softeng.jobcosting.jobcostingapp.Database.stub.Cost;
import com.softeng.jobcosting.jobcostingapp.Database.stub.Order;

import java.util.ArrayList;
import java.util.Date;

public class ActualDatabase implements Database {

	@Override
	public void addOrder(Date date) {

	}

	@Override
	public boolean deleteOrder(int orderID) {
		return false;
	}

	@Override
	public Cost addCost(int orderId, String store, String description, String type, double price) {
		return null;
	}

	@Override
	public boolean deleteCost(int index) {
		return false;
	}

	@Override
	public ArrayList<Order> sortOrders() {
		return null;
	}

	@Override
	public ArrayList<Cost> sortCosts(String attribute) {
		return null;
	}
}