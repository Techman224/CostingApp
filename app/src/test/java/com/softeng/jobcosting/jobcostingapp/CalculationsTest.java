package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;

import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculationsTest
{
    private Calculations newCalc = new Calculations();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy/M/d");
    Date newDate = new Date();

    @Test
    public void newOrder_isDateCorrect()
    {
        assertEquals("1," + dt.format(newDate) + "\n", newCalc.newOrder());
        assertEquals("2," + dt.format(newDate) + "\n", newCalc.newOrder());
    }

    @Test
    public void newItem_isInserted()
    {
        assertEquals("1,1,Shopify,RV-145,Board,100.00\n", newCalc.newItem(1, "Shopify", "RV-145", "Board", 100.00f));
        assertEquals("2,1,EBGames,RV-145,Board,50.00\n", newCalc.newItem(1, "EBGames", "RV-145", "Board", 50f));
        assertEquals("3,1,EBGames,RV-145,Board,10.58\n", newCalc.newItem(1, "EBGames", "RV-145", "Board", 10.584989875f));
        assertEquals("4,2,EBGames,RV-145,Board,50.00\n", newCalc.newItem(2, "EBGames", "RV-145", "Board", 50f));
    }

    @Test
    public void editItem_isEdited()
    {
        assertEquals("2 1 Safeway RV-145 Board 50.00 \n", newCalc.editItem("Store", "Safeway", 2));
        assertEquals("3 1 EBGames RV-200 Board 10.58 \n", newCalc.editItem("Description", "RV-200", 3));
        assertEquals("3 1 EBGames RV-200 Promo 10.58 \n", newCalc.editItem("Type", "Promo", 3));
    }

    @Test
    public void getItems_isAllItems()
    {
        assertEquals("3,1,EBGames,RV-200,Promo,10.58\n" +
                "2,1,Safeway,RV-145,Board,50.00\n" +
                "1,1,Shopify,RV-145,Board,100.00\n", newCalc.getItems(1));
        assertEquals("4,2,EBGames,RV-145,Board,50.00\n", newCalc.getItems(2));
    }

    @Test
    public void getOrderTotal_isCorrect()
    {
        // for this test, please change expected value if previous methods values are changed.

        assertEquals(160.58, newCalc.getOrderTotal(1), 0.1);
        assertEquals(50.00, newCalc.getOrderTotal(2), 0.1);
    }

    @Test
    public void getOrderID_Test()
    {
        System.out.println(newCalc.getOrderIDs());
    }
}