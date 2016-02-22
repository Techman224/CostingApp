package com.softeng.jobcosting.jobcostingapp;

import android.app.Application;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Courtney on 2016-02-21.
 */
public class GlobalDatabase extends Application {
    private static Database db;
    private static AtomicInteger newOrderID;

    public void onCreate() {
        super.onCreate();
        db = new Database();
        newOrderID = new AtomicInteger(1);
    }

    public static Database getDB() {
        return db;
    }

    public static AtomicInteger getNewOrderID() {
        return newOrderID;
    }
}
