package br.liveo.ndrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.liveo.ndrawer.ImageLoader.ImageLoader;
import br.liveo.ndrawer.MongoLabPlace.GetPlacesAsyncTask;
import br.liveo.ndrawer.MongoLabPlace.Place;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.ui.activity.ChooseCity;
import br.liveo.ndrawer.ui.activity.ContentActivity;

public class MapsActivity extends FragmentActivity {

    ArrayList<Place> VaranasiPlaces = new ArrayList<Place>();
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // 3g test
        //retrieving places
        try {
            GetPlacesAsyncTask task = new GetPlacesAsyncTask();
            try {
                VaranasiPlaces = task.execute().get();
            } catch (InterruptedException e) {
                 e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            //dyanmically creating imageviews
            ImageView[] iv = new ImageView[VaranasiPlaces.size()];
//            for(int i=0; i<VaranasiPlaces.size(); i++){
//                iv[i] = new ImageView(this);
//
//
//            }


            int loader = R.drawable.loader;

            final Intent[] intents = new Intent[30];

    //loop show to multiple images
    for (i = 1; i <= 11; i++) {
        String idImageView = "imageview" + i;
        // Imageview to show
        iv[i] = (ImageView) findViewById(MapsActivity.this.getResources().getIdentifier(idImageView, "id", getPackageName()));

        // Image url
        String image_url = VaranasiPlaces.get(i - 1).getImageURL();
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(image_url, loader, iv[i]);


        //on-click action:
        iv[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intents[i] = new Intent(MapsActivity.this, ContentActivity.class);

                intents[i].putExtra("imageviewId", getResources().getResourceEntryName(v.getId()));
                intents[i].putExtra("place_name"+i, VaranasiPlaces.get(i - 1).getName());
                intents[i].putExtra("place_content"+i, VaranasiPlaces.get(i - 1).getContent());
                startActivity(intents[i]);
            }
        });

    }
        } catch (IndexOutOfBoundsException e){
    //startActivity(new Intent(MapsActivity.this, ChooseCity.class));
    Toast.makeText(this, "Check your internet!!!", Toast.LENGTH_LONG).show();
        }
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

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        //VaranasiPlaces = (ArrayList<Place>)HelperMethods.readObjectFromFile("varanasiPlaces");
    try{
        Place first = VaranasiPlaces.get(0);
        if(first!=null)
        {
            // Toast.makeText(getApplicationContext(),first.getLatitude(),Toast.LENGTH_LONG);
            LatLng flatlang = new LatLng(Double.parseDouble(first.getLatitude()), Double.parseDouble(first.getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(flatlang, 10));
            for (Place x : VaranasiPlaces)
            {
                mMap.addMarker(new MarkerOptions().position(
                        new LatLng( Double.parseDouble(x.getLatitude()),
                                Double.parseDouble(x.getLongitude()))).title(x.getName()));
            }
        }
    } catch (IndexOutOfBoundsException e){
        //startActivity(new Intent(MapsActivity.this, ChooseCity.class));
        Toast.makeText(this, "Check your internet!!!", Toast.LENGTH_LONG).show();
    }
    }

}
