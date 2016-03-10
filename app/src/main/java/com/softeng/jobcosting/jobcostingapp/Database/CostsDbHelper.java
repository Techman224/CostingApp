package com.softeng.jobcosting.jobcostingapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alan on 10/03/2016.
 */
public class CostsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Costs.db";

    public CostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CostsOrdersContract.SQL_CREATE_ORDERS);
        db.execSQL(CostsOrdersContract.SQL_CREATE_COSTS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
