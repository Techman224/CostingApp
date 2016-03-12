package com.softeng.jobcosting.jobcostingapp.Database;

import android.app.Application;
import android.provider.Settings;

import com.softeng.jobcosting.jobcostingapp.Database.stub.SimpleStub;

import java.io.Serializable;

/**
 * Created by Courtney on 2016-02-21.
 * Modified by Alan on 2016-03-02.
 * Modified by Joseph on 2016-03-04
 */
public class GlobalDatabase extends Application {
    private static Database db;

    /**
     * Gets executed only when Android app launches
     */
    public void onCreate() {
        super.onCreate();
        db = new ActualDatabase();
    }

    /**
     * When testing, db will be null when constructor is called
     * Used for getting the reference to database for android app and tests
     */
    public GlobalDatabase () {
        if (db == null) {
            db = new SimpleStub();
        }
    }

    public static Database getDB() {
        if(db == null)
        {
            db = new SimpleStub();
        }

        return db;
    }
	

}
