package com.softeng.jobcosting.jobcostingapp.Database;

public interface Database {
	
	/*
	 * Name: Insert
	 * Description: A public insert method that allows other parts of the app to insert stuff 
	 * 				into the database while abstracted away the implementation of the database.
	 * 				That is to say that, this method will "bind" a parameter into the insert
	 * 				query replacing a value at the given field position in the VALUES part of
	 * 				the query so that it may be inserted with that value in the proper column.
	 * 				Note: This method builds a query but DOESN'T execute it (see: query).
	 * Parameter(s): field (String) - The field/column to which you wish to insert the value
	 * 				 value (String) - The value/entity to be inserted in the specified column
	 * Return: boolean - If the insertion query was properly built
	 */
	public boolean insert(String field, String value);
	
	/*
	 * Name: Update
	 * Description: A public update method that allows other parts of the app to update stuff
	 * 				in the database abstracted away from the implementation of the database.
	 * 				In essence this method takes a value and "binds" it to the proper position
	 * 				in the query as to update that column/field with that value. It also takes
	 * 				two other parameters prefixed with cond that are the conditional parts of
	 * 				the update query. That is to say if you had a UPDATE query that looked
	 * 				something like
	 * 				Note: This method builds a query but DOESN'T execute it (see: query)
	 * Parameter(s): field (String) - The field to be updated
	 * 				 value (String) - The value to update the field with
	 * 				 condField (String) - The field for the WHERE claus
	 * 				 condValue (String) - The value for the WHERE claus
	 * Return: boolean - If the update query was properly built
	 */
	public boolean update(String field, String value, String condField, String condValue);

	public boolean delete(String field, String value);

	/*
	 * Name: Select
	 * Description: A public select method that allows other parts of the app to get the complete
	 * 				contents of a table from the database.
	 * 				Note: This method builds a query but DOESN't execute it (see: query)
	 * Parameter(s): N/A
	 * Return: boolean - If the select query was built properly
	 */
	public boolean select();
	
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
	public boolean where(String field, String value);
	
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
	public boolean where(String[] fields, String field, String value);
	
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
	public boolean where(String[] fields, String innerQuery);

	/*
	 * Name: Sort
	 * Description: A public method that will allow other parts of the app to denote how they
	 * 				want the result set to be sorted before it comes back.
	 * Parameter(s): field (String) - The field/column to sort by
	 * 				 descending (boolean) - whether to sort in descending order or not
	 * Return: boolean - If the sort query was properly built
	 */
	public boolean sort(String field, boolean descending);

	/*
	 * Name: Set Table (setTable)
	 * Description: A public method that allows other parts of the app to set the table in which to
	 * 				get stuff from the database out ot
	 * Parameter(s): tableName (String) - The name of the table to be quering
	 * Return: void (N/A)
	 */
	public void setTable(String tableName);
	
	/*
	 * Name: query
	 * Description: 
	 * Parameter(s): N/A 
	 * Return: String - The results of the query
	 */
	public String query();
}