package com.softeng.jobcosting.jobcostingapp;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

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
        if(db == null)
          db = new Database();

        return db;
    }
}