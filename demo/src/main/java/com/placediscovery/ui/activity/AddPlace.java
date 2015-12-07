package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.placediscovery.MapsActivity;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.R;


public class AddPlace extends ActionBarActivity {

    protected EditText place_title;
    protected EditText place_details;
    protected Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);

        place_title = (EditText) findViewById(R.id.place_name);
        place_details = (EditText) findViewById(R.id.place_content);
        submit_button = (Button) findViewById(R.id.btn_submit);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Place");  //title for the toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });         //back icon added

        ImageView iv = (ImageView)findViewById(R.id.add_image);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(AddPlace.this, "Add Image", Toast.LENGTH_SHORT).show();

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // submit place details to database
            }
        });


    }
}
