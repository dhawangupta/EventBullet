package com.placediscovery.ui.activity.addingPlace;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.placediscovery.R;


/**
 * Created by Dhawan Gupta on 15-12-2015.
 */
//TODO:Sarkar design it as you wish

public class AddPlaceSelectCity extends ListActivity {

    private static String[] cityArray = {"Kolkata", "Mumbai", "New Delhi", "Chennai", "Bangalore", "Varanasi", "Jaipur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_select_city);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cityArray));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String selectedCity = (String) getListAdapter().getItem(position);


    }
}
