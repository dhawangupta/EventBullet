package com.placediscovery.ui.card;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.placediscovery.R;
import com.placediscovery.ui.activity.ChooseCity;
import com.placediscovery.ui.activity.addingPlace.AddPlaceSelectCity;

import static android.view.GestureDetector.SimpleOnGestureListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends Fragment {

    /*
    STEPS TO HANDLE THE RECYCLER CLICK

    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener

    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked

    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events

    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.

    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event

    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event

    7 if above condition holds true, fire the click event

    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.

    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mContainer;


    public TagFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        TagAdapter mAdapter = new TagAdapter(getActivity(), getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(getActivity(), ChooseCity.class));
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case 2:
                        startActivity(new Intent(getActivity(), AddPlaceSelectCity.class));
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                }


            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

    }


    public interface ClickListener {

        void onClick(View view, int position);

        void onLongClick(View view, int position);

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
