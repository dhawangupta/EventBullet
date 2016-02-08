package com.placediscovery.ui.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.R;

import java.util.Collections;
import java.util.List;

public class TagAdapter extends RecyclerView
        .Adapter<TagAdapter
        .ViewHolder> {

    private static MyClickListener myClickListener;
    List<Event> eventses;
    List<Event> events = Collections.emptyList();
    private ImageLoader imageLoader;
    private Context context;

    public TagAdapter(List<Event> myDataset, Context context) {
        events = myDataset;
        this.context = context;
    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        TagAdapter.myClickListener = myClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event eventObj = events.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(eventObj.getImageURL(), ImageLoader.getImageListener(holder.imageView,
                R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.imageView.setImageUrl(eventObj.getImageURL(), imageLoader);
        holder.textViewName.setText(eventObj.getName());
        holder.textViewRank.setText(String.valueOf(eventObj.getTimings()));
        holder.textViewRealName.setText(eventObj.getTicket());
        holder.textViewCreatedBy.setText(eventObj.getFreq());
        holder.textViewFirstAppearance.setText(eventObj.getduration());
    }



    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewRank;
        public TextView textViewRealName;
        public TextView textViewCreatedBy;
        public TextView textViewFirstAppearance;
        public TextView textViewPowers;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewRank = (TextView) itemView.findViewById(R.id.textViewRank);
            textViewRealName = (TextView) itemView.findViewById(R.id.textViewRealName);
            textViewCreatedBy = (TextView) itemView.findViewById(R.id.textViewCreatedBy);
            textViewFirstAppearance = (TextView) itemView.findViewById(R.id.textViewFirstAppearance);
            textViewPowers = (TextView) itemView.findViewById(R.id.textViewPowers);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}