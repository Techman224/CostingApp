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
        assertEquals("1,2,EBGames,RV-145,Board,50.00\n", newCalc.newItem(2, "EBGames", "RV-145", "Board", 50f));
    }

    //@Test
    public void editItem_isEdited()
    {
        Calculations newCalc = new Calculations();
        assertEquals(2, (newCalc.newItem(1000, "Shopify", "RV-145", "Board", (float)100.00)).compareTo("1,1000,Shopify,RV-145,Board,100.00"));
        assertEquals(2, (newCalc.editItem("Store", "EBGames", 1)).compareTo("1 1000 EBGames RV-145 Board 100.00"));
    }

    //@Test
    public void getItems_isAllItems()
    {
        Calculations newCalc = new Calculations();
        //assertEquals(1, (newCalc.newItem(1000, "Shopify", "RV-145", "Board", (float) 100.00)).compareTo("1,1000,Shopify,RV-145,Board,100.00"));
        //assertEquals(1, (newCalc.newItem(1001, "EBGames", "RV-145", "Board", (float) 100.00)).compareTo("2,1001,EBGames,RV-145,Board,100.00"));

        newCalc.newOrder();
        System.out.println(newCalc.getItems(1));
    }

    //@Test
    public void getTotal_isCorrect()
    {
        Calculations newCalc = new Calculations();

        newCalc.newItem(1000, "Shopify", "RV-145", "Board", 100.00f);
        newCalc.newItem(1000, "EBGames", "RV-145", "Board", 100.00f);

        System.out.println(newCalc.getOrderTotal(1000));
        System.out.println(newCalc.getDate(1000));
    }
}