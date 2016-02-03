package com.placediscovery.ui.activity;

/**
 * Created by ARIMIT on 14-Oct-15.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabPlace.PlaceQueryBuilder;
import com.placediscovery.R;
import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.HelperClasses.HelperMethods;
import com.placediscovery.ui.activity.adapter.CityAdapter;
import com.placediscovery.ui.activity.adapter.CityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChooseCity extends AppCompatActivity implements ViewHolderResponser {

    private RecyclerView mRecyclerView;
    private CityAdapter mAdapter;
    private ProgressDialog progressDialog;
    Intent intent;

    ArrayList<Place> places = new ArrayList<>();
    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecity);

        intent = new Intent(ChooseCity.this, MapsActivity.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CityAdapter(CityManager.getInstance().getCities(), R.layout.row_city,new WeakReference<ViewHolderResponser>(this), this);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.choose_city);  //title for the toolbar

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

        if(position==0) {
            selectedCity = Constants.cityArray[position].toLowerCase();

            GetPlacesAsyncTaskProgressDialog task = new GetPlacesAsyncTaskProgressDialog();
            try {
                task.execute();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else{
            Toast.makeText(ChooseCity.this, "We will be there soon!!!", Toast.LENGTH_LONG).show();
        }

    }

    private class GetPlacesAsyncTaskProgressDialog extends AsyncTask<Place, Void, ArrayList<Place>> {
        String server_output = null;
        String temp_output = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ChooseCity.this, "",
                    "loading places...", false, true,               //indeterminate:false, cancelable:true
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            GetPlacesAsyncTaskProgressDialog.this.cancel(true);
                        }
                    });
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected ArrayList<Place> doInBackground(Place... arg0) {

            ArrayList<Place> places = new ArrayList<>();
            try{
                PlaceQueryBuilder qb = new PlaceQueryBuilder(selectedCity);
                URL url = new URL(qb.buildPlacesGetURL());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }

                // create a basic db list
                String mongoarray = "{ artificial_basicdb_list: "+server_output+"}";
                Object o = com.mongodb.util.JSON.parse(mongoarray);

                DBObject dbObj = (DBObject) o;
                BasicDBList places_list = (BasicDBList) dbObj.get("artificial_basicdb_list");

                for (Object obj : places_list) {
                    DBObject placeObj = (DBObject) obj;

                    Place temp = new Place();
                    temp.setPlace_id(placeObj.get("_id").toString());
                    temp.setName(placeObj.get("name").toString());
                    temp.setLatitude(placeObj.get("latitude").toString());
                    temp.setLongitude(placeObj.get("longitude").toString());
                    temp.setFilter(placeObj.get("filter").toString());
                    temp.setImageURL(placeObj.get("imageURL").toString());
                    temp.setContent(placeObj.get("content").toString());
                    temp.setAverageRating(placeObj.get("averageRating").toString());
                    temp.setCount(placeObj.get("count").toString());
                    temp.setTimings(placeObj.get("timings").toString());
                    temp.setTicket(placeObj.get("ticket").toString());
                    temp.setBestTime(placeObj.get("bestTime").toString());

                    try {
                        BasicDBList reviewsList = (BasicDBList) placeObj.get("reviews");
                        BasicDBObject[] reviewsArr = reviewsList.toArray(new BasicDBObject[0]);
                        temp.setReviews(reviewsArr);
                    } catch (Exception ex){}

                    places.add(temp);

                }
            }catch (Exception e) {
                e.getMessage();
            }

            return places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> places) {
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();}

            if(places.size()!=0) {
                try {
                    HelperMethods.writeObject(ChooseCity.this,selectedCity,places);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("places", places);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            } else {
                try {
                    places = (ArrayList<Place>)HelperMethods.readObject(ChooseCity.this, selectedCity);
                    intent.putExtra("places", places);
                    intent.putExtra("selectedCity", selectedCity);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Something went wrong, please try again!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}



