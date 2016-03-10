package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculationsTest
{
    @Test
    public void newOrder_isDateCorrect()
    {
        Calculations newCalc = new Calculations();
        System.out.println((new Date()).getDate() + "/" + (new Date()).getMonth() + "/" + (new Date()).getYear());
        Date newDate = new Date();
        assertEquals(0, newCalc.newOrder().compareTo(newDate.getDate() + "/" + newDate.getMonth() + "/" + newDate.getYear()));
    }
}