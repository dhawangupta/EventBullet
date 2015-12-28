package com.placediscovery.ui.activity.adapter;

/**
 * Created by ARIMIT on 18-Sep-15.
 */
import com.placediscovery.Constants;

import java.util.ArrayList;
import java.util.List;

public class CityManager {

    private static String[] cityArray = Constants.cityArray;
    private static String loremIpsum = "Description";

    private static CityManager mInstance;
    private List<City> cities;

    public static CityManager getInstance() {
        if (mInstance == null) {
            mInstance = new CityManager();
        }

        return mInstance;
    }

    public List<City> getCities() {
        if (cities == null) {
            cities = new ArrayList<City>();

            for (String cityName : cityArray) {
                City city = new City();
                city.name = cityName;
                city.description = loremIpsum;
                city.imageName = cityName.replaceAll("\\s+","").toLowerCase();
                cities.add(city);
            }
        }

        return  cities;
    }

}
