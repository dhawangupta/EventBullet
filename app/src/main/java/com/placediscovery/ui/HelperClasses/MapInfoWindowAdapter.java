package com.placediscovery.ui.HelperClasses;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.views.MapView;
import com.placediscovery.R;

/**
 * Created by ZuberSk on 07-Jan-16.
 */
public class MapInfoWindowAdapter implements MapView.InfoWindowAdapter{

    private Context mContext;
    private LayoutInflater layoutInflater;

    public MapInfoWindowAdapter(Context context)
    {

        mContext=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    /**
     * Called when an info window will be shown as a result of a marker click.
     *
     * @param marker The marker the user clicked on.
     * @return View to be shown as a info window. If null is returned the default
     * info window will be shown.
     */
    @Nullable
    @Override
    public View getInfoWindow(Marker marker) {
        return layoutInflater.inflate(R.layout.infowindow_layout,null);

    }

}
