package com.example.smitald.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smitald.stormy.R;
import com.example.smitald.stormy.Weather.Day;
import com.example.smitald.stormy.adapters.DayAdapter;

import java.util.Arrays;


public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        /*
        String [] daysOfWeek = {"sunday", "monday", "monday","monday","monday","monday","monday","monday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daysOfWeek);

        setListAdapter(adapter);
        */
        

        Intent intent = getIntent();
        Parcelable[] parcels = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcels, parcels.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String message = String.format("Hello Position %d", position);
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
