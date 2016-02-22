package com.softeng.jobcosting.jobcostingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class AddOrderActivity extends AppCompatActivity {
    private ArrayList<View> items;
    private CalculationActivity calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        items = new ArrayList<View>();

        calc = new CalculationActivity();
        String newOrder = calc.newOrder();

        final int ORDER_ID = 0;
        final int DATE = 1;

        String[] values = newOrder.split(",");

        int orderID = Integer.parseInt(values[ORDER_ID]);
        TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        String orderNumView = orderNum.getText().toString();
        orderNumView += " " + Integer.toString(orderID);
        orderNum.setText(orderNumView);

        TextView date = (TextView)findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += " " + values[DATE];
        date.setText(dateView);
    }

    public void addItem(View view) {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.input_item, null);
        mainLayout.addView(mView);
        items.add(mView);
    }

    public void done(View view) {
        for(View v : items) {
            EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
            String store = storeInput.getText().toString();

            Spinner types = (Spinner) v.findViewById(R.id.typeSpinner);
            String type = (types.getSelectedItem()).toString();

            EditText descriptionInput = (EditText) v.findViewById(R.id.descEditText);
            String description = descriptionInput.getText().toString();

            EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
            String amount = amountInput.getText().toString();

            calc.newItem(store, type, description, amount);
        }

        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }
}