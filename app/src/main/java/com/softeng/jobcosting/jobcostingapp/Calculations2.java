package com.softeng.jobcosting.jobcostingapp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Calculations2
{
	private AtomicInteger atomicInteger = new AtomicInteger();

	public static void main(String[] args)
	{
		Database db = new Database();
	}

	public static int newOrder()
	{
		int newOrderID = AtomicInteger.get();
		insert("date", date());
		return newOrderID;
	}

	public static String newItem(String store, String description, String type, String price)
	{
		String result = null;

		if(insert("store", store) &&
				insert("description", description) &&
				insert("type", type) &&
				insert("price", price))
		{
			result = query();
		}
		else
		{
			// return null
		}

		return result;
	}

	public static void editItem(String store, String description, String type, float price)
	{

	}
}