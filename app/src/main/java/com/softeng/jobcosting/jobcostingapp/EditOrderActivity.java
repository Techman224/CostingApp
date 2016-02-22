package com.softeng.jobcosting.jobcostingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class EditOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calculations calc = new Calculations();
        String newOrder = calc.newOrder();

        final int ORDER_ID = 0;
        final int DATE = 1;

        String[] values = newOrder.split(",");

        int orderID = Integer.parseInt(values[ORDER_ID]);
        TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        String orderNumView = orderNum.getText().toString();
        orderNumView += Integer.toString(orderID);
        orderNum.setText(orderNumView);

        TextView date = (TextView)findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += values[DATE];
        date.setText(dateView);
    }
}
