package com.placediscovery.ui.activity.addingPlace;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;
import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.R;

import static com.placediscovery.R.drawable.ic_add_location_black_24dp;

public class AddPlaceMapsActivity extends AppCompatActivity implements MapView.OnScrollListener, View.OnClickListener {


    Marker dragMarker;
    AppCompatButton btnAddPlaceMaps;
    String selectedCity;
    Icon mIcon;
    private MapView mapView;
    private LatLng selectedCityLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_maps);
        getPreIntents();

        btnAddPlaceMaps = (AppCompatButton) findViewById(R.id.btn_addPlaceMaps);
        btnAddPlaceMaps.setOnClickListener(this);


        setUpMapIfNeeded(savedInstanceState);

    }


    private void setUpMapIfNeeded(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.mapviewAddingPlace);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setCenterCoordinate(selectedCityLatLng);
        mapView.setZoomLevel(11);
        mapView.setTiltEnabled(true);
        mapView.setMyLocationEnabled(true);
        mapView.setOnScrollListener(this);
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
        IconFactory iconFactory = IconFactory.getInstance(this);
        Drawable mDrawable;
        mDrawable = ContextCompat.getDrawable(this, ic_add_location_black_24dp);

        mIcon = iconFactory.fromDrawable(mDrawable);
        Toast.makeText(this,"Hiya",Toast.LENGTH_LONG).show();
        dragMarker=mapView.addMarker(new MarkerOptions()
                        .position(new LatLng(selectedCityLatLng))
                .icon(mIcon)

        );

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


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /**
     * Called when the map is scrolled.
     */
    @Override
    public void onScroll() {
        if(dragMarker!=null)
        {

            mapView.removeMarker(dragMarker);
            dragMarker=null;
        }
        LatLng latLng=mapView.getCenterCoordinate();
        dragMarker=mapView.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng))
                        .snippet("click to add a place")
                        .icon(mIcon)
        );

    }

    private void getPreIntents()
    {
        selectedCityLatLng=new LatLng(getIntent().getDoubleExtra(Constants.Latitutude,0),getIntent().getDoubleExtra(Constants.Longitude,0));
        selectedCity = getIntent().getExtras().getString("selectedCity");
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        LatLng userPlaceLatLng =new LatLng(mapView.getCenterCoordinate());
        Intent intent = new Intent(AddPlaceMapsActivity.this, AddPlaceContent.class);
        intent.putExtra(Constants.Latitutude,userPlaceLatLng.getLatitude()).putExtra(Constants.Longitude,userPlaceLatLng.getLongitude());
        intent.putExtra("selectedCity", selectedCity);
        startActivity(intent);
    }
}
