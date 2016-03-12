package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.UserInterface.EditOrderActivity;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class EditOrderActivityTest {

    @Test
    public void newOrder_isDateCorrect() {
        EditOrderActivity activity = new EditOrderActivity();



        assertEquals(dt.format(newDate), newCalc.newOrder());
    }

    @Test
    public void newItem_isInserted()
    {
        Calculations newCalc = new Calculations();
        assertEquals(1, (newCalc.newItem("1000", "Shopify", "RV-145", "Board", "100.00")).compareTo("1,1000,Shopify,RV-145,Board,100.00"));
        assertEquals(1, (newCalc.newItem("1001", "EBGames", "RV-145", "Board", "100.00")).compareTo("2,1001,EBGames,RV-145,Board,100.00"));
    }

    @Test
    public void editItem_isEdited()
    {
        Calculations newCalc = new Calculations();
        assertEquals(2, (newCalc.newItem("1000", "Shopify", "RV-145", "Board", "100.00")).compareTo("1,1000,Shopify,RV-145,Board,100.00"));
        assertEquals(2, (newCalc.editItem("Store", "EBGames", 1)).compareTo("1 1000 EBGames RV-145 Board 100.00"));
    }
}