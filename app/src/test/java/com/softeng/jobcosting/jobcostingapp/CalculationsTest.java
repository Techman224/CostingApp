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
        assertThat(Calculations.newOrder(), new Date());
    }
}