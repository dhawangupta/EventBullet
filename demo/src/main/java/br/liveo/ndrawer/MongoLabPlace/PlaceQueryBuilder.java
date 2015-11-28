package br.liveo.ndrawer.MongoLabPlace;

public class PlaceQueryBuilder {

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
	 * Returns the places collection
	 * @return
	 */
	public String documentRequest()
	{
		return "places";
	}

	/**
	 * Builds a complete URL using the methods specified above
	 * @return
	 */
	public String buildContactsGetURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}


}
