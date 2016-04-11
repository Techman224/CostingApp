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

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

import java.util.ArrayList;

public class EditOrderActivity extends AppCompatActivity implements OnTouchListener {
    private ArrayList<View> views;
    private Calculations calc;
    private int newItems;
    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        orderID = intent.getIntExtra("orderID", 0);

        views = new ArrayList<View>();
        calc = new Calculations();
        newItems = 0;

        TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        String orderNumView = orderNum.getText().toString();
        orderNumView += " " + Integer.toString(orderID);
        orderNum.setText(orderNumView);

        TextView date = (TextView) findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += " " + calc.getDate(orderID);
        date.setText(dateView);

        String result = calc.getItems(orderID);

        if(result != null) {
            String[] items = result.split("\n");

            final int STORE = 2;
            final int DESCRIPTION = 3;
            final int TYPE = 4;
            final int PRICE = 5;

            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.editOrderLinearLayout);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (String item : items) {
                boolean found = false;
                View mView = inflater.inflate(R.layout.input_item, null);
                mainLayout.addView(mView);
                views.add(mView);

                String[] fields = item.split(",");

                if(fields.length == 6) {
                    EditText storeInput = (EditText) mView.findViewById(R.id.storeEditText);
                    storeInput.setText(fields[STORE]);

                    Spinner types = (Spinner) mView.findViewById(R.id.typeSpinner);
                    String[] typeOptions = getResources().getStringArray(R.array.types);
                    for (int i = 0; i < typeOptions.length && !found; i++) {
                        if (typeOptions[i].equals(fields[TYPE])) {
                            types.setSelection(i);
                            found = true;
                        }
                    }

                    EditText descriptionInput = (EditText) mView.findViewById(R.id.descEditText);
                    descriptionInput.setText(fields[DESCRIPTION]);

                    EditText amountInput = (EditText) mView.findViewById(R.id.amtEditText);
                    amountInput.setText(fields[PRICE]);
                }
            }
        }
    }

    //Called when ADD ITEM button is clicked
    public void addItem(View view) {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.editOrderLinearLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View lastItem = views.get(views.size() - 1);
        EditText store = (EditText) lastItem.findViewById(R.id.storeEditText);
        String lastStore = store.getText().toString();

        View mView = inflater.inflate(R.layout.input_item, null);
        views.add(mView);
        mainLayout.addView(mView);
        newItems++;

        EditText newItem = (EditText) mView.findViewById(R.id.storeEditText);
        newItem.setText(lastStore);
    }

    //Called when UPDATE button is clicked
    public void update(View view) {
        boolean validStore = true;
        boolean validAmount = true;

        for(int i = 0; i < views.size(); i++) {
            View v = views.get(i);

            EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
            String store = storeInput.getText().toString();

            EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
            String amount = amountInput.getText().toString();

            if (store.equals("")) {
                validStore = false;
            }

            if (amount.equals("") || !amount.matches("\\s*[-+]?\\d*(\\.\\d{1,2})?\\s*")) {
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
            int i = 0;
            for(;i < (views.size() - newItems); i++) {
                View v = views.get(i);
                int costID = i + 1;

                EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
                String store = storeInput.getText().toString();
                calc.editItem("Store", store, costID);

                Spinner types = (Spinner) v.findViewById(R.id.typeSpinner);
                String type = (types.getSelectedItem()).toString();
                calc.editItem("Type", type, costID);

                EditText descriptionInput = (EditText) v.findViewById(R.id.descEditText);
                String description = descriptionInput.getText().toString();
                calc.editItem("Description", description, costID);

                EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
                String amount = amountInput.getText().toString();
                calc.editItem("Price", amount, costID);
            }

            for(;i < views.size(); i++) {
                View v = views.get(i);

                EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
                String store = storeInput.getText().toString();

                Spinner types = (Spinner) v.findViewById(R.id.typeSpinner);
                String type = (types.getSelectedItem()).toString();

                EditText descriptionInput = (EditText) v.findViewById(R.id.descEditText);
                String description = descriptionInput.getText().toString();

                EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
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
