package com.softeng.jobcosting.jobcostingapp.Database;

import android.app.Application;

import com.softeng.jobcosting.jobcostingapp.Database.Database;
import com.softeng.jobcosting.jobcostingapp.Database.stub.StubDatabase;

/**
 * Created by Courtney on 2016-02-21.
 * Modified by Alan on 2016-03-02.
 */
public class GlobalDatabase extends Application {
    private static Database db;

    public void onCreate() {
        ///super.onCreate();
        db = new StubDatabase();
    }

    public static Database getDB() {
        return db;
    }
	
	public static void main(String args[]) {
		System.out.println("Is it getting to this point?");
		GlobalDatabase.getDB();
	}
}
