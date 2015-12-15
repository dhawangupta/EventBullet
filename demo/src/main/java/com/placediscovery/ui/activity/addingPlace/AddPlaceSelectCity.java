package com.placediscovery.ui.activity.addingPlace;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.placediscovery.R;

import java.util.ArrayList;

/**
 * Created by Dhawan Gupta on 15-12-2015.
 */
public class AddPlaceSelectCity extends ListActivity {

    ArrayList<String> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_select_city);


        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems));


    }
    }
