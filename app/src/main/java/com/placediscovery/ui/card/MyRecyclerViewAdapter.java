package com.placediscovery.ui.card;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .ContentHolder> {

    private static MyClickListener myClickListener;
    private ArrayList<Content> mDataset;

    public MyRecyclerViewAdapter(ArrayList<Content> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        MyRecyclerViewAdapter.myClickListener = myClickListener;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_row, parent, false);

        ContentHolder contentHolder = new ContentHolder(view);
        return contentHolder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.dateTime.setText(mDataset.get(position).getmText2());
    }

    public void addItem(Content content, int index) {
        mDataset.add(index, content);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class ContentHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public ContentHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(Constants.LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}