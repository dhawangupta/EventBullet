package com.placediscovery.ui.activity.addingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;
import com.placediscovery.Constants;
import com.placediscovery.R;

public class AddPlaceMaps extends AppCompatActivity implements MapView.OnInfoWindowClickListener {

    MapView mapView;
    LatLng selectedCityLatLng;
    double lat=20,lon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_maps);
        lat=getIntent().getDoubleExtra(Constants.selectedCityLat,0);
        lon=getIntent().getDoubleExtra(Constants.selectedCityLon,0);
        selectedCityLatLng=new LatLng(lat,lon);
        setUpMapIfNeeded(savedInstanceState);
    }


    private void setUpMapIfNeeded(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(new LatLng(selectedCityLatLng));
        mapView.setZoomLevel(11);
        mapView.setTiltEnabled(true);
        mapView.setMyLocationEnabled(true);
        mapView.addMarker(new MarkerOptions()
                        .position(new LatLng(selectedCityLatLng))
                        .snippet("click to add a place")
        );
        mapView.onCreate(savedInstanceState);
    }





//    @Override
//    public void onMapLongClick(LatLng latLng) {
//
//
//
//        //TODO: use the following code wherever an alert dialog is needed
//        // we will show a prompt to user whenever s/he tries adding the marked location

//        new AlertDialog.Builder(AddPlace.this)
//                .setTitle("Add Place")
//                .setMessage("Are you sure you want to add this place?")
//                .setPositiveButton(android.R.string.yes, new
//                        DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface
//                                                        dialog, int which) {
//                                // continue with saving in database
//                            }
//                        })
//                .setNegativeButton(android.R.string.no, new
//                        DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface
//                                                        dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//        // submit place details to database

    //   }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * Called when the user clicks on an info window.
     *
     * @param marker The marker of the info window the user clicked on.
     * @return If true the listener has consumed the event and the info window will not be closed.
     */
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Intent i = new Intent(AddPlaceMaps.this, AddPlaceContent.class);
        i.putExtra(Constants.Latitutude, marker.getPosition().getLatitude());
        i.putExtra(Constants.Longitude, marker.getPosition().getLongitude());
        marker.remove();
        startActivity(i);
        return false;
    }
}
