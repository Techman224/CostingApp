package com.softeng.jobcosting.jobcostingapp;

import com.softeng.jobcosting.jobcostingapp.Order;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculationsTest
{
    @Test

}

class OrderTest
{
    @Test
    public void Order_isCorrect()
    {
        Order sampleorder = new Order(01, 01, 1970, 1000, "Shoppify");
        assertEquals(Order, sampleorder.getClass());
    }
}
