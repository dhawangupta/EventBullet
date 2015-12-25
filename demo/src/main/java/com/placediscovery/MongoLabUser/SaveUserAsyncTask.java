package com.placediscovery.MongoLabUser;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SaveUserAsyncTask extends AsyncTask<User, Void, Boolean> {

	@Override
	protected Boolean doInBackground(User... arg0) {
		try 
		{
			User user = arg0[0];
			
			UserQueryBuilder qb = new UserQueryBuilder();
			
			HttpClient httpClient = new DefaultHttpClient();
	        HttpPost request = new HttpPost(qb.buildUsersSaveURL());

	        StringEntity params =new StringEntity(qb.createUser(user));
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	        
	        if(response.getStatusLine().getStatusCode()<205)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		} catch (Exception e) {
			//e.getCause();
			String val = e.getMessage();
			String val2 = val;
			return false;
		}		
	}

}
