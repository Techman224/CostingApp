package com.softeng.jobcosting.jobcostingapp.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;


public class SummaryActivity extends AppCompatActivity {

    private Calculations calc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calc = new Calculations();

        Intent intent = getIntent();

        String strOrderID = intent.getStringExtra("orderID");
        setTitle(strOrderID);

        strOrderID = strOrderID.substring(strOrderID.length()-1);
        int orderID = Integer.parseInt(strOrderID);
        String items = getOrderInfo(orderID);

//        if(items == null)   {
            items = "1,1,Shopify,Im a cost,Board,5000.00\n2,1,Red Paddle,Im a revenue,Board,9000.52";
//        }
        String[][] processedItems = parseItems(items);

        setLayout(processedItems);
        setFixedBar(orderID);
    }

    public void setLayout(String[][]table) {

        LinearLayout contentLay = (LinearLayout)findViewById(R.id.summContentLayout);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView currCol = null;
        View tblLay = null;

        for(int row = 0; row < table.length; row++) {

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

                currCol.setText(table[row][col]);
            }
            contentLay.addView(tblLay);
        }

        Button editButton = new Button(this);
        editButton.setText(R.string.edit_order);
        int orderID = Integer.parseInt(table[0][1]);
        editButton.setOnClickListener(new editOnClickListener(orderID));

        contentLay.addView(editButton);
    }


    public void setFixedBar(int orderID)    {

//        String margin = "Profit Margin: " + Float.toString(calc.getMargin(orderID));
//        String profit = "Total Profit: " + Float.toString(calc.getProfit(orderID));

        String margin = "Profit Margin: temp";
        String profit = "Total Profit: temp";

        ((TextView)findViewById(R.id.profitLabel)).setText(profit);
        ((TextView)findViewById(R.id.marginLabel)).setText(margin);
    }

    public String getOrderInfo(int orderID) {
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


    private class editOnClickListener implements OnClickListener    {
        private int orderID;

        public editOnClickListener(int orderID) {
            this.orderID = orderID;
        }

        public void onClick(View v)  {
            Intent intent = new Intent(SummaryActivity.this, EditOrderActivity.class);
            intent.putExtra("orderID", orderID);
            startActivity(intent);
        }
    }
}
