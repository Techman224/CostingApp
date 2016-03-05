package com.softeng.jobcosting.jobcostingapp.Database;


public class ActualDatabase implements Database {
	public boolean insert(String field, String value) {
		return true;
	}
	
	public boolean update(String field, String value, String condField, String condValue) {
		return true;
	}
	
	public boolean select() {
		return true;
	}
	
	public boolean where(String field, String value) {
		return true;
	}
	
	public boolean where(String[] fields, String field, String value) {
		return true;
	}
	
	public boolean where(String[] fields, String innerQuery) {
		return true;
	}
	
	public void setTable(String tableName) {
	}
	
	public String query() {
		return null;
	}
}