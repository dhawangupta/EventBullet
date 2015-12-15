package com.placediscovery;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dhawan Gupta on 08-12-2015.
 */
public final  class Constants
{
    public static final String Varanasi="Varanasi";

    //These are going to be used for the initial point in the map when adding a place
    // first one is lattitude and second one is longitude

    public static LatLng varanasiLatLng = new LatLng(25.28,82.96);

    //TODO: add corrected lat and long and for other cities also
    public static LatLng bangaloreLatLng = new LatLng(12.96,77.56);
    public static LatLng kolkataLatLng = new LatLng(22.56,88.36);
    public static LatLng jaipurLatLng = new LatLng(26.9,75.8);

    public static final String Latitutude="LATITUDE";
    public static final String Longitude="LONGITUDE";
}
