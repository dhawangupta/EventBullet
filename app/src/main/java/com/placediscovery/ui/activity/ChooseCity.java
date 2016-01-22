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
import com.mongodb.DBObject;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabPlace.PlaceQueryBuilder;
import com.placediscovery.R;
import com.placediscovery.Constants;
import com.placediscovery.HelperMethods;
import com.placediscovery.ui.activity.adapter.CityAdapter;
import com.placediscovery.ui.activity.adapter.CityManager;

import java.io.BufferedReader;
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


    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecity);

        intent = new Intent(ChooseCity.this, MapsActivity.class);

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

        //Add here for selecting city from card layout. "cityname" should be same as that in db.
        switch (position) {
            case 0 : selectedCity = Constants.Kolkata.toLowerCase();
                break;
            case 1 : selectedCity = Constants.Mumbai.toLowerCase();
                break;
            case 2 : selectedCity = Constants.NewDelhi.toLowerCase();
                break;
            case 3 : selectedCity = Constants.Chennai.toLowerCase();
                break;
            case 4 : selectedCity = Constants.Bengaluru.toLowerCase();
                break;
            case 5 : selectedCity = Constants.Varanasi.toLowerCase();
                break;
            case 6 : selectedCity = Constants.Jaipur.toLowerCase();
                break;
            case 7 : selectedCity = Constants.Agra.toLowerCase();
                break;
            case 8 : selectedCity = Constants.Hyderabad.toLowerCase();
                break;

        }

        GetPlacesAsyncTaskProgressDialog task = new GetPlacesAsyncTaskProgressDialog();
        try {
            task.execute();

        } catch (Exception e) {
            e.printStackTrace();
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
            try
            {

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
                    DBObject userObj = (DBObject) obj;

                    Place temp = new Place();
                    temp.setPlace_id(userObj.get("_id").toString());
                    temp.setName(userObj.get("name").toString());
                    temp.setLatitude(userObj.get("latitude").toString());
                    temp.setLongitude(userObj.get("longitude").toString());
                    temp.setFilter(userObj.get("filter").toString());
                    temp.setImageURL(userObj.get("imageURL").toString());
                    temp.setContent(userObj.get("content").toString());
                    temp.setAverageRating(userObj.get("averageRating").toString());
                    temp.setCount(userObj.get("count").toString());
                    temp.setTimings(userObj.get("timings").toString());
                    temp.setTicket(userObj.get("ticket").toString());
                    temp.setBestTime(userObj.get("bestTime").toString());
                    temp.setToDo(userObj.get("toDo").toString());
                    places.add(temp);

                }
                HelperMethods.saveObjectToCache("saved_"+selectedCity, places);
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
                intent.putExtra("places", places);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"Something went wrong, please try again!!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}



