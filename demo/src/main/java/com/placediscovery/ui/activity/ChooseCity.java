package com.placediscovery.ui.activity;

/**
 * Created by ARIMIT on 14-Oct-15.
 */

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import com.placediscovery.HelperMethods;
import com.placediscovery.ui.activity.adapter.CityAdapter;

import com.placediscovery.R;
import com.placediscovery.ui.activity.adapter.CityManager;
import com.placediscovery.MapsActivity;
public class ChooseCity extends AppCompatActivity implements ViewHolderResponser {

    private RecyclerView mRecyclerView;
    private CityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecity);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CityAdapter(CityManager.getInstance().getCities(), R.layout.row_country,new WeakReference<ViewHolderResponser>(this), this);
        mRecyclerView.setAdapter(mAdapter);

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
    }


    @Override
    public void didClickOnView(View view, int position)
    {
        if(!HelperMethods.isInternetAvailable(ChooseCity.this)) {
            Toast.makeText(ChooseCity.this, "Check your internet!!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(ChooseCity.this, MapsActivity.class);

        //Add here for selecting city from card layout. "cityname" should be same as that in db.
        switch (position) {
            case 0 : intent.putExtra("selectedCity","kolkata");
                startActivity(intent);
                break;
            case 1 : intent.putExtra("selectedCity","mumbai");
                startActivity(intent);
                break;
            case 2 : intent.putExtra("selectedCity","newdelhi");
                startActivity(intent);
                break;
            case 3 : intent.putExtra("selectedCity","chennai");
                startActivity(intent);
                break;
            case 4 : intent.putExtra("selectedCity","bengaluru");
                startActivity(intent);
                break;
            case 5 : intent.putExtra("selectedCity", "varanasi");
                startActivity(intent);
                break;
            case 6 : intent.putExtra("selectedCity","jaipur");
                startActivity(intent);
                break;
            case 7 : intent.putExtra("selectedCity","agra");
                startActivity(intent);
                break;

        }


    }


}
