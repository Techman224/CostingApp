package com.softeng.jobcosting.jobcostingapp;


import static android.support.test.InstrumentationRegistry.getTargetContext;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.softeng.jobcosting.jobcostingapp.Database.ActualDatabase;
import com.softeng.jobcosting.jobcostingapp.Database.GlobalDatabase;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;


/**
 * Created by joseph on 2016-03-12.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class ActualDatabaseTest {

    private GlobalDatabase gdb;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(ActualDatabase.DATABASE_NAME);
        gdb = new GlobalDatabase();
    }

    @Test
    public void testDatabaseType() {
        assertTrue(gdb.getDB() instanceof ActualDatabase);
    }

}
