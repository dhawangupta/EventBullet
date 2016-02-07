package com.placediscovery.ui.card;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.placediscovery.R;
import com.placediscovery.ui.ClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.GestureDetector.SimpleOnGestureListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends Fragment {


    private static final String TITLE = "TagFragment";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    protected LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private View mContainer;
    private List<Content> mDataset;


    public TagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_tag, container, false);
        rootview.setTag(TITLE);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.event_list);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        TagAdapter mAdapter = new TagAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */

    private void initDataset() {
        mDataset = new ArrayList(DATASET_COUNT);
        for (int index = 0; index < 20; index++) {
            Content obj = new Content("Some Primary Text " + index,
                    "Secondary " + index);
            mDataset.add(obj);
        }

    }


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        GestureDetector gestureDetector;
        ClickListener clickListener;

        public RecyclerTouchListener(Context context, RecyclerView mRecyclerDrawer, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (view != null && clickListener != null) {
                        clickListener.onLongClick(view, recyclerView.getChildAdapterPosition(view));
                    }
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (view != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(view, rv.getChildLayoutPosition(view));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
