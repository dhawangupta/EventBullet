package com.placediscovery.MongoLabPlace;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dhawan Gupta on 31-12-2015.
 */

/**
 * AsyncTask to update a given place
 * @author KYAZZE MICHAEL
 * @edited Dhawan Gupta
 *
 */
public class UpdatePlaceAsyncTask extends AsyncTask<Object, Void, Boolean> {

    String selectedCity;

    public UpdatePlaceAsyncTask(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        Place place = (Place) params[0];

        try {

            PlaceQueryBuilder qb = new PlaceQueryBuilder(selectedCity);
            URL url = new URL(qb.buildPlacesUpdateURL(place.getPlace_id()));
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(
                    connection.getOutputStream());

            osw.write(qb.setPlaceData(place));
            osw.flush();
            osw.close();
            return connection.getResponseCode() < 205;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }

    }

}
