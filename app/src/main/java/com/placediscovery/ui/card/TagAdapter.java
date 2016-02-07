package com.placediscovery.ui.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.placediscovery.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagAdapter extends RecyclerView
        .Adapter<TagAdapter
        .ViewHolder> {

    private static MyClickListener myClickListener;
    List<Content> mDataset = Collections.emptyList();

    public TagAdapter(List<Content> myDataset) {
        mDataset = myDataset;
    }

    public TagAdapter(Context context, ArrayList<Content> data) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}