package com.placediscovery.MongoLabPlace;

import android.os.AsyncTask;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.placediscovery.ui.HelperMethods;

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
public class GetPlacesAsyncTask extends AsyncTask<Place, Void, ArrayList<Place>> {

	static String server_output = null;
	static String temp_output = null;
	String selectedCity;

	public GetPlacesAsyncTask(String selectedCity){
		this.selectedCity = selectedCity;
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
				places.add(temp);

			}
			HelperMethods.saveObjectToCache("saved_"+selectedCity, places);
		}catch (Exception e) {
			e.getMessage();
		}

		return places;
	}
}
