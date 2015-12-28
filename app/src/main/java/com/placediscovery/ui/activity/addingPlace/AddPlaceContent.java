//created by ARIMIT

package com.placediscovery.ui.activity.addingPlace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.placediscovery.AWSClasses.Util;
import com.placediscovery.R;

import java.util.List;


public class AddPlaceContent extends AppCompatActivity
{

    protected EditText place_title;
    protected EditText place_details;
    protected Button submit_button;

    // The TransferUtility is the primary class for managing transfer to S3
    private TransferUtility transferUtility;

    // The SimpleAdapter adapts the data about transfers to rows in the UI
    private SimpleAdapter simpleAdapter;

    // A List of all transfers
    private List<TransferObserver> observers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_content);

        // Initializes TransferUtility, always do this before using it.
        transferUtility = Util.getTransferUtility(this);

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

                Toast.makeText(AddPlaceContent.this, "Add Image", Toast.LENGTH_SHORT).show();

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