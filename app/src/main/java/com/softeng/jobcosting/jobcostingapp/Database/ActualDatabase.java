package com.softeng.jobcosting.jobcostingapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActualDatabase extends SQLiteOpenHelper implements Database {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "jobcostingapp.db";

	private String query;
	private String table;

	public ActualDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		query = null;
		table = "Costs";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CostsOrdersContract.SQL_CREATE_ORDERS);
		db.execSQL(CostsOrdersContract.SQL_CREATE_COSTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(CostsOrdersContract.SQL_DROP_COSTS);
		db.execSQL(CostsOrdersContract.SQL_DROP_ORDERS);
		onCreate(db);
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

		//Does the query allready exist and if so, is it doing an insert
		if(query == null) {

			//The query doesn't exist so start a insert
			query = "INSERT INTO " + table + " (" + field + ") VALUES (" + value + ");";
			//System.out.println("Built query: " + query);

			//The build happend successfully
			valid = true;
		}
		else if(query.toUpperCase().contains("INSERT INTO")) {
			//The query already exists so try to append the query

			//Get the columns already being inserted
			String columns = query.substring(query.indexOf("(") + 1, query.indexOf(")"));
			String[] queryColumns = columns.split(",");

			//Check that the field matches no existing columsn (don't already have it in the query)
			boolean alreadyExists = false;
			for(int j=0;j < queryColumns.length;j++) {
				if(queryColumns[j].equalsIgnoreCase(field)) {
					alreadyExists = true;
				}
			}

			//If it isn't already being inserted do the proper processing
			if(!alreadyExists) {
				//Append the new column to the end of the setof columns
				columns = columns.concat("," + field);

				//Get the values already being inserted
				String values = query.substring(query.lastIndexOf("(") + 1, query.lastIndexOf(")"));

				//Value validation code to come...

				//Append the new value to the end of the set of values
				values = values.concat("," + value);

				//Rebuild the query with the new column and field sets
				query = "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");";
				//System.out.println("Built query: " + query);

				//The build happend successfully
				valid = true;
			}
			else {
				//Error: The column specified is already being inserted into
			}
		}
		else {
			//Error: Can't attempt a insert while the query is amist doing a different statement
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

		if(field != null && value != null) {
			if(query == null) {
				//The query doesn't exist so start a update
				query = "UPDATE " + table + " SET (" + field + "=" + value + ") WHERE (" + condField + "=" + condValue +");";
				//System.out.println("Built query: " + query);

				//The build happend successfully
				valid = true;
			}
			else if(query.toUpperCase().contains("UPDATE")) {
				//Get the SET part of the UPDATE query
				int setValuesStartPos = query.indexOf("(");
				int setValuesEndPos = query.indexOf(")");
				String setValues = query.substring(setValuesStartPos + 1, setValuesEndPos);
				//Now divide it up into anticedin and consequence so that it is easier to work with
				String[] setValue = setValues.split(",");
				String[][] setValuePair = new String[setValue.length][2];
				for(int j=0;j < setValue.length;j++) {
					setValuePair[j] = setValue[j].split("=");

					if(setValuePair[j][0].equalsIgnoreCase(field)) {
						if(!setValuePair[j][1].equalsIgnoreCase(value)) {
							setValues = setValues.concat("," + field + "=" + value);
						}
					}
					else {
						setValues = setValues.concat("," + field + "=" + value);
					}
				}

				//Get the WHERE part of the UPDATE query
				int condValuesStartPos = query.indexOf("WHERE") + query.substring(query.indexOf("WHERE")).indexOf("(");
				int condValuesEndPos = query.indexOf("WHERE") + query.substring(query.indexOf("WHERE")).indexOf(")");
				String condValues = query.substring(condValuesStartPos + 1, condValuesEndPos);
				String[] preCondValue = condValues.split(",");
				String[][] condValuePair = new String[preCondValue.length][2];
				for(int j=0;j < preCondValue.length;j++) {
					condValuePair[j] = preCondValue[j].split("=");

					if(condValuePair[j][0].equalsIgnoreCase(condField)) {
						if(!condValuePair[j][1].equalsIgnoreCase(condValue)) {
							condValues = condValues.concat("," + condField + "=" + condValue);
						}
					}
					else {
						condValues = condValues.concat("," + condField + "=" + condValue);
					}
				}

				query = query.substring(0, query.indexOf("(") + 1) + setValues + query.substring(query.indexOf(")"), query.lastIndexOf("(") + 1) + condValues + query.substring(query.lastIndexOf(")"));
				//System.out.println("Built Query: " + query);

				valid = true;
			}
			else {
				//Error: Can't attempt a update while the query is amist doing a different statement
			}
		}
		else {
			//Error: Can't update with null parameters
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

		if(field != null && value != null) {
			if(query == null) {
				//The query doesn't exist so start a select
				query = "SELECT * FROM " + table + " WHERE (" + field + " = " + value + ");";
				//System.out.println("Built query: " + query);

				//The build happend successfully
				valid = true;
			}
			else if(query.toUpperCase().contains("SELECT")) {
				//Check if the query already contains a WHERE clause
				if(query.toUpperCase().contains("WHERE")) {
					//Get the conditions already in the query
					String conditions = query.substring(query.lastIndexOf("(") + 1, query.lastIndexOf(")"));
					String[] queryConditions = conditions.split(",");

					//Check that the field doesn't already exist in another condition
					int exists = -1;
					for(int j=0;j < queryConditions.length;j++) {
						String[] tokens = queryConditions[j].split("=");
						if(tokens[0].equalsIgnoreCase(field)) {
							exists = j;
						}
					}

					if(exists == -1) {
						//Append the conditions with that of the one made with the parameters
						conditions.concat("," + field + " = " + value);
						//Rebuild the query string with the new condition
						query = query.substring(0, query.lastIndexOf("(") + 1) + conditions + ");";
						System.out.println("Built query: " + query);

						//The build happend successfully
						valid = true;
					}
					else {
						//Even though the field may exist we want to check if the values match
						String[] tokens = queryConditions[exists].split("=");
						if(tokens[1].equalsIgnoreCase(value)) {

							//Build OR query code to come...

							System.out.println("Built query: " + query);

							//The build happend successfully
							valid = true;
						}
						else {
							//Error: This exact condition already exists
						}
					}
				}
				else {
					//Because the query doesn't have a where clause remove the semicolon and append it
					query = query.substring(0, query.indexOf(";")) + " WHERE (" + field + " = " + value + ");" ;
					//System.out.println("Built query: " + query);

					//The build happend successfully
					valid = true;
				}
			}
			else {
				//Error: Can't attempt a select whille the query is amist doing a different statement
			}
		}
		else {
			//Error: Invalid arguments supplied
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

		//Check the caller is unaware that there is a simpler method call
		if(fields != null) {
			//Check that we don't have any jokers sending null arguments
			if(field != null && value != null) {
				//Check if the query is still being built (havn't queryed yet)
				if(query == null) {
					//The query hasn't been built yet so start to build a new one
					query = "SELECT (";
					for(int j=0;j < fields.length;j++) {
						query += fields[j];
						if(j != fields.length - 1) {
							query += ",";
						}
					}
					query += ") FROM " + table + ";";
					//Reuse this other form of the method so that the code doesn't have to be replicated
					valid = where(field, value);
				}
				else if(query.toUpperCase().contains("SELECT")) {
					//The query is already (somewhat built). So, lets check if it contains a star (all fields alias)
					if(query.toUpperCase().contains("*")) {
						//Its currently selecting for all fields lets remove that and rebuild with the specific columns we want
						String tempQuery = query.substring(0, query.indexOf("*")) + "(";
						for(int j=0;j < fields.length;j++) {
							tempQuery += fields[j];
							if(j != fields.length - 1) {
								tempQuery += ",";
							}
						}
						tempQuery += ")" + query.substring(query.indexOf("*") + 1);
						query = tempQuery;
					}
					else {
						//The columns are already explicit. So, lets look at them
						String columns = query.substring(query.indexOf("(") + 1, query.indexOf(")"));
						String[] columnNames = columns.split(",");
						for(int j=0;j < fields.length;j++) {
							for(int k=0;k < columnNames.length;k++) {
								if(fields[j] != null) {
									//Check if the field we are selecting from already is coming back
									if(fields[j].equalsIgnoreCase(columnNames[k])) {
										fields[j] = null;
									}
								}
							}
						}

						//Go through the list of fields to be return and add them
						for(int j=0;j < fields.length;j++) {
							//Make sure their not already being selected (see above) or some joker gave you inaccuarte data
							if(fields[j] != null) {
								columns.concat("," + fields[j]);
							}
						}

						//We're ready to rebuild the query
						query = query.substring(0, query.indexOf("(") + 1) + columns + query.substring(query.indexOf(")"));
					}

					//Reuse the other version of the method to do the processing for conditional changes etc....
					valid = where(field, value);
				}
				else {
					//Error: Can't attempt a select while the query is amist doing a different statement
				}
			}
			else {
				//Error: Invalid parameters passed
			}
		}
		else {
			//Why bother doing processing just send it to the appropriate method
			valid = where(field, value);
		}

		//Return if the build was successful
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

		if(query == null) {
			query = "SELECT (";
			for(int j=0;j < fields.length;j++) {
				query += fields[j];
				if(j < fields.length - 1) {
					query += ", ";
				}
			}
			query += ") FROM " + table + " WHERE (" + innerQuery + ");";
			//System.out.println("Built Query: " + query);
		}
		else {
			//Error: Can't attempt a select statement while the query is amist doing a different statement
		}

		return valid;
	}

	public void setTable(String tableName) {
		table = tableName;
	}

	/*
	 * Name: query
	 * Description:
	 * Parameter(s): N/A
	 * Return: String - The results of the query
	 */
	public String query() {
		SQLiteDatabase db = this.getWritableDatabase();
		String result = null;

		Cursor c = null;
		c = db.rawQuery(query, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			int col = 0;
			while(col < c.getColumnCount()) {
				switch(c.getType(col)) {
					case Cursor.FIELD_TYPE_INTEGER:
						result += Integer.toString(c.getInt(col));
						break;
					case Cursor.FIELD_TYPE_FLOAT:
						result += Float.toString(c.getFloat(col));
						break;
					case Cursor.FIELD_TYPE_STRING:
						result += c.getString(col);
						break;
					case Cursor.FIELD_TYPE_BLOB:
						result += c.getBlob(col).toString();
						break;
					default:
						//An unknown type was returned.break;
				}
				col++;
			}
			c.moveToNext();
		}
		c.close();

		query = null;

		return result;
	}
}