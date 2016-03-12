package com.softeng.jobcosting.jobcostingapp.UserInterface;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

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

        String strOrderID = intent.getStringExtra("orderID");
        strOrderID = strOrderID.substring(strOrderID.length()-1);
        int orderID = Integer.parseInt(strOrderID);
        String items = getOrderInfo(orderID);

        if(items == null)   {
            items = "1,1,Shopify,Im a cost,Board,5000.00\n2,1,Red Paddle,Im a revenue,Board,9000.52";
        }
        String[][] processedItems = parseItems(items);


//        setGridLayout(processedItems);
    }

    public void addItem(View view) {
        TableLayout row = (TableLayout) findViewById(R.id.tableLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.colmd_items, null);
        row.addView(mView);
    }


    public String getOrderInfo(int orderID) {
        Calculations calc = new Calculations();
        return calc.getItems(orderID);
    }

    public String[][] parseItems(String toParse) {

        String[] parsedItems = toParse.split("\n");
        String[][] parsedAll = new String[parsedItems.length][];

        for(int row = 0; row < parsedAll.length; row++) {

            parsedAll[row] = parsedItems[row].split(",");
        }

        return parsedAll;
    }

//    public void setGridLayout(String[][]items)  {
//
//        GridLayout grLay = (GridLayout)findViewById(R.id.summary_layout);
//        grLay.removeAllViews();
//
//        int total = items.length * items[0].length;
//        int column = items[0].length;
//        int row = items.length;
//        grLay.setColumnCount(column);
//        grLay.setRowCount(row+1);
//
//        for(int i=0, c=0, r=0; i < total; i++,c++)  {
//
//            if(c == column) {
//                c=0;
//                r++;
//            }
//
//            TextView toAdd = new TextView(this);
//            toAdd.setText(items[r][c]);
//
//            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
//            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
//            param.rightMargin = 5;
//            param.topMargin = 5;
//            param.setGravity(Gravity.CENTER);
//            param.columnSpec = GridLayout.spec(c);
//            param.rowSpec = GridLayout.spec(r);
//            toAdd.setLayoutParams(param);
//            grLay.addView(toAdd);
//        }
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
