package com.placediscovery.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
//import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.R;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements View.OnClickListener {
    //AppCompatActivity extends FragmentActivity
    //FloatingActionButton actionButton;
    ProgressDialog progressDialog;
    //    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    MapView mapView=null;

    ArrayList<Place> places = new ArrayList<>();
    ArrayList<Place> places_filtered = new ArrayList<>();
    ArrayList<Marker> places_marker = new ArrayList<>();

    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        selectedCity = getIntent().getExtras().getString("selectedCity");
        //retrieving places
        places = (ArrayList<Place>)getIntent().getExtras().getSerializable("places");

//        actionButton = (FloatingActionButton)findViewById(R.id.fab);
//        setFAB(initFAB(R.drawable.ic_star));
//        createMenuItems();

        loadFiltersLayout();
        loadImages();
            
        setUpMapIfNeeded(savedInstanceState);

    }

//    private void setFAB(ImageView imageView) {
//        actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(imageView)
//                .setBackgroundDrawable(R.drawable.button_action_red_selector)
//                .build();
//    }
//
//    private ImageView initFAB(int drawable) {
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(drawable);
//        return imageView;
//
//    }
//
//    private void createMenuItems() {
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        itemBuilder.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_action_blue_selector));
////        Creating Menu  items
//        SubActionButton button1 = itemBuilder.setContentView(initFAB(R.drawable.ic_my_location_white_48dp))
//
//                .build();
//        SubActionButton button2 = itemBuilder.setContentView(initFAB(R.drawable.ic_close)).build();
//        SubActionButton button3 = itemBuilder.setContentView(initFAB(R.drawable.ic_menu)).build();
//        button1.setOnClickListener(this);
//        button2.setOnClickListener(this);
//        button3.setOnClickListener(this);
//        button3.setTag(Constants.TAG_LOCATION);
//        button2.setTag(Constants.TAG_FILTER);
//        button1.setTag(Constants.TAG_NOTYETDECIDED);
//
//
////        Create Menu with Items
//        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
//                .addSubActionView(button1)
//                .addSubActionView(button2)
//                .addSubActionView(button3)
//                        //  .addSubActionView(button4)
//                .attachTo(actionButton)
//                .build();
//
//    }

    private void loadFiltersLayout(){
        TextView[] filtersTextViews = new TextView[places.size()];

        filtersTextViews[0] = (TextView)findViewById(R.id.filter_all);
        filtersTextViews[1] = (TextView)findViewById(R.id.filter_attractions);
        filtersTextViews[2] = (TextView)findViewById(R.id.filter_food);
        filtersTextViews[3] = (TextView)findViewById(R.id.filter_events);

        filtersTextViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                places_filtered = places;
                loadMarkers(places_filtered);
            }
        });

        filtersTextViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                places_filtered.clear();
                for (Place p : places) {
                    if (p.getFilter().equals("Attractions"))
                        places_filtered.add(p);
                }
                loadMarkers(places_filtered);
            }
        });

        filtersTextViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                places_filtered.clear();
                for (Place p:places){
                    if(p.getFilter().equals("Food & Drinks"))
                        places_filtered.add(p);
                }
                loadMarkers(places_filtered);
            }
        });

        filtersTextViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                places_filtered.clear();
                for (Place p : places) {
                    if (p.getFilter().equals("Live") || p.getFilter().equals("Events"))
                        places_filtered.add(p);
                }
                loadMarkers(places_filtered);
            }
        });
    }

    private void loadMarkers(ArrayList<Place> places_filtered){
        for(Marker m:places_marker)
            mapView.removeMarker(m);

        for (Place x : places_filtered )
        {
            places_marker.add(mapView.addMarker(new MarkerOptions().position(
                    new LatLng(Double.parseDouble(x.getLatitude()),
                            Double.parseDouble(x.getLongitude()))).title(x.getName())));
        }

        Toast.makeText(MapsActivity.this, "Places Updated for you", Toast.LENGTH_SHORT).show();
        //change map places to explore places
    }


    private void loadImages(){
        int loader = R.drawable.loader;         //loader image

        // counting numOfImages
        int numOfImages = 0;
        for(Place p:places){
            if(p.getFilter().equals("Events"))
                numOfImages+=p.getEvents().length;
            else
                numOfImages++;
        }

        final Intent[] intents = new Intent[numOfImages];

        //dyanmically creating imageviews
        LinearLayout imageviews = (LinearLayout) findViewById(R.id.imageviews);
        ImageView[] iv = new ImageView[numOfImages];

        //converting px to dp
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (150 * scale);
        int dpHeightInPx = (int) (100 * scale);
        int dpMarginInPx = (int) (1*scale);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        params.setMargins(dpMarginInPx, dpMarginInPx, dpMarginInPx, dpMarginInPx);

        for(int j=0; j<numOfImages; j++){

            if(places.get(j).getFilter().equals("Events")){
                for(Event event:places.get(j).getEvents()){
                    String image_url = event.getImageURL().split(",")[0];
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

                    iv[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            } else {
                // Image url
                String image_url = places.get(j).getImageURL().split(",")[0];
                if (image_url.equals("")) {
                    continue;
                }
                iv[j] = new ImageView(this);
                iv[j].setId(j + 1);
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
                        intents[finalJ].putExtra("selectedCity", selectedCity);
                        startActivity(intents[finalJ]);
                    }
                });
            }
        }
        //following lines of code can be used for retrieving ids when used in a loop
//        String idImageView = "imageview" + i;
//        iv[i] = (ImageView) findViewById(MapsActivity.this.getResources().getIdentifier(i+"", "id", getPackageName()));
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
                    places_marker.add(mapView.addMarker(new MarkerOptions().position(
                            new LatLng(Double.parseDouble(x.getLatitude()),
                                    Double.parseDouble(x.getLongitude()))).title(x.getName())));
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getTag().equals(Constants.TAG_LOCATION)) {

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mapView.getMyLocation()))   // Sets the center of the map to Maracanã
                    .tilt(20)                                   // Sets the tilt of the camera to 30 degrees
                    .build();                                   // Creates a CameraPosition from the builder

            mapView.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 25000, null);
        } else if (v.getTag().equals(Constants.TAG_FILTER)) {

        } else if (v.getTag().equals(Constants.TAG_NOTYETDECIDED)) {

        }
    }

}
