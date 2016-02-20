package com.placediscovery.MongoLabPlace;

public class PlaceQueryBuilder {

	String selectedCity;

	public PlaceQueryBuilder(String selectedCity){
		this.selectedCity = selectedCity;
	}

	/**
	 * Specify your database name here
	 * @return
	 */
	public String getDatabaseName() {
		return "travelexplorer";
	}

	/**
	 * Specify your MongoLab API here
	 * @return
	 */
	public String getApiKey() {
		return "xA8E78wu30cfP3fdc3PJBvHhu8KYRbS-";
	}

	/**
	 * This constructs the URL that allows you to manage your database, 
	 * collections and documents
	 * @return
	 */
	public String getBaseUrl()
	{
		return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
	}

	/**
	 * Completes the formating of your URL and adds your API key at the end
	 * @return
	 */
	public String docApiKeyUrl()
	{
		return "?apiKey="+getApiKey();
	}

	/**
	 * Get a specified document
	 * @param docid
	 * @return
	 */
	public String docApiKeyUrl(String docid)
	{
		return "/"+docid+"?apiKey="+getApiKey();
	}

	/**
	 * Returns the places collection
	 * @return
	 */
	public String documentRequest()
	{
		return selectedCity;    //this corresponds to the city particular city  collection in the database, it is defined by ChooseCity.java
	}

	/**
	 * Builds a complete URL using the methods specified above
	 * @return
	 */
	public String buildPlacesSaveURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}

	/**
	 * This method is identical to the one above.
	 * @return
	 */
	public String buildPlacesGetURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}

	/**
	 * Get a Mongodb document that corresponds to the given object id
	 * @param doc_id
	 * @return
	 */
	public String buildPlacesUpdateURL(String doc_id)
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
	}

	/**
	 * Formats the place details for MongoHLab Posting
	 * @param place: Details of the place
	 * @return
	 */
	public String createPlace(Place place)
	{
		return String.format("{\"name\": \"%s\", " +
						"\"latitude\": \"%s\", " +
						"\"longitude\": \"%s\", " +
						"\"filter\" : \"%s\", " +
						"\"imageURL\" : \"%s\", " +
						"\"content\" : \"%s\", " +
						"\"averageRating\" : \"%s\", " +
						"\"count\" : \"%s\"," +
						"\"timings\":\"%s\"," +
						"\"ticket\":\"%s\"," +
						"\"bestTime\":\"%s\"," +
						"\"events\":[],"+
						"\"reviews\":[]"+
						"}",
				place.getName(),place.getLatitude(), place.getLongitude(), place.getFilter(),
				place.getImageURL(), place.getContent(), place.getAverageRating(), place.getCount(),
				place.getTimings(),place.getTicket(),place.getBestTime());
	}

    /**
     * Update a given place record
     * @param place
     * @return
     */
    public String setPlaceData(Place place) {
        return String.format("{ \"$set\" : " +
						"{\"name\": \"%s\", " +
						"\"latitude\": \"%s\", " +
						"\"longitude\": \"%s\", " +
						"\"filter\" : \"%s\", " +
						"\"imageURL\" : \"%s\", " +
						"\"content\" : \"%s\", " +
						"\"averageRating\" : \"%s\", " +
						"\"count\" : \"%s\"," +
						"\"timings\":\"%s\"," +
						"\"ticket\":\"%s\"," +
						"\"bestTime\":\"%s\"," +
						" } }",
				place.getName(),place.getLatitude(), place.getLongitude(), place.getFilter(),
				place.getImageURL(), place.getContent(), place.getAverageRating(), place.getCount(),
				place.getTimings(),place.getTicket(),place.getBestTime());
    }

    public String addReview(String user_id, String review){
        return String.format("{\"$push\" : " +
                        "{\"reviews\":" +
                        "{\"user_id\":\"%s\",\"review\":\"%s\"}" +
                        "}" +
                        "}",
                user_id, review);
    }
}
