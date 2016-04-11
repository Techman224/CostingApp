package com.softeng.jobcosting.jobcostingapp.Database.stub;

import com.softeng.jobcosting.jobcostingapp.Database.Database;
import com.softeng.jobcosting.jobcostingapp.Database.QueryBuilder;
import com.softeng.jobcosting.jobcostingapp.Database.QueryType;
import com.softeng.jobcosting.jobcostingapp.Database.stub.StubDB;

/*
 * Name: Database
 * Description: The class that provides the interface and implementation of the database for the app.
 * Author: Alan Bridgeman
 * Date: 07/02/2016
 */
public class StubDatabase implements Database {
	private StubDB db; 		//The Stub Databas
	private QueryBuilder query; 	//The active SQL query
	private String table;
	
	/*
	 * Name: Database (Default Constructor)
	 * Description: Initalizes the Database (stub for the moment) and sets the active query initally to null 
	 */
	public StubDatabase() {
		db = new StubDB();
		query = new QueryBuilder();
		query.setTable("Costs");
	}

	/*
	 * Name: insert
	 * Description: A public insert method that allows other parts of the app to insert stuff
	 * 				into the database abstracted away from the implementation of the database
	 * 				Note: This method builds a query but DOESN'T execute it (see: query).
	 * Parameter(s): field (String) - The field to which you wish to insert the value
	 * 				 value (String) - The value to be inserted in the specified column
	 * Return: boolean - If the insertion query was properly built
	 */
	public boolean insert(String field, String value) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("INSERT INTO");
		}
		else {
			valid = (query.getType() == QueryType.INSERT_INTO);
		}

		if(valid) {
			valid = query.addColumn(field);
			if (valid) {
				valid = query.addValue(value);
			}
		}

		return valid;
	}

	/*
	 * Name: update
	 * Description: A public update method that allows other parts of the app to update stuff
	 * 				in the database abstracted away from the implementation of the database
	 * 				Note: This method builds a query but DOESN'T execute it (see: query)
	 * Parameter(s): field (String) - The field to be updated
	 * 				 value (String) - The value to update the field with
	 * 				 condField (String) - The field for the WHERE claus
	 * 				 condValue (String) - The value for the WHERE claus
	 * Return: boolean - If the update query was properly built
	 */
	public boolean update(String field, String value, String condField, String condValue) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("UPDATE");
		}
		else {
			valid = (query.getType() == QueryType.UPDATE);
		}

		if(valid) {
			valid = query.addColumn(field);
			if (valid) {
				valid = query.addValue(value);
				if(valid) {
					valid = query.addCondition(condField, condValue);
				}
			}
		}

		return valid;
	}

	public boolean delete(String field, String value) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("DELETE");
		}
		else {
			valid = (query.getType() == QueryType.DELETE);
		}

		if(valid) {
			valid = query.addCondition(field, value);
		}

		return valid;
	}

	public boolean select() {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("SELECT");
		}
		else {
			valid = (query.getType() == QueryType.SELECT);
		}

		return valid;
	}

	/*
	 * Name: where
	 * Description: A public method that allows other parts of the app to get stuff from the
	 * 				database based on a criteria abstracted away from the implementation of
	 * 				the database
	 * 				Note: This method builds a query but DOESN'T execute it (see: query)
	 * Parameter(s): field (String) - The field portion of the condition
	 * 				 value (String) - The value portion of the condition
	 * Return: boolean - If the where query was properly built
	 */
	public boolean where(String field, String value) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("SELECT");
		}
		else {
			valid = (query.getType() == QueryType.SELECT);
		}

		if(valid) {
			valid = query.addCondition(field, value);
		}

		return valid;
	}
	/*
	 * Name: where
	 * Description: A public method that allows other parts of the app to get stuff from the
	 * 				database based on a criteria abstracted away from the implementation of
	 * 				the database
	 * 				Note: This method builds a query but DOESN'T execute it (see: query)
	 * Parameter(s): fields (String[]) - The fields of the database to return
	 * 				 field (String) - The field portion of the condition
	 * 				 value (String) - The value portion of the condition
	 * Return: boolean - If the where query was properly built
	 */
	public boolean where(String[] fields, String field, String value) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("SELECT");
		}
		else {
			valid = (query.getType() == QueryType.SELECT);
		}

		if(valid) {
			for(String currField : fields) {
				if(valid) {
					valid = query.addColumn(currField);
				}
			}
			if(valid) {
				valid = query.addCondition(field, value);
			}
		}

		return valid;
	}
	/*
	 * Name: where
	 * Description: A public method that allows other parts of the app to get stuff from the
	 * 				database based on a criteria abstracted away from the implementation of
	 * 				the database
	 * 				Note: This method builds a query but DOESN'T execute it (see: query)
	 * Parameter(s): fields (String[]) - The fields of the database to return
	 * 				 innerQuery (String) - The inner SELECT statement to be added
	 * Return: boolean - If the where query was properly built
	 */
	public boolean where(String[] fields, String innerQuery) {
		boolean valid = false;

		if(query.getType() == null) {
			valid = query.setType("SELECT");
		}
		else {
			valid = (query.getType() == QueryType.SELECT);
		}

		if(valid) {
			for(String currField : fields) {
				if(valid) {
					valid = query.addColumn(currField);
				}
			}
			if(valid) {
				valid = false;//query.addCondition(field, value);
			}
		}

		return valid;
	}

	public boolean sort(String field, boolean descending) {
		boolean valid = false;

		if(query.getType() != null) {
			valid = (query.getType() == QueryType.SELECT);
		}

		if(valid) {
			valid = query.sortInDescending(field, descending);
		}

		return valid;
	}

	public void setTable(String tableName) {
		query.setTable(tableName);
	}
	/*
	 * Name: query
	 * Description: 
	 * Parameter(s): N/A 
	 * Return: String - The results of the query
	 */
	public String query() {
		String results = db.query(query.build()); //Execute the query
		query = null; //Reset the query to empty
		return results; //Return the results
	}
}