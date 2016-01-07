package com.placediscovery.MongoLabPlace;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dhawan Gupta on 31-12-2015.
 */
public class CreatePlaceAsyncTask extends AsyncTask<Place, Void, Boolean>{

    String selectedCity;

    public CreatePlaceAsyncTask(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @Override
    protected Boolean doInBackground(Place... params) {
        try
        {
            Place place = params[0];

            PlaceQueryBuilder qb = new PlaceQueryBuilder(selectedCity);
            URL url = new URL(qb.buildPlacesSaveURL());
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(false);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            //connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(
                    connection.getOutputStream());

            osw.write(qb.createPlace(place));
            osw.flush();
            osw.close();
            return connection.getResponseCode() < 205;

        } catch (Exception e) {
            //e.getCause();
            String val = e.getMessage();
            String val2 = val;
            return false;
        }
    }

}

