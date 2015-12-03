package com.placediscovery.ui.activity;

/**
 * Created by ARIMIT on 14-Oct-15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.placediscovery.HelperMethods;
import com.placediscovery.MongoLabPlace.GetPlacesAsyncTask;
import com.placediscovery.MongoLabPlace.Place;
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
        //TODO: add city specific code here

        if(HelperMethods.isInternetAvailable(ChooseCity.this))
        {
            if (position == 5) {
//            final ProgressDialog progressDialog = new ProgressDialog(CardUI.this,
//                    R.style.AppTheme_Dark_Dialog);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Loading Places in Varanasi...");
//            progressDialog.show();


//            ArrayList<Place> VaranasiPlaces = null;
//           GetPlacesAsyncTask task = new GetPlacesAsyncTask();
//
//            try {
//               VaranasiPlaces = task.execute().get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//           } catch (ExecutionException e) {
//               e.printStackTrace();
//           }
//
//
//            HelperMethods.saveObjectToCache("varanasiPlaces", VaranasiPlaces);

//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            // On complete call either onSignupSuccess or onSignupFailed
//                            // depending on success
//
//                            // onSignupFailed();
//                            progressDialog.dismiss();
//                        }
//                    }, 3000);
                startActivity(new Intent(ChooseCity.this, MapsActivity.class));
            }
        } else {
            Toast.makeText(ChooseCity.this, "Check your internet!!",Toast.LENGTH_LONG).show();
        }
    }


}
