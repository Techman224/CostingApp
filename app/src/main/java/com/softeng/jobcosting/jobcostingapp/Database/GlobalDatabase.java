package com.softeng.jobcosting.jobcostingapp.Database;

import android.app.Application;

import com.softeng.jobcosting.jobcostingapp.Database.Database;

/**
 * Created by Courtney on 2016-02-21.
 */
public class GlobalDatabase extends Application {
    private static Database db;

    public void onCreate() {
        super.onCreate();
        db = new Database();
    }

    public static Database getDB() {
        return db;
    }
}
