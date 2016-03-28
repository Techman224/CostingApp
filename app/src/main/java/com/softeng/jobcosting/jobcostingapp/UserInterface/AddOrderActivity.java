package com.softeng.jobcosting.jobcostingapp.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

import java.util.ArrayList;

public class AddOrderActivity extends AppCompatActivity implements OnTouchListener {
    private ArrayList<View> items;
    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = new ArrayList<View>();

        //add the first item
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.addOrderLinearLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mView = inflater.inflate(R.layout.input_item, null);
        items.add(mView);
        mainLayout.addView(mView);

        //display the orderID
        //orderID = Integer.parseInt(values[ORDER_ID]);
        //TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        //String orderNumView = orderNum.getText().toString();
        //orderNumView += " " + Integer.toString(orderID);
        //orderNum.setText(orderNumView);

        //display the current date
        Date currDate = new Date();
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(currDate);
        TextView date = (TextView) findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += " " + formattedDate;
        date.setText(dateView);
    }

    //Called when ADD ITEM button is clicked
    public void addItem(View view) {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.addOrderLinearLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View lastItem = items.get(items.size() - 1);
        EditText store = (EditText) lastItem.findViewById(R.id.storeEditText);
        String lastStore = store.getText().toString();

        View mView = inflater.inflate(R.layout.input_item, null);
        items.add(mView);
        mainLayout.addView(mView);

        EditText newItem = (EditText) mView.findViewById(R.id.storeEditText);
        newItem.setText(lastStore);
    }

    //Called when DONE button is clicked
    public void done(View view) {
        boolean validStore = true;
        boolean validAmount = true;

        for(int i = 0; i < items.size(); i++) {
            EditText storeInput = (EditText) items.get(i).findViewById(R.id.storeEditText);
            String store = storeInput.getText().toString();

            Spinner types = (Spinner) items.get(i).findViewById(R.id.typeSpinner);
            String type = (types.getSelectedItem()).toString();

            EditText descriptionInput = (EditText) items.get(i).findViewById(R.id.descEditText);
            String description = descriptionInput.getText().toString();

            EditText amountInput = (EditText) items.get(i).findViewById(R.id.amtEditText);
            String stringAmount = amountInput.getText().toString();

            if (store.equals("")) {
                validStore = false;
            }

            if (stringAmount.equals("") || !stringAmount.matches("\\s*[-+]?\\d*(\\.\\d{1,2})?\\s*")) {
                validAmount = false;
            }
        }

        if(!validStore) {
            TextView errorPopup = (TextView) findViewById(R.id.errorMessage);
            String errorString = errorPopup.getText().toString();
            errorString += "Please enter a store for each item.";
            errorPopup.setText(errorString);
            View popup = findViewById(R.id.popup);
            popup.setVisibility(View.VISIBLE);
            popup.setOnTouchListener(this);
        } else if(!validAmount) {
            TextView errorPopup = (TextView) findViewById(R.id.errorMessage);
            String errorString = errorPopup.getText().toString();
            errorString += "Please enter a valid amount for each item.";
            errorPopup.setText(errorString);
            View popup = findViewById(R.id.popup);
            popup.setVisibility(View.VISIBLE);
            popup.setOnTouchListener(this);
        } else {
            //create a new order
            Calculations calc = new Calculations();
            String newOrder = calc.newOrder();

            //get orderID FOR NOW will eventually be putting it in myself.
            String[] values = newOrder.split(",");
            orderID = Integer.parseInt(values[0]);

            for(int i = 0; i < items.size(); i++) {
                EditText storeInput = (EditText) items.get(i).findViewById(R.id.storeEditText);
                String store = storeInput.getText().toString();

                Spinner types = (Spinner) items.get(i).findViewById(R.id.typeSpinner);
                String type = (types.getSelectedItem()).toString();

                EditText descriptionInput = (EditText) items.get(i).findViewById(R.id.descEditText);
                String description = descriptionInput.getText().toString();

                EditText amountInput = (EditText) items.get(i).findViewById(R.id.amtEditText);
                String stringAmount = amountInput.getText().toString();
                float amount = Float.parseFloat(stringAmount);

                calc.newItem(orderID, store, description, type, amount);
            }

            Intent returnIntent = new Intent(this, MainActivity.class);
            startActivity(returnIntent);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setVisibility(View.GONE);
        TextView errorPopup = (TextView) v.findViewById(R.id.errorMessage);
        errorPopup.setText("ERROR:\n");
        return true;
    }
}
