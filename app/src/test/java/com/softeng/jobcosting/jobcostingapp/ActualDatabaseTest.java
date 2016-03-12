package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.Database.ActualDatabase;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActualDatabaseTest {
    @Test
    public void insert_accuracy() {
        assertTrue("Insert didn't work", insert("OrderID","5"));
        assertNotNull("Query returned null", query());
    }
}