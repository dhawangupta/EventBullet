package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MapsActivity;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabUser.UserStatus;
import com.placediscovery.R;


public class ContentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity);

        //Following is the upper toolbar code which is not needed for now.
        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My title");     //title for the toolbar
        */

        Intent intent = getIntent();
        int imageviewId = intent.getExtras().getInt("imageviewId");
        ArrayList<Place> places = (ArrayList<Place>) intent.getExtras().getSerializable("placesObject");

        String place_name = places.get(imageviewId).getName();
        String place_content = places.get(imageviewId).getContent();;
        TextView t1 = (TextView)findViewById(R.id.place_name);
        TextView t2 = (TextView)findViewById(R.id.place_content);
        t1.setText(place_name);
        t2.setText(place_content);

        int loader = R.drawable.loader;         //loader image
        // Image url
        String image_url = places.get(imageviewId).getImageURL();
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(image_url, loader, (ImageView)findViewById(R.id.contentPageImage));


        //some toolbar code
        /*
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        */

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserStatus.GetStatus()){

                    Toast.makeText(ContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}
