package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.placediscovery.MapsActivity;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.R;


public class ContentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My title");     //title for the toolbar

        setTitle(R.id.title);

        Intent intent = getIntent();
        int imageviewId = intent.getExtras().getInt("imageviewId");
        ArrayList<Place> VaranasiPlaces = (ArrayList<Place>) intent.getExtras().getSerializable("placesObject");
        String place_name = VaranasiPlaces.get(imageviewId).getName();
        String place_content = VaranasiPlaces.get(imageviewId).getContent();;

        TextView t1 = (TextView)findViewById(R.id.place_name);
        TextView t2 = (TextView)findViewById(R.id.place_content);

        t1.setText(place_name);
        t2.setText(place_content);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
