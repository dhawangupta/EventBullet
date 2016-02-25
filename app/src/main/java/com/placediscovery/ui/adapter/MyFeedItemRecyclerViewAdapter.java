package com.placediscovery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.Network.MySingleton;
import com.placediscovery.R;
import com.placediscovery.ui.CustomViews.FeedImageView;

import java.util.List;


public class MyFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<Event> feeditems;
    private Context context;
    private ImageLoader imageLoader;

    public MyFeedItemRecyclerViewAdapter(List<Event> items, Context context) {
        feeditems = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (imageLoader == null)
            imageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.item = feeditems.get(position);
        holder.name.setText(holder.item.getName());

        // Converting timestamp into x ago format
//        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
//                Long.parseLong(holder.item.getTimeStamp()),
//                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.timing.setText(holder.item.getTimings());

        // Chcek for empty status message
        if (!TextUtils.isEmpty(holder.item.getStatus())) {
            holder.statusMsg.setText(holder.item.getStatus());
            holder.statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            holder.statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (holder.item.getEventUrl() != null) {
            holder.url.setText(Html.fromHtml("<a href=\"" + holder.item.getEventUrl() + "\">"
                    + holder.item.getEventUrl() + "</a> "));

            // Making url clickable
            holder.url.setMovementMethod(LinkMovementMethod.getInstance());
            holder.url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            holder.url.setVisibility(View.GONE);
        }

//        user profile pic
        holder.profilePic.setImageUrl(holder.item.getProfilePicUrl(), imageLoader);

        // Feed image
        if (holder.item.getFeedimageURL() != null) {

            holder.feedImageView.setImageUrl(holder.item.getFeedimageURL(), imageLoader);
            holder.feedImageView.setVisibility(View.VISIBLE);
            holder.feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            holder.feedImageView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return feeditems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View convertView;
        public Event item;
        TextView name;
        TextView timing;
        TextView statusMsg;
        TextView url;
        NetworkImageView profilePic;
        FeedImageView feedImageView;

        public ViewHolder(View view) {
            super(view);
            convertView = view;
            name = (TextView) convertView.findViewById(R.id.name);
            timing = (TextView) convertView.findViewById(R.id.timestamp);
            statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
            url = (TextView) convertView.findViewById(R.id.txtUrl);
            profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
            feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);
        }
    }
}
