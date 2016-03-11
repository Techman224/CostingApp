package com.softeng.jobcosting.jobcostingapp.UserInterface;

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

import com.softeng.jobcosting.jobcostingapp.BusinessLogic.Calculations;
import com.softeng.jobcosting.jobcostingapp.R;

import java.util.ArrayList;

public class EditOrderActivity extends AppCompatActivity {
    private ArrayList<View> views;
    private Calculations calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        views = new ArrayList<View>();

        calc = new Calculations();

        int orderID = 1;
        String dateString = "2016/02/22";
        int firstCostID = 1;

        TextView orderNum = (TextView)findViewById(R.id.orderNumber);
        String orderNumView = orderNum.getText().toString();
        orderNumView += " " + Integer.toString(orderID);
        orderNum.setText(orderNumView);

        TextView date = (TextView)findViewById(R.id.date);
        String dateView = date.getText().toString();
        dateView += " " + dateString;
        date.setText(dateView);

        String result = calc.getItems(orderID);
        orderNum.setText(result);

        if(result != null) {
            String[] items = result.split("\n");

            final int STORE = 2;
            final int DESCRIPTION = 3;
            final int TYPE = 4;
            final int PRICE = 5;

            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            boolean found = false;

            for (String item : items) {
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

    public void update(View view) {
        for(int currCostID = 1; currCostID <= views.size(); currCostID++) {
            View v = views.get(currCostID - 1); //the first element has costID=1 and index=0

            EditText storeInput = (EditText) v.findViewById(R.id.storeEditText);
            String store = storeInput.getText().toString();
            calc.editItem("Store", store, currCostID);

            Spinner types = (Spinner) v.findViewById(R.id.typeSpinner);
            String type = (types.getSelectedItem()).toString();
            calc.editItem("Type", type, currCostID);

            EditText descriptionInput = (EditText) v.findViewById(R.id.descEditText);
            String description = descriptionInput.getText().toString();
            calc.editItem("Description", description, currCostID);

            EditText amountInput = (EditText) v.findViewById(R.id.amtEditText);
            String amount = amountInput.getText().toString();
            calc.editItem("Price", amount, currCostID);
        }

        Intent returnIntent = new Intent(this, MainActivity.class);
        startActivity(returnIntent);
    }
}
