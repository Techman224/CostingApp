package com.softeng.jobcosting.jobcostingapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActualDatabase extends SQLiteOpenHelper implements Database {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "jobcostingapp.db";

	private QueryBuilder query;

	public ActualDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		query = new QueryBuilder();
		query.setTable("Costs");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		DatabaseContract contract = new DatabaseContract();
		Table[] tables = contract.getTables();
		for(Table table : tables) {
			db.execSQL(table.create());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DatabaseContract contract = new DatabaseContract();
		Table[] tables = contract.getTables();
		for(Table table : tables) {
			db.execSQL(table.drop());
		}
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
				valid = false;//query.addCondition(field, innerQuery);
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
		SQLiteDatabase db = this.getWritableDatabase();
		String result = null;

		boolean isInsert = (query.getType() == QueryType.INSERT_INTO);

		Cursor c = null;
		c = db.rawQuery(query.build(), null);

		if(isInsert){
			String resultQuery = "SELECT * FROM " + query.getTable() + " ORDER BY OrderID DESC limit 1";
			c = db.rawQuery(resultQuery, null);
		}

		result = "";

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
				result += ",";
				col++;
			}
			result += "\n";
			c.moveToNext();
		}
		c.close();

		return result;
	}
}