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
import android.widget.LinearLayout;
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


        setLayout(processedItems);
    }

    public void setLayout(String[][]table) {


        View tblLay = null;



        for(int row = 0; row < table.length; row++) {
            LinearLayout contentLay = (LinearLayout)findViewById(R.id.summContentLayout);
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView currCol = null;
            tblLay = inflater.inflate((R.layout.colmd_items), null);

            for (int col = 2; col < table[row].length; col++) {


                switch (col) {
                    case 2:
                        currCol = (TextView) tblLay.findViewById(R.id.storeTextView);
                        break;
                    case 3:
                        currCol = (TextView) tblLay.findViewById(R.id.descTextView);
                        break;
                    case 4:
                        currCol = (TextView) tblLay.findViewById(R.id.typeTextView);
                        break;
                    case 5:
                        currCol = (TextView) tblLay.findViewById(R.id.amtTextView);
                        break;
                }

                System.out.println(table[row][col]);
                currCol.setText(table[row][col]);
                System.out.println(currCol.getText());
            }
            contentLay.addView(tblLay);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
