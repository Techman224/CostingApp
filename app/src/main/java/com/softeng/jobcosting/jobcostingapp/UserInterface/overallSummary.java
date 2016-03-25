package com.softeng.jobcosting.jobcostingapp.UserInterface;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

public class overallSummary extends AppCompatActivity {

    private Calculations calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calc = new Calculations();
    }

    public void setLayout() {
        int[]orderIDs = calc.getOrderIDs();

        LinearLayout contentLay = (LinearLayout)findViewById(R.id.overSummContent);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tblLay = null;
        TextView column = null;
        String margin = "", profit = "";

        for(int currOrder = 0; currOrder < orderIDs.length; currOrder++)    {
            tblLay = inflater.inflate((R.layout.colmd_items), null);

            column = (TextView)tblLay.findViewById(R.id.idTextView);
            column.setText(orderIDs[currOrder]);

            column = (TextView)tblLay.findViewById(R.id.marginTextView);
            margin = Float.toString(calc.getMargin(orderIDs[currOrder]));
            column.setText(margin);

            column = (TextView)tblLay.findViewById(R.id.profitTextView);
            profit = Float.toString(calc.getProfit(orderIDs[currOrder]));
            column.setText(profit);

            contentLay.addView(tblLay);

        }
    }

}
