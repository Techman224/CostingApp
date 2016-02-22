package com.softeng.jobcosting.jobcostingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalDatabase gb = new GlobalDatabase();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView mainList = (ListView) findViewById(R.id.listView);
        String[] listItems = getResources().getStringArray(R.array.sports_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.listText, listItems);
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new ListClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.calculator) {
            startActivity(new Intent(MainActivity.this, ManualCalculator.class));
        }
        else if (id == R.id.summary) {
            startActivity(new Intent(MainActivity.this, SummaryActivity.class));
        }
        else if (id == R.id.editOrder) {
            startActivity(new Intent(MainActivity.this, EditOrderActivity.class));
        }
        else if (id == R.id.addOrder) {
            startActivity (new Intent(MainActivity.this, AddOrderActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public class ListClickListener implements OnItemClickListener   {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

            TextView listText = (TextView) view.findViewById(R.id.listText);
            String textFromList = listText.getText().toString();

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra("orderID", textFromList);
            startActivity(intent);
        }
    }
}
