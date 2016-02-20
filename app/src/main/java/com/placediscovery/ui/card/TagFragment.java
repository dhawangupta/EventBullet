package com.placediscovery.ui.card;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.placediscovery.HelperClasses.Config;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.R;
import com.placediscovery.ui.ClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.GestureDetector.SimpleOnGestureListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends Fragment {


    private static final String TITLE = "TagFragment";

    private RecyclerView recyclerView;
    private List<Event> events;
    private TagAdapter adapter;



    public TagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<>();

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_tag, container, false);
        rootview.setTag(TITLE);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.event_list);
        recyclerView.setHasFixedSize(true);
        getData();
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        //final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                       // loading.dismiss();

                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Event eventObj = new Event();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                eventObj.setImageURL(json.getString(Config.TAG_IMAGE_URL));
                eventObj.setName(json.getString(Config.TAG_NAME));
                eventObj.setTimings(json.getInt(Config.TAG_TIME));
                eventObj.setTicket(json.getString(Config.TAG_TICKET));
                eventObj.setCreatedby(json.getString(Config.TAG_CREATED_BY));
                eventObj.setFreq(json.getString(Config.TAG_FREQ));
                JSONArray jsonArray = json.getJSONArray(Config.TAG_DURATION);
                String duration = (String) jsonArray.get(0);
                eventObj.setduration(duration);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            events.add(eventObj);
        }

        //Finally initializing our adapter
        adapter = new TagAdapter(events, getActivity());

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */


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
