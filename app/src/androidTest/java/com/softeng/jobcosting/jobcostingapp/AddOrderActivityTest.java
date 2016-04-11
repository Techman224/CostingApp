package com.softeng.jobcosting.jobcostingapp;

/**
 * Created by Courtney on 2016-04-10.
 */

import com.robotium.solo.Solo;
import com.softeng.jobcosting.jobcostingapp.UserInterface.AddOrderActivity;
import com.softeng.jobcosting.jobcostingapp.UserInterface.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class AddOrderActivityTest extends ActivityInstrumentationTestCase2<AddOrderActivity> {
    private static final String STORE = "Shopify";
    private static final String PRICE = "2.45";

    private Solo solo;

    public AddOrderActivityTest() {
        super(AddOrderActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testDone() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        //click on menu item AddOrder
        solo.clickOnMenuItem("Add Order");
        //Assert that AddOrderActivity activity is opened
        solo.assertCurrentActivity("Expected AddOrder Activity", AddOrderActivity.class);
        //In text field 0, enter STORE
        solo.enterText(2, STORE);
        //In text field 0, enter PRICE
        solo.enterText(4, PRICE);
        //Click on Done button
        solo.clickOnView(solo.getView(R.id.addOrderDoneButton));
        //Assert that MainActivity activity is opened
        solo.assertCurrentActivity("Expected Main Activity", MainActivity.class);
    }
}