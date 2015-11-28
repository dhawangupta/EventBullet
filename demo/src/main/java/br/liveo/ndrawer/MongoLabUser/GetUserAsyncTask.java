package br.liveo.ndrawer.MongoLabUser;

import android.os.AsyncTask;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Async Task to retrieve your stored contacts from mongolab
 * @author KYAZZE MICHAEL
 *
 */
public class GetUserAsyncTask extends AsyncTask<User, Void, ArrayList<User>> {
	static String server_output = null;
	static String temp_output = null;

	@Override
	protected ArrayList<User> doInBackground(User... arg0) {
		
		ArrayList<User> users = new ArrayList<User>();
		try 
		{			
			
			UserQueryBuilder qb = new UserQueryBuilder();
	        URL url = new URL(qb.buildUsersGetURL());
	        HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
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
			BasicDBList contacts = (BasicDBList) dbObj.get("artificial_basicdb_list");
			
		  for (Object obj : contacts) {
			DBObject userObj = (DBObject) obj;

			User temp = new User();
			temp.setUser_id(userObj.get("_id").toString());
			temp.setName(userObj.get("name").toString());
			temp.setEmail(userObj.get("email").toString());
			temp.setPassword(userObj.get("password").toString());

			users.add(temp);
			
			}
		
		}catch (Exception e) {
			e.getMessage();
		}
		
		return users;
	}
}
