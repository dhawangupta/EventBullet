package com.placediscovery.ui.activity.addingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.placediscovery.Constants;
import com.placediscovery.R;

public class AddPlaceMaps extends FragmentActivity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Marker marker=null;
    LatLng selectedCityLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_maps);

        selectedCityLatLng = getIntent().getExtras().getParcelable("selectedCityLatLng");
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions()
                .position(selectedCityLatLng)
                .title("click to add a place")).setDraggable(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedCityLatLng, 10));

        mMap.setOnInfoWindowClickListener(this);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i =new Intent(AddPlaceMaps.this,AddPlaceContent.class);
        i.putExtra(Constants.Latitutude,marker.getPosition().latitude);
        i.putExtra(Constants.Longitude,marker.getPosition().longitude);
        marker.remove();
        startActivity(i);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {



        //TODO: use the following code wherever an alert dialog is needed
        // we will show a prompt to user whenever s/he tries adding the marked location

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

    }
}
