package com.placediscovery.ui.activity.adapter;

/**
 * Created by ARIMIT on 18-Sep-15.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import com.placediscovery.R;
import com.placediscovery.ui.activity.ViewHolderResponser;

/**
 * Created by ZuberSk on 04-Oct-15.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{

    private final WeakReference<ViewHolderResponser> mResponder;
    private List<City> cities;
    private int rowLayout;
    private Context mContext;

    public CityAdapter(List<City> cities, int rowLayout,WeakReference<ViewHolderResponser> activity,Context context) {
        this.cities = cities;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mResponder=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v,mResponder);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        City city = cities.get(i);
        viewHolder.cityName.setText(city.name);
        viewHolder.cityImage.setImageDrawable(mContext.getDrawable(city.getImageResourceId(mContext)));

    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final WeakReference<ViewHolderResponser> mResponder;
        public TextView cityName;
        public ImageView cityImage;

        public ViewHolder(View itemView,WeakReference<ViewHolderResponser> responder) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.cityName);
            cityImage = (ImageView)itemView.findViewById(R.id.cityImage);
            mResponder=responder;
            cityImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResponder.get().didClickOnView(v,getAdapterPosition());
                }
            });
            cityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResponder.get().didClickOnView(v,getAdapterPosition());
                }
            });

        }

    }
}