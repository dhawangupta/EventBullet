package com.placediscovery.MongoLabUser;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateUserAsyncTask extends AsyncTask<User, Void, Boolean> {

	@Override
	protected Boolean doInBackground(User... arg0) {
        User user = arg0[0];
//		try
//		{
//
//
//			UserQueryBuilder qb = new UserQueryBuilder();
//
//			HttpClient httpClient = new DefaultHttpClient();
//	        HttpPost request = new HttpPost(qb.buildUsersSaveURL());
//
//	        StringEntity params =new StringEntity(qb.createUser(user));
//	        request.addHeader("content-type", "application/json");
//	        request.setEntity(params);
//	        HttpResponse response = httpClient.execute(request);
//
//	        if(response.getStatusLine().getStatusCode()<205)
//	        {
//	        	return true;
//	        }
//	        else
//	        {
//	        	return false;
//	        }
//		} catch (Exception e) {
//			//e.getCause();
//			String val = e.getMessage();
//			String val2 = val;
//			return false;
//		}


        UserQueryBuilder qb = new UserQueryBuilder();
        String postParameter= qb.createUser(user);

        try {
            URL url = new URL(qb.buildUsersSaveURL());
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.connect();
            OutputStream os=null;
            os = new BufferedOutputStream(
                    urlConnection.getOutputStream());
            os.write(postParameter.getBytes());
            os.flush();
            if(os!=null )
            {
                os.close();
            }
            return urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

}
