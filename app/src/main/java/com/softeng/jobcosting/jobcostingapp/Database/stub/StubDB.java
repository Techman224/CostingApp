package com.softeng.jobcosting.jobcostingapp.Database.stub;

import java.util.Hashtable; 					//For the Stub Database
import java.util.regex.PatternSyntaxException; 	//For the Stub Database (query processing)

/*
 * Name: StubDB
 * Description: The datastructure for the stub database
 * Author: Alan Bridgeman
 * Date: 07/02/2016
 */
public class StubDB {
		private Hashtable<String, String[]> orders; //The orders table (OrderID, Date)
		private Hashtable<String, String[]> costs; //The costs table (CostID, OrderID, Store, Description, Type, Price)
		
		/*
		 * Name: StubDB (Default Constructor)
		 * Description: Initalizes the tables and adds a metadata row for use during querying
		 */
		public StubDB() {
			//Initalize the tables
			orders = new Hashtable<String, String[]>();
			costs = new Hashtable<String, String[]>();
			
			//Add a metadata row that contains the names of the columns
			orders.put("Names", new String[]{"OrderID","Date"});
			costs.put("Names", new String[]{"CostID","OrderID","Store","Description","Type","Price"});
		}
		
		/*
		 * Name: query
		 * Description: A method to query the database (the only entry point into the stub databas)
		 * Parameter(s): query (String) - The SQL query string to process
		 * Return: String - The results of the query
		 */
		public String query(String query) {
			String results = null;
			
			//Check the operation we are trying to do
			if(query.contains("INSERT INTO")) {
				//Call the delegate method
				results = insertQuery(query);
			}
			else if(query.contains("SELECT")) {
				//Call the delegate method
				results = selectQuery(query);
			}
			else if(query.contains("UPDATE")) {
				//Call the delegate method
				results = updateQuery(query);
			}
			
			//Return the results of the query
			return results;
		}
		
		/*
		 * Name: insertQuery
		 * Description: provides the processing for a insert SQL query.
		 * Parameter(s): query (String) - The SQL query string
		 * Return: boolean - If the query was successful 
		 */
		private String insertQuery(String query) {
			String results = null;
			
			//Check for a valid SQL call (ensures constraints for futher processing)
			boolean proper = false;
			try {
				proper = query.matches("(INSERT\\sINTO\\s+\\w+\\s*(\\((\\w+,?\\s*)+\\))?($|\\s)+VALUES\\s+\\((.+,?\\s*)+\\);)?+");
			}
			catch(PatternSyntaxException e) {
				//Error: I'm an idiot apparently and the regular expression failed
			}
			
			if(proper) {
				
				//Get the tablename from the query string
				int tableNameStartPos = query.indexOf("INSERT INTO") + 11;
				int tableNameEndPos = (query.contains("(") && query.indexOf("(") < query.indexOf("VALUES")) ? query.indexOf("(") - 1 : query.indexOf("VALUES") - 1;
				int temp = tableNameStartPos;
				String tableName = stripSpaces(query.substring(tableNameStartPos, tableNameEndPos));
				
				//Get the column names from the query string
				String[] colNames = null;
				if(query.contains("(") && query.indexOf("(") < query.indexOf("VALUES")) {
					int colStartPos = query.indexOf("(");
					int colEndPos = query.indexOf(")");
					String cols = stripSpaces(query.substring(colStartPos + 1, colEndPos));
					colNames = cols.split(",");
					
					for(int j=0;j < colNames.length;j++) {
						colNames[j] = stripSpaces(colNames[j]);
					}
				}
				
				//Get the values from the query string
				int valStartPos = query.indexOf("VALUES") + query.substring(query.indexOf("VALUES")).indexOf("(");
				int valEndPos = query.lastIndexOf(")");
				String vals = query.substring(valStartPos + 1, valEndPos);
				String[] colVals = vals.split(",");
				for(int j=0;j < colVals.length;j++) {
					colVals[j] = stripSpaces(colVals[j]);
				}
				
				//Check if there was explicit column names
				if(colNames != null) {
					//Make sure the number of column names given and the number of values given match
					if(colNames.length == colVals.length) {
						//Get the table's meta information (column names)
						String[] colHeaders = null;
						if(tableName.equalsIgnoreCase("Costs")) {
							colHeaders = costs.get("Names");
						}
						else if(tableName.equalsIgnoreCase("Orders")) {
							colHeaders = orders.get("Names");
						}
						else {
							//Error: Refrences unknown table
						}
						
						//Make sure it was a valid table
						if(colHeaders != null) {
							//Ensure the number of column names (equal to the number of values) provided are consistant with (less than or equal to) the number in the table
							if(colNames.length <= colHeaders.length) {
								//Check that all the column names specified are valid names
								int matches = 0;
								for(int j=0;j < colNames.length;j++) {
									for(int k=0;k < colHeaders.length;k++) {
										if(colNames[j].equalsIgnoreCase(colHeaders[k])) {
											matches++;
										}
									}
								}
								
								if(matches != 0) {
									//Allign the column names with the values (or "" for not given ones) for insertion
									String[] insertVals = new String[colHeaders.length];
									for(int j=0;j < insertVals.length;j++) {
										insertVals[j] = "";
									}
									for(int j=0;j < colNames.length;j++) {
										for(int k=0;k < colHeaders.length;k++) {
											if(colHeaders[k].equalsIgnoreCase(colNames[j])) {
												insertVals[k] = colVals[j];
											}
										}
									}
									
									//Insert the values into the appropriate table
									if(tableName.equalsIgnoreCase("Costs")) {
										if(insertVals[0].equalsIgnoreCase("")) {
											insertVals[0] = Integer.toString(costs.size());
										}
										costs.put(insertVals[0], insertVals);
										results = "";
										for(int j=0;j < insertVals.length;j++) {
											results += insertVals[j];
											if(j < insertVals.length - 1) {
												results += ",";
											}
										}
										results += "\n";
									}
									else if(tableName.equalsIgnoreCase("Orders")) {
										if(insertVals[0].equalsIgnoreCase("")) {
											insertVals[0] = Integer.toString(orders.size());
										}
										if(insertVals[1].equalsIgnoreCase("Date()")) {
											insertVals[1] = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.DATE);
										}
										orders.put(insertVals[0], insertVals);
										
										results = "";
										for(int j=0;j < insertVals.length;j++) {
											results += insertVals[j];
											if(j < insertVals.length - 1) {
												results += ",";
											}
										}
										results += "\n";
									}
								}
								else {
									//Error: One of the specified columns doesn't exist
								}
							}
							else {
								//Error: The number of columns specified is greater the number of columns in the table
							}
						}
					}
					else {
						//Error: Number of column names and number of values don't match (ie. your trying to insert a column with no value or too many values into too few columns)
					}
				}
				else {
					if(tableName.equalsIgnoreCase("Costs")) {
						//Get the costs table's meta information (columns) and make sure the number of values matches
						if(colVals.length == costs.get(new String[]{"Names"}).length) {
							for(int j=0;j < colVals.length;j++) {
								colVals[j] = stripSpaces(colVals[j]);
							}
							//Insert into the table
							costs.put(colVals[0], colVals);
							
							results = "";
							for(int j=0;j < colVals.length;j++) {
								results += colVals[j];
								if(j < colVals.length - 1) {
									results += ",";
								}
							}
							results += "\n";
						}
						else {
							//Error: Number of column names and number of values don't match (ie. your trying to insert a column with no value or too many values into too few columns)
						}
					}
					else if(tableName.equalsIgnoreCase("Orders")) {
						//Get the orders table's meta information (columns) and make sure the number of vlaues matches
						if(colVals.length == orders.get("Names").length) {
							for(int j=0;j < colVals.length;j++) {
								colVals[j] = stripSpaces(colVals[j]);
							}
							//Insert into the table
							orders.put(colVals[0], colVals);
							
							results = "";
							for(int j=0;j < colVals.length;j++) {
								results += colVals[j];
								if(j < colVals.length - 1) {
									results += ",";
								}
							}
							results += "\n";
						}
						else {
							//Error: Number of column names and number of values don't match (ie. your trying to insert a column with no value or too many values into too few columns)
						}
					}
					else {
						//Error: Refrences unknown table
					}
				}
			}
			else {
				//Error: Invalid SQL INSERT INTO statement
			}
			
			return results;
		}
		
		/*
		 * Name: selectQuery
		 * Description: 
		 * Parameter(s): query (String) - The SQL query string
		 * Return: String - The results of the query
		 */
		private String selectQuery(String query) {
			String results = null;
			
			//Check for a valid SQL call (ensures constraints for futher processing)
			//For more details: see the table at the bottom of this file
			boolean proper = false;
			try {
				proper = query.matches("(SELECT\\s+(\\((\\w+,?\\s*)+\\)|\\*)($|\\s)+FROM\\s+\\w+($|\\s)*(WHERE\\s+(\\((\\w+\\s*=\\s*.+,?\\s*)+\\)|\\w+\\s*=\\s*SELECT\\s+(\\((\\w+,?\\s*)+\\)|\\*)($|\\s)+FROM\\s+\\w+($|\\s)*.*))?;)?+");
			}
			catch(PatternSyntaxException e) {
				//Error: I'm an idiot apparently and the regular expression failed
			}
			
			if(proper) {
			    //Get the column names from the query string
				String[] colNames = null;
				if(!(query.contains("*") || (query.contains("*") && query.contains("WHERE") && query.indexOf("(") > query.indexOf("*")))) {
					int colStartPos = query.indexOf("(");
					int colEndPos = query.indexOf(")");
					String cols = query.substring(colStartPos + 1, colEndPos);
					colNames = cols.split(",");
				}
				
				//Get the tablename from the query string
				int tableNameStartPos = query.indexOf("FROM") + 4;
				int tableNameEndPos = -1;
				int temp = tableNameStartPos;
				boolean found = false;
				for(int j=0;j < query.length() - temp;j++) {
					if(!found) {
						if(query.charAt(temp + j) == ' ') {
							tableNameStartPos++;
						}
						else {
							found = true;
						}
					}
					else {
						if(query.charAt(temp + j) == ' ' && tableNameEndPos == -1) {
							tableNameEndPos = temp + j;
						}
					}
				}
				String tableName = query.substring(tableNameStartPos, tableNameEndPos);
				
				//Check if there is a WHERE part
				if(query.toUpperCase().contains("WHERE")) {
					//Check for a inner SELECT statment
					if(query.substring(query.indexOf("WHERE")).contains("SELECT")) {
						//Recursivly call selectQuery for the inner query
						String innerResults = selectQuery(query.substring(query.lastIndexOf("SELECT"),query.lastIndexOf(")")));
						
						//Get the column names for the appropriate table
						String[] colHeaders = null;
						if(tableName.equalsIgnoreCase("Costs")) {
							colHeaders = costs.get("Names");
						}
						else if(tableName.equalsIgnoreCase("Orders")) {
							colHeaders = orders.get("Names");
						}
						else {
							//Error: Refrences unknown table
						}
						
						String[] searchVals = null;
						//Set up an array that holds the values we want in the right columns and nulls in the others
						if(colHeaders != null) {
							searchVals = new String[colHeaders.length];
						}
						
						String colName = query.substring(query.indexOf("WHER") + query.substring(query.indexOf("WHERE")).indexOf("(") + 1,query.lastIndexOf(";") - 1).split("=")[0];
						
						//Process each condition (putting it in the search array)
						if(colHeaders != null) {
							for(int k=0;k < colHeaders.length;k++) {
								if(colName.equalsIgnoreCase(colHeaders[k])) {
									if(innerResults.contains(",")) {
										String[] innerResult = innerResults.split(",");
										for(int j=0;j < innerResult.length;j++) {
											if(j < innerResult.length - 1) {
												searchVals[k] = innerResult[j];
											}
										}
										//innerResults = innerResults.substring(0, innerResults.length() - 2);
									}
									else {
										searchVals[k] = innerResults;
									}
								}
							}
						}
						
						if(tableName.equalsIgnoreCase("Costs")) {
							//Get the values from the table
							String[] costKeys = (String[])costs.keySet().toArray();
							String[][] costVals = (String[][])costs.values().toArray();
							//Go through the table row by row
							for(int j=0;j < costKeys.length;j++) {
								//if it isn't the metadata row process it
								if(!costKeys[j].equalsIgnoreCase("Names")) {
									//Check if this row meets our search criteria (Note: that this assumes it does initally)
									boolean flag = true;
									for(int k=0;k < searchVals.length;k++) {
										if(searchVals[k] != null) {
											if(!costVals[j][k].equalsIgnoreCase(searchVals[k])) {
												flag = false;
											} 
										}
									}
									
									//If it meets the search criteria
									if(flag) {
										//Process each element in the row
										for(int k=0;k < costVals[j].length;k++) {
											//Add the element to the results only if it is a column we care about
											if(costs.get("Names")[k].equalsIgnoreCase(colNames[k])) {
												results += costVals[j][k];
												if(k < costVals[j].length - 1) {
													results += ",";
												}
											}
										}
										//Put each row on a new line of the results
										results += "\n";
									}
								}
							}
						}
						else if(tableName.equalsIgnoreCase("Orders")) {
							if(colNames == null) {
								colNames = orders.get("Names");
							}
							
							Object[] orderKeysObjs = orders.keySet().toArray();
							String[] orderKeys = new String[orderKeysObjs.length];
							for(int j=0;j < orderKeysObjs.length;j++) {
								orderKeys[j] = (String)orderKeysObjs[j];
							}
							//Get the rest of the values form the table (Still stupd that I can only get Object[])
							Object[] orderValsObjs = orders.values().toArray();
							String[][] orderVals = new String[orderValsObjs.length][];
							for(int j=0;j < orderValsObjs.length;j++) {
								orderVals[j] = (String[])orderValsObjs[j];
								System.out.print("\n");
							}
							
							results = "";
							for(int j=0;j < orderKeys.length;j++) {
								if(!orderKeys[j].equalsIgnoreCase("Names")) {
									boolean flag = true;
									for(int k=0;k < searchVals.length;k++) {
										if(searchVals[k] != null) {
											if(!orderVals[j][k].equalsIgnoreCase(searchVals[k])) {
												flag = false;
											} 
										}
										
									}
									
									if(flag) {
										for(int k=0;k < orderVals[j].length;k++) {
											for(int l=0;l < colNames.length;l++) {
												if(orders.get("Names")[k].equalsIgnoreCase(colNames[l])) {
													results += orderVals[j][k];
													if(k < orderVals[j].length - 1) {
														results += ",";
													}
												}
											}
										}
										results += "\n";
									}
								}
							}
						}
						else {
							//Error: Refrences unknown table
						}
					}
					else {
						//Get the column names for the appropriate table
						String[] colHeaders = null;
						if(tableName.equalsIgnoreCase("Costs")) {
							colHeaders = costs.get("Names");
						}
						else if(tableName.equalsIgnoreCase("Orders")) {
							colHeaders = orders.get("Names");
						}
						else {
							//Error: Refrences unknown table
						}
						
						String[] searchVals = null;
						//Set up an array that holds the values we want in the right columns and nulls in the others
						if(colHeaders != null) {
							searchVals = new String[colHeaders.length];
						}
						
						//Get the conditions from the query string
						int conditionsStartPos = query.lastIndexOf("(");
						int conditionsEndPos = query.lastIndexOf(")");
						String conditions = query.substring(conditionsStartPos + 1, conditionsEndPos);
						String[] conds = conditions.split(",");
						
						//Process each condition (putting it in the search array)
						for(int j=0;j < conds.length;j++) {
							String[] tokens = conds[j].split("=");
							
							if(colHeaders != null) {
								for(int k=0;k < colHeaders.length;k++) {
									if(stripSpaces(tokens[0]).equalsIgnoreCase(colHeaders[k])) {
										searchVals[k] = stripSpaces(tokens[1]);
									}
								}
							}
						}
						
						if(tableName.equalsIgnoreCase("Costs")) {
							//Get the values from the table
							
							//Get the "Key" from the table (stupid Hashtable will only return Object[])
							Object[] costKeysObjs = costs.keySet().toArray();
							String[] costKeys = new String[costKeysObjs.length];
							for(int j=0;j < costKeysObjs.length;j++) {
								costKeys[j] = (String)costKeysObjs[j];
							}
							//Get the rest of the values form the table (Still stupd that I can only get Object[])
							Object[] costValsObjs = costs.values().toArray();
							String[][] costVals = new String[costValsObjs.length][];
							for(int j=0;j < costValsObjs.length;j++) {
								costVals[j] = (String[])costValsObjs[j];
							}
							
							results = "";
							//Go through the table row by row
							for(int j=0;j < costKeys.length;j++) {
								//if it isn't the metadata row process it
								if(!costKeys[j].equalsIgnoreCase("Names")) {
									//Check if this row meets our search criteria (Note: that this assumes it does initally)
									boolean flag = true;
									for(int k=0;k < searchVals.length;k++) {
										if(searchVals[k] != null) {
											if(!costVals[j][k].equalsIgnoreCase(searchVals[k])) {
												flag = false;
											} 
										}
									}
									
									//If it meets the search criteria
									if(flag) {
										//Process each element in the row
										for(int k=0;k < costVals[j].length;k++) {
											if(colNames != null) {
												//Add the element to the results only if it is a column we care about
												for(int l=0;l < colNames.length;l++) {
													if(costs.get("Names")[k].equalsIgnoreCase(colNames[l])) {
														results += costVals[j][k];
														if(k < costVals[j].length - 1) {
															results += ",";
														}
													}
												}
											}
											else {
												results += costVals[j][k];
												if(k < costVals[j].length - 1) {
													results += ",";
												}
											}
										}
										//Put each row on a new line of the results
										results += "\n";
									}
								}
							}
						}
						else if(tableName.equalsIgnoreCase("Orders")) {
							if(colNames == null) {
								colNames = orders.get("Names");
							}
							
							Object[] orderKeysObjs = orders.keySet().toArray();
							String[] orderKeys = new String[orderKeysObjs.length];
							for(int j=0;j < orderKeysObjs.length;j++) {
								orderKeys[j] = (String)orderKeysObjs[j];
							}
							//Get the rest of the values form the table (Still stupd that I can only get Object[])
							Object[] orderValsObjs = orders.values().toArray();
							String[][] orderVals = new String[orderValsObjs.length][];
							for(int j=0;j < orderValsObjs.length;j++) {
								orderVals[j] = (String[])orderValsObjs[j];
							}
							
							results = "";
							for(int j=0;j < orderKeys.length;j++) {
								if(!orderKeys[j].equalsIgnoreCase("Names")) {
									boolean flag = true;
									for(int k=0;k < searchVals.length;k++) {
										if(searchVals[k] != null) {
											if(!orderVals[j][k].equalsIgnoreCase(searchVals[k])) {
												flag = false;
											} 
										}
									}
									
									if(flag) {
										for(int k=0;k < orderVals[j].length;k++) {
											if(orders.get("Names")[k].equalsIgnoreCase(colNames[k])) {
												results += orderVals[j][k];
												if(k < orderVals[j].length - 1) {
													results += ",";
												}
											}
										}
										results += "\n";
									}
								}
							}
						}
						else {
							//Error: Refrences unknown table
						}
					}
				}
				else {
					results = "";
					if(tableName.equalsIgnoreCase("Costs")) {
						if(colNames == null) {
							colNames = costs.get("Names");
						}
						String[] costKeys = (String[])costs.keySet().toArray();
						String[][] costVals = (String[][])costs.values().toArray();
						for(int j=0;j < costKeys.length;j++) {
							if(!costKeys[j].equalsIgnoreCase("Names")) {
								for(int k=0;k < costVals[j].length;k++) {
									if(costs.get("Names")[k].equalsIgnoreCase(colNames[k])) {
										results += costVals[j][k];
										if(k < costVals[j].length - 1) {
											results += ",";
										}
									}
								}
								results += "\n";
							}
						}
					}
					else if(tableName.equalsIgnoreCase("Orders")) {
						if(colNames == null) {
							colNames = orders.get("Names");
						}
						
						Object[] orderKeysObjs = orders.keySet().toArray();
						String[] orderKeys = new String[orderKeysObjs.length];
						for(int j=0;j < orderKeysObjs.length;j++) {
							orderKeys[j] = (String)orderKeysObjs[j];
						}
						
						//Get the rest of the values form the table (Still stupd that I can only get Object[])
						Object[] orderValsObjs = orders.values().toArray();
						String[][] orderVals = new String[orderValsObjs.length][];
						for(int j=0;j < orderValsObjs.length;j++) {
							orderVals[j] = (String[])orderValsObjs[j];
						}
						
						results = "";
						for(int j=0;j < orderKeys.length;j++) {
							if(!orderKeys[j].equalsIgnoreCase("Names")) {
								for(int k=0;k < orderVals[j].length;k++) {
									if(orders.get("Names")[k].equalsIgnoreCase(colNames[k])) {
										results += orderVals[j][k];
										if(k < orderVals[j].length - 1) {
											results += ",";
										}
									}
								}
								results += "\n";
							}
						}
					}
					else {
						//Error: Refrences unknown table
					}
				}
			}
			else {
				//Error: Invalid SQL SELECT statement
			}
			
			return results;
		}
		
		private String updateQuery(String query) {
			String results = null;
			
			boolean proper = false;
			try {
				proper = query.matches("(UPDATE\\s+\\w+\\s+SET\\s+\\((\\s*\\w+\\s*=\\s*.+\\s*,?)+\\)\\s+WHERE\\s+\\((\\s*\\w+\\s*=\\s*.+\\s*,?)+\\);)?+");
			}
			catch(PatternSyntaxException e) {
				//Error: I'm an idiot apparently and the regular expression failed
			}
			
			if(proper) {
				//Get the tablename from the query string
				int tableNameStartPos = query.indexOf("UPDATE") + 6;
				int tableNameEndPos = -1;
				int temp = tableNameStartPos;
				boolean found = false;
				for(int j=0;j < query.length() - temp;j++) {
					if(!found) {
						if(query.charAt(temp + j) == ' ') {
							tableNameStartPos++;
						}
						else {
							found = true;
						}
					}
					else {
						if(query.charAt(temp + j) == ' ' && tableNameEndPos == -1) {
							tableNameEndPos = temp + j;
						}
					}
				}
				String tableName = query.substring(tableNameStartPos, tableNameEndPos);
				
				//Get the column meta data (Names) from the hashtables
				String[] colHeaders = null;
				if(tableName.equalsIgnoreCase("Costs")) {
					colHeaders = costs.get("Names");
				}
				else if(tableName.equalsIgnoreCase("Orders")) {
					colHeaders = orders.get("Names");
				}
				else {
					//Error: Querying non-existant table
				}
				
				//Get the SET part of the UPDATE query
				int setValuesStartPos = query.indexOf("(");
				int setValuesEndPos = query.indexOf(")");
				String setValues = query.substring(setValuesStartPos + 1, setValuesEndPos);
				//Now divide it up into anticedin and consequence so that it is easier to work with
				String[] setValue = setValues.split(",");
				String[][] setValuePair = new String[setValue.length][2];
				//This is to keep track of the position of the column we are setting
				int[] setValueCols = new int[setValue.length];
				//Check the validity of the column names and if valid get the position (number in the array) of it
				for(int j=0;j < setValue.length;j++) {
					//Split the string into anticeden and consequence by the deliminator of the = sign
					setValuePair[j] = stripSpaces(setValue[j]).split("=");
					
					setValueCols[j] = -1;
					for(int k=0;k < colHeaders.length;k++) {
						if(setValuePair[j][0].equalsIgnoreCase(colHeaders[k])) {
							setValueCols[j] = k;
						}
					}
					
					//If the position is still -1 then the column name was invalid
					if(setValueCols[j] == -1) {
						//Error: An invalid column name was given to set
					}
				}
				
				//Get the WHERE part of the UPDATE query
				int condValuesStartPos = query.indexOf("WHERE") + query.substring(query.indexOf("WHERE")).indexOf("(");
				int condValuesEndPos = query.indexOf("WHERE") + query.substring(query.indexOf("WHERE")).indexOf(")");
				String condValues = query.substring(condValuesStartPos + 1, condValuesEndPos);
				String[] condValue = condValues.split(",");
				String[][] condValuePair = new String[condValue.length][2];
				int[] condValueCols = new int[condValue.length];
				for(int j=0;j < condValue.length;j++) {
					condValuePair[j] = stripSpaces(condValue[j]).split("=");
					
					condValueCols[j] = -1;
					for(int k=0;k < colHeaders.length;k++) {
						if(stripSpaces(condValuePair[j][0]).equalsIgnoreCase(colHeaders[k])) {
							condValueCols[j] = k;
						}
					}
					
					if(condValueCols[j] == -1) {
						//Error: An invalid column name was given to where clause
					}
				}
				
				if(tableName.equalsIgnoreCase("Costs")) {
					Object[] costKeysObjs = costs.keySet().toArray();
					String[] costKeys = new String[costKeysObjs.length];
					for(int j=0;j < costKeysObjs.length;j++) {
						costKeys[j] = (String)costKeysObjs[j];
					}
					
					Object[] costValsObjs = costs.values().toArray();
					String[][] costVals = new String[costValsObjs.length][];
					for(int j=0;j < costValsObjs.length;j++) {
						costVals[j] = (String[])costValsObjs[j];
					}
					
					for(int j=0;j < costVals.length;j++) {
						if(!costKeys[j].equalsIgnoreCase("Names")) {
							boolean matches = true;
							for(int k=0;k < condValueCols.length;k++) {
								if(!costVals[j][condValueCols[k]].equalsIgnoreCase(stripSpaces(condValuePair[k][1]))) {
									matches = false;
								}
							}
							
							if(matches) {
								for(int k=0;k < setValueCols.length;k++) {
									System.out.println("Setting " + colHeaders[setValueCols[k]] + " to " + setValuePair[k][1]);
									costs.get(costKeys[j])[setValueCols[k]] = setValuePair[k][1];
								}
								results = "";
								for(int k=0;k < costVals[j].length;k++) {
									results += costs.get(costKeys[j])[k] + " ";
								}
								results += "\n";
							}
						}
					}
				}
				else if(tableName.equalsIgnoreCase("Orders")) {
					Object[] orderKeysObjs = orders.keySet().toArray();
					String[] orderKeys = new String[orderKeysObjs.length];
					for(int j=0;j < orderKeysObjs.length;j++) {
						orderKeys[j] = (String)orderKeysObjs[j];
					}
					
					Object[] orderValsObjs = orders.values().toArray();
					String[][] orderVals = new String[orderValsObjs.length][];
					for(int j=0;j < orderValsObjs.length;j++) {
						orderVals[j] = (String[])orderValsObjs[j];
					}
					
					for(int j=0;j < orderVals.length;j++) {
						if(!orderKeys[j].equalsIgnoreCase("Names")) {
							boolean matches = true;
							for(int k=0;k < condValueCols.length;k++) {
								if(!orderVals[j][condValueCols[k]].equalsIgnoreCase(stripSpaces(condValuePair[k][1]))) {
									matches = false;
								}
							}
							
							if(matches) {
								for(int k=0;k < setValueCols.length;k++) {
									System.out.println("Setting " + colHeaders[setValueCols[k]] + " to " + setValuePair[k][1]);
									orders.get(orderKeys[j])[setValueCols[k]] = setValuePair[k][1];
								}
								results = "";
								for(int k=0;k < orderVals[j].length;k++) {
									results += orders.get(orderKeys[j])[k] + " ";
								}
								results += "\n";
							}
						}
					}
				}
			}
			else {
				//Error: Invalid SQL UPDATE statement
			}
			
			return results;
		}
		
		/*
		 * Name: stripSpaces
		 * Description: Remove prepending or postpending spaces of the string (useful for saving space of values, making sure values match, etc...)
		 * Parameter(s): str (String) - The string to be processed
		 * Return: String - The string having all the prepending and postpending spaces removed.
		 */
		private String stripSpaces(String str) {
			//Setup the positions
			int start = -1;
			int end = -1;
			//Go through the string character by character
			for(int j=0;j < str.length();j++) {
				if(!(str.charAt(j) == ' ') && start == -1) {
					start = j;
				} else if(str.charAt(j) == ' ' && start != -1 && end == -1) {
					end = j;
				}
			}
			//for the cases where there isn't any spaces at places I'd be removing them
			if(start == -1) {
				start = 0;
			}
			if(end == -1) {
				end = str.length();
			}
			//Return the newly processed string
			return str.substring(start, end);
		}
		
		/*
		 * 	INSERT INTO Regular Expression 	// 									Comments
		 * --------------------------------------------------------------------------------------------------------------------
		 * 	( 								// Start of Group 1 (Master Group)
		 * 		INSERT 						// Must start with the exact sequence of characters: INSERT
		 * 		\s 							// Followed by a single space character 
		 * 		INTO 						// Then the exact sequence of characters: INTO
		 * 		\s+ 						// One or more space characters will follow that
		 * 		\w+ 						// The table name is then expected (made up of one or more word characters)
		 * 		\s* 						// Zero or more space characters again
		 * 		( 							// Start of Group 2 (Field Set Group)
		 * 			\( 						// The group of fields in a INSERT INTO statment are surounded by parenthasis in standard SQL
		 * 				( 					// Start of Group 3 (Single Field Group)
		 * 					\w+ 			// The name of the field (made up of one or more word characters)
		 * 					,? 				// Only one comma MAY appear to seperate field names
		 * 					\s* 			// And zero or more spaces at the end (between)
		 * 				)+ 					// End of Group 3 with it being one or more of this group
		 * 			\) 						// The end parenthasis for the fields in the actual SQL statment
		 * 		)? 							// End of Group 2 with only one MAY appear
		 * 		( 							// Start of Group 4 (between keywords)
		 * 			$ 						// New line character
		 * 			| 						// OR (Group 4: between keywords)
		 * 			\s 						// Space character
		 * 		)+ 							// The end of Group 4 with it being one or more
		 * 		VALUES 						// The exact sequence of characters: VALUES
		 * 		\s+ 						// One or more space characters
		 * 		\( 							// The group of values in a INSERT INTO statement are surounded by parenthasis in standard SQL
		 * 			( 						// Start of Group 5 (Single Value Group)
		 * 				.+ 					// A value to be inserted (made up of one or more of any character)
		 * 				,? 					// Only one comma MAY appear to seperate values
		 * 				\s* 				// And zero or more spaces at the end (between)
		 * 			)+ 						// End of Group 5 with it being one or more of this group
		 * 		\) 							// The end parenthasis for the values in the actual SQL statement
		 * 		; 							// A semicolon as per SQL syntax
		 * 	)?+ 							// End of Group 1 (Master Group) having greedy one or none
		 * 
		 * Actual Expression
		 * ------------------------------------------------------------
		 * (INSERT\sINTO\s+\w+\s*(\((\w+,?\s*)+\))?($|\s)+VALUES\s+\((.+,?\s*)+\);)?+
		 * 
		 * Success Tests
		 * ------------------------------------------------------------
		 * INSERT INTO Orders VALUES (1000, 07/02/2016);
		 * INSERT INTO Orders (OrderID, Date) VALUES (1000, 07/02/2016);
		 * 
		 * Fail Tests
		 * ------------------------------------------------------------
		 * INSERT INTO;
		 * INSERT INTO Orders;
		 * INSERT INTO Orders VALUES;
		 * INSERT INTO Orders (1000, 07/02/2016);
		 * INSERT INTO VALUES (1000, 07/02/2016);
		 * INSERTINTO Orders VALUES (1000, 07/02/2016);
		 * INSERT INTO (OrderID, Date) VALUES (1000, 07/02/2016);
		 * INSERT INTO Orders (OrderID, Date) VALUES (1000, 07/02/2016)
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 	SELECT Regular Expression		// 									Comments
		 * --------------------------------------------------------------------------------------------------------------------
		 * 	( 								// Start of Group 1 (Master Group)
		 * 		SELECT 						// Must start with exact sequence of characters: SELECT
		 * 		\s+ 						// Followed by one or more spaces
		 * 		( 							// Start of Group 2 (Field Set Group)
		 * 			\( 						// The group of fields in a select statement is sorounded by parenthasis in standard SQL
		 * 				( 					// Start of Group 3 (Single Field Group)
		 * 					\w+ 			// The name of the field (made up of one or more word characters)
		 * 					,? 				// Zero or one comma is expected
		 * 					\s* 			// And zero or more spaces at the end (between)
		 * 				)+ 					// End of Group 3 with it being one or more
		 * 			\) 						// The end parenthasis for the fields in the actual SQL statement
		 * 			| 						// OR (Group 2: Field Set Group)
		 * 			\* 						// A astricks (in standard SQL this indicates all fields)
		 * 		) 							// End of Group 2
		 * 		( 							// Start of Group 4 (between keywords 1)
		 * 			$ 						// New line character
		 * 			| 						// Or (Group 4: between keywords 1)
		 * 			\s 						// space character
		 * 		)+ 							// End of Group 4 with it being one or more
		 * 		FROM 						// The exact sequence of characters: FROM
		 * 		\s+ 						// One or more white spaces
		 * 		\w+ 						// The name of the table (made up of one or more word characters)
		 * 		( 							// Start of Group 5 (between keywords 2)
		 * 			$ 						// New line character
		 * 			| 						// Or (Group 5: between keywords 2)
		 * 			\s 						// Space character
		 * 		)* 							// End of Group 5 with it being zero or more
		 * 		( 							// Start of Group 6 (where claus)
		 * 			WHERE 					// The exact sequence of characters: WHERE
		 * 			\s+ 					// One or more space characters
		 * 			( 						// Start of Group 7 (Condition Set Group)
		 * 				\( 					// The group of conditions in a select statement is sorounded by parenthesis in standard SQL
		 * 					( 				// Start of Group 8 (Single Condition Group)
		 * 						\w+ 		// The anticeden of the condition (made up of one or more word characers)
		 * 						\s* 		// And zero or more spaces between the anticeden and the equal sign
		 * 						= 			// The exact character of the equal sign
		 * 						\s* 		// And zero or more spaces between the equal sign and the consequence 
		 * 						.+ 			// The consequence of the condition (made up of one or more characters of any type)
		 * 						,? 			// Zero or more comma(s) expected
		 * 						\s* 		// And zero or more spaces at the end (between)
		 * 					)+ 				// End of Group 8 with it being one or more
		 * 				\) 					// The end parenthasis for the conditions in the actual SQL statement
		 * 				| 					// Or (Group 7: Condition Set Group)
		 * 				\w+ 				// The anticeden of the condition (made up of one or more word characters)
		 * 				\s* 				// Followed by zero or more spaces between the anticeden and the equal sign
		 * 				= 					// The exact character of the equal sign
		 * 				\s* 				// Zero or more spaces between the equal sign and the inner SELECT statement
		 * 				SELECT 				// Inner SELECT query must start with the exact sequence of characters: SELECT
		 * 				\s+ 				// After that one or more space characters
		 * 				( 					// Start of Group 9 (Inner SQL Field Set Group)
		 * 					\( 				// Inner SQL mush have parenthesis around the set of fields to be selected
		 * 						( 			// Start of Group 10 (Inner SQL Single Field Group)
		 * 							\w+ 	// The name of the field (made up of one or more word characters)
		 * 							,? 		// Then zero or more comma(s) to seperate the values
		 * 							\s* 	// Finally zero or more spaces at the end (between)
		 * 						)+ 			// End of Group 10 with it being one or more
		 * 					\) 				// The end parenthasis for the fields in the inner SQL
		 * 					| 				// Or (Group 9: Inner SQL Field Set Group)
		 * 					\* 				// A astricks (in standard SQL this indicates all fields)
		 * 				) 					// End of Group 9
		 * 				( 					// Start of Group 11 (Inner SQL between keywords 1)
		 * 					$ 				// New line character
		 * 					| 				// Or (Group 11: Inner SQL between keywords 1)
		 * 					\s 				// Space character
		 * 				)+ 					// End of Group 11 with it being one or more
		 * 				FROM 				// The exact sequence of character: FROM
		 * 				\s+ 				// One or more space characters
		 * 				\w+ 				// The table name (made up of one or more word characters)
		 * 				( 					// Start of Group 12 (Inner SQL between keywords 2)
		 * 					$ 				// New line character
		 * 					| 				// Or (Group 12: Inner SQL between keywords 2)
		 * 					\s 				// Space character
		 * 				)+ 					// End of Group 12 with it being one or more
		 * 				.* 					// One or more of any character
		 * 			) 						// ENd of Group 7 (Condition Set Group)
		 * 		)? 							// End of Group 6 (Where Claus) having one or none
		 * 		; 							// A semicolon character to end the statement (As is proper SQL)
		 * 	)?+ 							// End of Group 1 (Master Group) having greedy one or none
		 * 
		 * Actual Expression
		 * ------------------------------------------------------
		 * (SELECT\s+(\((\w+,?\s*)+\)|\*)($|\s)+FROM\s+\w+($|\s)*(WHERE\s+(\((\w+\s*=\s*.+,?\s*)+\)|\w+\s*=\s*SELECT\s+(\((\w+,?\s*)+\)|\*)($|\s)+FROM\s+\w+($|\s)*.*))?;)?+
		 * 
		 * Success Tests
		 * ------------------------------------------------------
		 * SELECT * FROM Orders;
		 * SELECT * FROM Orders WHERE (OrderID=1000);
		 * SELECT * FROM Orders WHERE (OrderID = 1000);
		 * SELECT (Date) FROM Orders WHERE (OrderID = 1000);
		 * SELECT (OrderID,Date) FROM Orders WHERE (OrderID = 1000);
		 * SELECT (OrderID, Date) FROM Orders WHERE (OrderID = 1000);
		 * SELECT (OrderID, Date) FROM Orders WHERE (OrderID = 1000, Date = 07/02/2016);
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID=SELECT * FROM Costs;
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT * FROM Costs;
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT * FROM Costs WHERE (CostID=1000);
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT * FROM Costs WHERE (CostID = 1000);
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT (OrderID) FROM Costs WHERE (CostID = 1000);
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT (OrderID, Price) FROM Costs WHERE (CostID = 1000);
		 * 
		 * Fail Tests
		 * --------------------------------------------------------
		 * SELECT
		 * SELECT;
		 * SELECT FROM;
		 * SELECT Date FROM;
		 * SELECT FROM Orders;
		 * SELECT * FROM  ;
		 * SELECT Date FROM Orders;
		 * SELECT * FROM Orders WHERE;
		 * SELECT * FROM Orders WHERE (OrderID);
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT;
		 * SELECT (OrderID, Date) FROM Orders WHERE OrderID = SELECT (OrderID) FROM Costs WHERE (CostID = 1000)
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * UPDATE QueryParameter
		 * ---------------------
		 * (
		 * 		UPDATE
		 * 		\s+
		 * 		\w+
		 * 		\s+
		 * 		SET
		 * 		\s+
		 * 		\(
		 * 			(
		 * 				\s*
		 * 				\w+
		 * 				\s*
		 * 				=
		 * 				\s*
		 * 				.+
		 * 				\s*
		 * 				,?
		 * 			)+
		 * 		\)
		 * 		\s+
		 * 		WHERE
		 * 		\s+
		 * 		\(
		 * 			(
		 * 				\s*
		 * 				\w+
		 * 				\s*
		 * 				=
		 * 				\s*
		 * 				.+
		 * 				\s*
		 * 				,?
		 * 			)+
		 * 		\)
		 * 		;
		 * )?+
		 * 
		 * (UPDATE\s+\w+\s+SET\s+\((\s*\w+\s*=\s*.+\s*,?)+\)\s+WHERE\s+\((\s*\w+\s*=\s*.+\s*,?)+\);)?+
		 */
	}