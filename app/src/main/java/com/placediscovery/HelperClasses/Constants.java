package com.placediscovery.HelperClasses;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dhawan Gupta on 08-12-2015.
 */
public final class Constants {
    public static final String Varanasi = "Varanasi";
    public static final String Bengaluru = "Bengaluru";
    public static final String Jaipur = "Jaipur";
    public static final String Kolkata = "Kolkata";
    public static final String Mumbai = "Mumbai";
    public static final String NewDelhi = "NewDelhi";
    public static final String Chennai = "Chennai";
    public static final String Agra = "Agra";
    public static final String Hyderabad = "Hyderabad";

    public final static String[] cityArray = {Varanasi, Agra, Kolkata, Mumbai, NewDelhi, Chennai, Bengaluru, Jaipur, Hyderabad};

    public static final String selectedCityLat = "selectedCityLat";
    public static final String selectedCityLon = "selectedCityLon";

    //These are going to be used for the initial point in the map when adding a place
    // first one is lattitude and second one is longitude
    public static final String Latitutude = "LATITUDE";
    public static final String Longitude = "LONGITUDE";
    //MapsActivity Class
    public static final String TAG_LOCATION = "My Location";
    public static final String TAG_FILTER = "Filters";
    public static final String TAG_NOTYETDECIDED = "Not yet Decided";
    //DrawerFragment
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "userlearnedrawer";
    public static final String LOG_TAG = "TagAdapter";
    public static LatLng varanasiLatLng = new LatLng(25.28, 82.96);
    public static LatLng bengaluruLatLng = new LatLng(12.96, 77.56);
    public static LatLng kolkataLatLng = new LatLng(22.56, 88.36);
    public static LatLng jaipurLatLng = new LatLng(26.9, 75.8);
    public static LatLng mumbaiLatLng = new LatLng(18.98, 72.83);
    public static LatLng newdelhiLatLng = new LatLng(28.61, 77.21);
    public static LatLng chennaiLatLng = new LatLng(13.08, 80.27);
    //TODO:update Agra and Hyderabad lat long
    public static LatLng agraLatLng = new LatLng(13.08, 80.27);
    public static LatLng hyderabadLatLng = new LatLng(13.08, 80.27);
}
