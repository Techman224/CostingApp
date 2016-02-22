package com.softeng.jobcosting.jobcostingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final int TYPE = 2;
        final int DESC = 3;
        final int AMT = 4;

        int orderID = Integer.parseInt(intent.getStringExtra("orderID"));
        String items = getOrderInfo(orderID);
        String[][] processedItems = parseItems(items);



    }



    public String getOrderInfo(int orderID) {
        Calculations calc = new Calculations();
        return calc.getItems(orderID);
    }

    public String[][] parseItems(String toParse) {

        String[] parsedItems = toParse.split("\\s+");
        String[][] parsedAll = new String[parsedItems.length][];

        for(int row = 0; row < parsedAll.length; row++) {
            parsedAll[row] = parsedAll[row][0].split(",");
        }

        return parsedAll;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
