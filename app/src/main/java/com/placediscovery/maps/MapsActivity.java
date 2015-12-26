package com.placediscovery.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;
import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MongoLabPlace.GetPlacesAsyncTask;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.R;
import com.placediscovery.ui.activity.ContentActivity;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    protected Button travel;
    protected Button explore;
    //AppCompatActivity extends FragmentActivity
    Spinner spinner;
    // Create a Data Source it may be an Array of String or ArrayList<String>
    String []arr = {"Filter","Gaming","Drugs","Sex","Partying","Religious","Others"};
    // An adapter to show data
    ArrayAdapter<String> adapter;
    ArrayList<Place> places = new ArrayList<>();
//    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    MapView mapView=null;
    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        travel = (Button) findViewById(R.id.btn_travel);
        explore = (Button) findViewById(R.id.btn_explore);
        spinner = (Spinner) findViewById(R.id.spinnerCountry);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arr);
        spinner.setAdapter(adapter);
// Used OnItemSelected Listener for Spinner item click, here i am showing a toast
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Clicked" + selectedItem, Toast.LENGTH_LONG).show();
// Filter option chosen
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        travel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Travel Places", Toast.LENGTH_SHORT).show();
                //change map places to travel places
            }
        });

        explore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MapsActivity.this, "Explore Places", Toast.LENGTH_SHORT).show();
                //change map places to explore places
            }
        });



//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("Add Place");  //title for the toolbar
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });         //back icon added


        selectedCity = getIntent().getExtras().getString("selectedCity");
        //retrieving places
        try {

//            try
//            {
//                places = (ArrayList<Place>) HelperMethods.readObjectFromFile("saved_" + selectedCity);
//            } catch (NullPointerException n)
            {
                GetPlacesAsyncTask task = new GetPlacesAsyncTask(selectedCity);
                try {
                    places = task.execute().get();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            int loader = R.drawable.loader;         //loader image
            final Intent[] intents = new Intent[places.size()];

            //dyanmically creating imageviews
            LinearLayout imageviews = (LinearLayout) findViewById(R.id.imageviews);
            ImageView[] iv = new ImageView[places.size()];

            //converting px to dp
            final float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx  = (int) (150 * scale);
            int dpHeightInPx = (int) (100 * scale);
            int dpMarginInPx = (int) (1*scale);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            params.setMargins(dpMarginInPx, dpMarginInPx, dpMarginInPx, dpMarginInPx);

            for(int j=0; j<places.size(); j++){
                // Image url
                String image_url = places.get(j).getImageURL();

                if(image_url.equals("")){
                    continue;
                }

                iv[j] = new ImageView(this);
                iv[j].setId(j+1);
                iv[j].setLayoutParams(params);
                imageviews.addView(iv[j]);

                // ImageLoader class instance
                ImageLoader imgLoader = new ImageLoader(getApplicationContext());
                // whenever you want to load an image from url
                // call DisplayImage function
                // url - image url to load
                // loader - loader image, will be displayed before getting image
                // image - ImageView
                imgLoader.DisplayImage(image_url, loader, iv[j]);

                //on-click action:
                final int finalJ = j;
                iv[j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intents[finalJ] = new Intent(MapsActivity.this, ContentActivity.class);

                        intents[finalJ].putExtra("imageviewId", finalJ);
                        intents[finalJ].putExtra("placesObject", places);
                        startActivity(intents[finalJ]);
                    }
                });

            }






            //following lines of code can be used for retrieving ids when used in a loop
//        String idImageView = "imageview" + i;
//        iv[i] = (ImageView) findViewById(MapsActivity.this.getResources().getIdentifier(i+"", "id", getPackageName()));




        } catch (IndexOutOfBoundsException e){
            Toast.makeText(this, "Check your internet!!!", Toast.LENGTH_LONG).show();
        }
        setUpMapIfNeeded(savedInstanceState);

    }



    private void setUpMapIfNeeded(Bundle savedInstanceState) {
        // Do a null check to confirm that we have not already instantiated the map.
        mapView=(MapView)findViewById(R.id.mapview);
        if (mapView != null)
        {
            setUpMap(savedInstanceState);
        }

    }


    private void setUpMap(Bundle savedInstanceState) {

        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setZoomLevel(11);
        mapView.onCreate(savedInstanceState);

        try{
            Place first = places.get(0);
            if(first!=null)
            {
                LatLng flatlang = new LatLng(Double.parseDouble(first.getLatitude()), Double.parseDouble(first.getLongitude()));
                mapView.setCenterCoordinate(flatlang);
                for (Place x : places)
                {
                    mapView.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(x.getLatitude()),
                                    Double.parseDouble(x.getLongitude()))).title(x.getName()));
                }
            }
        } catch (IndexOutOfBoundsException e){
            //startActivity(new Intent(MapsActivity.this, ChooseCity.class));
            Toast.makeText(this, "Check your internet!!!", Toast.LENGTH_LONG).show();
        }
    }
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
    public void onPause()  {
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


}
