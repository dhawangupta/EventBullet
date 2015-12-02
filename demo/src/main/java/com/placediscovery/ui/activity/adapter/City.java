package com.placediscovery.ui.activity.adapter;

/**
 * Created by ARIMIT on 18-Sep-15.
 */
import android.content.Context;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class City {

    public String name;
    public String description;
    public String imageName;


    public int getImageResourceId(Context context)
    {
        try {
            return context.getResources().getIdentifier(this.imageName, "mipmap", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
