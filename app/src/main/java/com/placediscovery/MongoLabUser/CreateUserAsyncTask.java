package com.placediscovery.MongoLabUser;

import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateUserAsyncTask extends AsyncTask<User, Void, Boolean> {

	@Override
	protected Boolean doInBackground(User... arg0) {
        try
        {
            User user = arg0[0];

            UserQueryBuilder qb = new UserQueryBuilder();
            URL url = new URL(qb.buildUsersSaveURL());
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

            osw.write(qb.createUser(user));
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

