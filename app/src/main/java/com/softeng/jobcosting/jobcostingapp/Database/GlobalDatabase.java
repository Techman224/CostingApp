package com.softeng.jobcosting.jobcostingapp.Database;

import android.app.Application;
import android.provider.Settings;

import com.softeng.jobcosting.jobcostingapp.Database.stub.StubDatabase;

/**
 * Created by Courtney on 2016-02-21.
 * Modified by Alan on 2016-03-02.
 * Modified by Joseph on 2016-03-04
 */
public class GlobalDatabase {
    private static Database db;

    public GlobalDatabase (boolean stub) {

        if (stub == true) {
            db = new StubDatabase();
        }
        else {
            db = new ActualDatabase();
        }
    }


    public static Database getDB() {
        return db;
    }
	

}
