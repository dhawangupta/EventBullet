package com.placediscovery.MongoLabUser;

/**
 * Created by Dhawan Gupta on 31-12-2015.
 */

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsyncTask to update a given user
 * @author KYAZZE MICHAEL
 * @edited Dhawan Gupta
 *
 */
public class UpdateUserAsyncTask extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... params) {
        User user = (User) params[0];

        try {

            UserQueryBuilder qb = new UserQueryBuilder();
            URL url = new URL(qb.buildUsersUpdateURL(user.getUser_id()));
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(
                    connection.getOutputStream());

            osw.write(qb.setUserData(user));
            osw.flush();
            osw.close();
            return connection.getResponseCode() < 205;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }

    }

}
