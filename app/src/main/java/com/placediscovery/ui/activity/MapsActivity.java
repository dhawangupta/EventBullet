package com.placediscovery.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.R;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    ArrayList<Event> events = new ArrayList<>();
    ArrayList<Event> events_filtered = new ArrayList<>();
    ArrayList<Marker> events_marker = new ArrayList<>();
    String selectedCity;
    TextView[] filtersTextViews;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




//        selectedCity = getIntent().getExtras().getString("selectedCity");
        //retrieving events
        events = (ArrayList<Event>) getIntent().getExtras().getSerializable("events");
        filtersTextViews = new TextView[events.size()];
//        initTextViews();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    private void initTextViews() {
        filtersTextViews[0] = (TextView) findViewById(R.id.filter_all);
        filtersTextViews[1] = (TextView) findViewById(R.id.filter_attractions);
        filtersTextViews[2] = (TextView) findViewById(R.id.filter_food);
        filtersTextViews[3] = (TextView) findViewById(R.id.filter_events);
        filtersTextViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events_filtered = events;
                loadMarkers(events_filtered);
            }
        });
        setFilters(filtersTextViews[1]);
        setFilters(filtersTextViews[2]);
        setFilters(filtersTextViews[3]);
    }

    private void setFilters(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                String filter = (String) tv.getText();
                events_filtered.clear();
                for (Event p : events) {
//                    if (p.getFilter().equals(filter))
                        events_filtered.add(p);
                }
                loadMarkers(events_filtered);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        setUpMap();
    }


    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        try {
            Event first = events.get(0);
            if (first != null) {
                LatLng flatlang = new LatLng(Double.parseDouble(first.getLatitude()), Double.parseDouble(first.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(flatlang, 11));
                for (Event x : events) {
                    events_marker.add(mMap.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(x.getLatitude()),
                                    Double.parseDouble(x.getLongitude()))).title(x.getName())));
                }
            }
        } catch (IndexOutOfBoundsException e) {
            //startActivity(new Intent(MapsActivity.this, ChooseCity.class));
            Toast.makeText(this, "Check your internet!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadMarkers(ArrayList<Event> events_filtered) {

        mMap.clear();

        for (Event x : events_filtered) {
            events_marker.add(mMap.addMarker(new MarkerOptions().position(
                    new LatLng(Double.parseDouble(x.getLatitude()),
                            Double.parseDouble(x.getLongitude()))).title(x.getName())));
        }

        Toast.makeText(MapsActivity.this, "Events Updated for you", Toast.LENGTH_SHORT).show();
        //change map events to explore events
    }


    private void loadEventsImages() {
        int loader = R.drawable.loader;         //loader image
        final Intent[] intents = new Intent[events.size()];

        //dyanmically creating imageviews
        LinearLayout imageviews = (LinearLayout) findViewById(R.id.imageviews);
        ImageView[] iv = new ImageView[events.size()];

        //converting px to dp
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx = (int) (150 * scale);
        int dpHeightInPx = (int) (100 * scale);
        int dpMarginInPx = (int) (1 * scale);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        params.setMargins(dpMarginInPx, dpMarginInPx, dpMarginInPx, dpMarginInPx);

        for (int j = 0; j < events.size(); j++) {
            // Image url
            String image_url = events.get(j).getImageURL().split(",")[0];
            if (image_url.equals("")) {
                continue;
            }
            iv[j] = new ImageView(this);
            iv[j].setId(j + 1);
            iv[j].setLayoutParams(params);
            imageviews.addView(iv[j]);

            // ImageLoader class instance
//            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
//            imgLoader.DisplayImage(image_url, loader, iv[j]);

            //on-click action:
            final int finalJ = j;
            iv[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intents[finalJ] = new Intent(MapsActivity.this, ContentActivity.class);

                    intents[finalJ].putExtra("imageviewId", finalJ);
                    intents[finalJ].putExtra("eventsObject", events);
                    intents[finalJ].putExtra("selectedCity", selectedCity);
                    startActivity(intents[finalJ]);
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        loadEventsImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.add_fab) {
//
//
//            if (location == null) {
//                return;
//            }
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            Toast.makeText(this, String.valueOf(latitude), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}