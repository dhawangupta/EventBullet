package com.placediscovery.ui.activity.addingPlace;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.R;

import static com.placediscovery.HelperClasses.Constants.cityArray;


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
        String selectedCity="";

        //position corresponds to position of cities in cityArray
        switch (position){
            case 0: selectedCityLatLng = Constants.kolkataLatLng;
                selectedCity = Constants.Varanasi.toLowerCase();
                break;
            case 1: selectedCityLatLng = Constants.mumbaiLatLng;
                selectedCity = Constants.Agra.toLowerCase();
                break;
            case 2: selectedCityLatLng = Constants.newdelhiLatLng;
                selectedCity = Constants.Kolkata.toLowerCase();
                break;
            case 3: selectedCityLatLng = Constants.chennaiLatLng;
                selectedCity = Constants.Mumbai.toLowerCase();
               break;
            case 4: selectedCityLatLng = Constants.bengaluruLatLng;
                selectedCity = Constants.NewDelhi.toLowerCase();
                break;
            case 5: selectedCityLatLng = Constants.varanasiLatLng;
                selectedCity = Constants.Chennai.toLowerCase();
                break;
            case 6: selectedCityLatLng = Constants.jaipurLatLng;
                selectedCity = Constants.Bengaluru.toLowerCase();
                break;
            case 7: selectedCityLatLng = Constants.agraLatLng;
                selectedCity = Constants.Jaipur.toLowerCase();
                break;
            case 8: selectedCityLatLng = Constants.hyderabadLatLng;
                selectedCity = Constants.Hyderabad.toLowerCase();
                break;
        }


        if(selectedCityLatLng==null)
        {
            Toast.makeText(getApplicationContext(),"userPlaceLatLng is null",Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, AddPlaceMapsActivity.class);
            i.putExtra("selectedCity", selectedCity);
            i.putExtra(Constants.Latitutude,selectedCityLatLng.getLatitude());
            i.putExtra(Constants.Longitude,selectedCityLatLng.getLongitude());
            startActivity(i);
        }
    }
}
