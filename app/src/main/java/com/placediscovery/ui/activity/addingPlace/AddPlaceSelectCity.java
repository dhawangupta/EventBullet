package com.placediscovery.ui.activity.addingPlace;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.placediscovery.Constants;
import com.placediscovery.R;

import static com.placediscovery.Constants.cityArray;
import static com.placediscovery.Constants.selectedCityLat;
import static com.placediscovery.Constants.selectedCityLon;


/**
 * Created by Dhawan Gupta on 15-12-2015.
 */
//TODO:Sarkar design it as you wish

public class AddPlaceSelectCity extends ListActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_select_city);

        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cityArray));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        LatLng selectedCityLatLng=null;

        //position corresponds to position of cities in cityArray
        switch (position){
            case 0: selectedCityLatLng = Constants.kolkataLatLng;
                break;
            case 1: selectedCityLatLng = Constants.mumbaiLatLng;
                break;
            case 2: selectedCityLatLng = Constants.newdelhiLatLng;
                break;
            case 3: selectedCityLatLng = Constants.chennaiLatLng;
               break;
            case 4: selectedCityLatLng = Constants.bangaloreLatLng;
                break;
            case 5: selectedCityLatLng = Constants.varanasiLatLng;
                break;
            case 6: selectedCityLatLng = Constants.jaipurLatLng;
                break;
            case 7: selectedCityLatLng = Constants.agraLatLng;
                break;
            case 8: selectedCityLatLng = Constants.hyderabadLatLng;
                break;
        }


        if(selectedCityLatLng==null)
        {
            Toast.makeText(getApplicationContext(),"userPlaceLatLng is null",Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, AddPlaceMaps.class);
            i.putExtra("selectedCityLatLng", (Parcelable) selectedCityLatLng);
            startActivity(i);
        }
    }
}
