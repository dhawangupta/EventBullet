package com.placediscovery.MongoLabUser;

public class UserQueryBuilder {
	
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
		return "RXHr6P_daeHiIoDGIZ8guPJGJVfR_QHE";
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
	 * Returns the users collection
	 * @return
	 */
	public String documentRequest()
	{
		return "users";
	}
	
	/**
	 * Builds a complete URL using the methods specified above
	 * @return
	 */
	public String buildUsersSaveURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}
	
	/**
	 * This method is identical to the one above. 
	 * @return
	 */
	public String buildUsersGetURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}

	/**
	 * Get a Mongodb document that corresponds to the given object id
	 * @param doc_id
	 * @return
	 */
	public String buildUsersGetURL(String doc_id)
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
	}

	/**
	 * Get a Mongodb document that corresponds to the given object id
	 * @param doc_id
	 * @return
	 */
	public String buildUsersUpdateURL(String doc_id)
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
	}
	
	
	/**
	 * Formats the contact details for MongoHLab Posting
	 * @param user: Details of the person
	 * @return
	 */
	public String createUser(User user)
	{
		return String
		.format("{\"name\": \"%s\"," +
                        "\"email\": \"%s\", " +
                        "\"password\": \"%s\"" +
                        "\"savedplaces\":\"\", " +
                        "\"ratings\":[]}",
				user.name, user.email, user.password);
	}

	/**
	 * Update a given user record
	 * @param user
	 * @return
	 */
	public String setUserData(User user) {
		return String.format("{ \"$set\" : " +
                        "{\"name\" : \"%s\", " +
                        "\"email\" : \"%s\", " +
                        "\"password\" : \"%s\", " +
                        "\"savedplaces\" : \"%s\"}" +
                        "}",
                user.getName(),
                user.getEmail(), user.getPassword(),
                user.getSavedplaces());
	}

	public String addNewRatingbyUser(String place_id, String rating){
        return String.format("{\"$push\" : " +
                        "{\"ratings\":" +
                        "{\"place_id\":\"%s\",\"rating\":\"%s\"}" +
                        "}" +
                        "}",
                place_id, rating);
    }
}
