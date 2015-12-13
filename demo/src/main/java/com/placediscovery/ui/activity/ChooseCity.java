package com.placediscovery.ui.activity;

/**
 * Created by ARIMIT on 14-Oct-15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import com.placediscovery.HelperMethods;
import com.placediscovery.MongoLabPlace.PlaceQueryBuilder;
import com.placediscovery.ui.activity.adapter.CityAdapter;

import com.placediscovery.R;
import com.placediscovery.ui.activity.adapter.CityManager;
import com.placediscovery.MapsActivity;
public class ChooseCity extends Activity implements ViewHolderResponser {

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
            case 4 : intent.putExtra("selectedCity","bengaluru");
                startActivity(intent);
                break;
            case 5 : intent.putExtra("selectedCity", "varanasi");
                startActivity(intent);
                break;
            case 6 : intent.putExtra("selectedCity","jaipur");
                startActivity(intent);
                break;

        }


    }


}
