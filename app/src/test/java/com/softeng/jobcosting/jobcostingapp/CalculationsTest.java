package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;

import java.text.SimpleDateFormat;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculationsTest
{

    @Test
    public void newOrder_isDateCorrect()
    {
        Calculations newCalc = new Calculations();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/mm/dd");
        Date newDate = new Date();

        assertEquals(dt.format(newDate), newCalc.newOrder());
    }

    @Test
    public void newItem_isInserted()
    {
        Calculations newCalc = new Calculations();
        assertEquals(0, (newCalc.newItem("1000", "Shopify", "RV-145", "Board", "100.00")).compareTo("1000, Shopify, RV-145, Board, 100.00"));
    }
}