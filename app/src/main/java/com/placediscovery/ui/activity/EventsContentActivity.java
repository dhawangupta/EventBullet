package com.placediscovery.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabPlace.PlaceQueryBuilder;
import com.placediscovery.MongoLabPlace.UpdatePlaceAsyncTask;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserQueryBuilder;
import com.placediscovery.MongoLabUser.UserStatus;
import com.placediscovery.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//import com.placediscovery.ImageLoader.ImageLoader;


public class EventsContentActivity extends AppCompatActivity implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    String selectedCity;
    User loggedInUser;
    float userRating;
    EditText reviewField;
    Place selectedPlace;
    ProgressBar progressBarFooter;
    private SliderLayout mDemoSlider;   //this is imageslider used
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_content);

        final UserStatus userStatus = new UserStatus();
        loggedInUser = new User(userStatus);

        mDemoSlider = (SliderLayout) findViewById(R.id.eventContentPageImageSlider);

        //Following is the upper toolbar code which is not needed for now.
        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My title");     //title for the toolbar
        */

        Intent intent = getIntent();
        final int imageviewId = intent.getExtras().getInt("imageviewId");
        final ArrayList<Place> places = (ArrayList<Place>) intent.getExtras()
                .getSerializable("placesObject");
        selectedCity = intent.getExtras().getString("selectedCity");

        selectedPlace = places.get(imageviewId);
        String place_name = selectedPlace.getName();
        String place_content = selectedPlace.getContent();
        String image_url = selectedPlace.getImageURL();
        final double currentRating = Double.parseDouble(selectedPlace.getAverageRating());
        final int currentCount = Integer.parseInt(selectedPlace.getCount());
        String timings = selectedPlace.getTimings();
        String ticket = selectedPlace.getTicket();
        String bestTime = selectedPlace.getBestTime();

        TextView t1 = (TextView) findViewById(R.id.event_name);
        TextView t2 = (TextView) findViewById(R.id.event_currentratingtext);
        TextView countText = (TextView) findViewById(R.id.event_rating_count);
        final TextView rateThis = (TextView) findViewById(R.id.event_rating_ratethis);
        TextView t3 = (TextView) findViewById(R.id.event_content);
        LinearLayout timingsLayout = (LinearLayout) findViewById(R.id.event_timings);
        TextView timingsValue = (TextView) findViewById(R.id.event_timingsValue);
        LinearLayout typeLayout = (LinearLayout) findViewById(R.id.event_type);
        TextView typeValue = (TextView) findViewById(R.id.event_typeValue);
        LinearLayout ticketLayout = (LinearLayout) findViewById(R.id.event_ticket);
        TextView ticketValue = (TextView) findViewById(R.id.event_ticketValue);
        LinearLayout freqLayout = (LinearLayout) findViewById(R.id.event_freq);
        TextView freqValue = (TextView) findViewById(R.id.event_freqValue);
        LinearLayout durationLayout = (LinearLayout) findViewById(R.id.event_duration);
        TextView durationValue = (TextView) findViewById(R.id.event_durationValue);
        reviewField = (EditText) findViewById(R.id.event_reviewTextField);
        Button reviewSubmitBtn = (Button) findViewById(R.id.event_reviewBtn);


        t1.setText(place_name);
        t2.setText(currentRating + "/5");
        t3.setText(Html.fromHtml(place_content));
        countText.setText("(" + currentCount + ")");

        if (timings.equals(""))
            timingsLayout.setVisibility(LinearLayout.GONE);
        else
            timingsValue.setText(" " + timings);

        if (ticket.equals(""))
            ticketLayout.setVisibility(LinearLayout.GONE);
        else
            ticketValue.setText(" " + ticket);



        rateThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserStatus.isLoginStatus()) {
                    Dialog ratingDialog = new Dialog(EventsContentActivity.this);
                    ratingDialog.setContentView(R.layout.dialog_ratingplace);
                    ratingDialog.setCancelable(true);

                    ratingBar = (RatingBar) ratingDialog.findViewById(R.id.ratingbar);
                    /*
                    * This is the listener for rating bar, edit it to change functionality
                    * */
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                            //TODO: add for rating by user and also change to suitable UI for Rating
                            userRating = rating;
                            int newCount = currentCount + 1;
                            double newRating = (currentRating * currentCount + rating) / newCount;
                            selectedPlace.setCount(String.valueOf(newCount));
                            selectedPlace.setAverageRating(String.valueOf(newRating));

                            Toast.makeText(EventsContentActivity.this, "New Rating: " + rating,
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                    Button submitBtn = (Button) ratingDialog.findViewById(R.id.rating_dialog_button);
                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UpdatePlaceAsyncTask tsk = new UpdatePlaceAsyncTask(selectedCity);
                            tsk.execute(selectedPlace);

                            UpdateUserRatingAsyncTask task = new UpdateUserRatingAsyncTask();
                            task.execute(loggedInUser, selectedPlace.getPlace_id(), Float.toString(userRating));
                        }
                    });

                    ratingDialog.show();

                } else {
                    Toast.makeText(EventsContentActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }

        });
        String[] image_urls = image_url.split(",");

        if (image_urls.length <= 1) {
            // following is old imageloader code
            int loader = R.drawable.loader;         //loader image
            String hd_url = image_url.substring(0, image_url.length() - 6) + ".jpg";
            // ImageLoader class instance
//            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
//            imgLoader.DisplayImage(hd_url, loader, (ImageView) findViewById(R.id.contentPageImage));

        } else {


            for (String url : image_urls) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(url.substring(0, url.length() - 6) + ".jpg")      //for higher quality '_n' was removed from url
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //when you want to add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);       //replace "Stack" by other transformers to implement different kind of slider animations
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }

        //some toolbar code
//        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(topToolBar);

//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserStatus.isLoginStatus()) {
//
//                    /*TODO: place_id or name of place (we have to devise a method to display saved places in SavedPlaces.java
//                    * TODO: check for adding same place multiple items
//                    *
//                    * */
//                    loggedInUser.setSavedplaces(loggedInUser.getSavedplaces() + "," + places.get(imageviewId).getPlace_id());
//
//                    UpdateUserAsyncTask tsk = new UpdateUserAsyncTask();
//                    tsk.execute(loggedInUser);
//
//                    Toast.makeText(EventsContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(EventsContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        });

//        try {
//            setUpReviews();
//        }catch (Exception e){}

        final String review = reviewField.getText().toString();
        reviewSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStatus.LoginStatus) {
                    UpdatePlaceReviewAsyncTask task = new UpdatePlaceReviewAsyncTask();
                    task.execute(selectedPlace, loggedInUser.getUser_id(), review);
                } else
                    Toast.makeText(EventsContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
            }
        });

    }

    void setUpReviews() {

        BasicDBObject[] reviewsObject = selectedPlace.getReviews();
        //dynamically creating textviews for reviews
        // Inflate the footer
        View footer = getLayoutInflater().inflate(
                R.layout.footer_progress, null);
        // Find the progressbar within footer
        progressBarFooter = (ProgressBar)
                footer.findViewById(R.id.pbFooterLoading);

        ListView reviewsList = (ListView) findViewById(R.id.reviewsList);
        reviewsList.addFooterView(progressBarFooter);
        TextView[] review_user_names = new TextView[reviewsObject.length];
        TextView[] reviews_values = new TextView[reviewsObject.length];
        LinearLayout[] reviews = new LinearLayout[reviewsObject.length];
        for (int j = 0; j < reviewsObject.length; j++) {
            DBObject reviewObj = reviewsObject[j];
            String user_id = reviewObj.get("user_id").toString();
            String review = reviewObj.get("review").toString();

            GetUserNameAsyncTask task = new GetUserNameAsyncTask();
            String user_name = null;
            try {
                user_name = task.execute(user_id).get().getName();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            reviews[j] = new LinearLayout(this);
            reviews[j].setOrientation(LinearLayout.VERTICAL);
            review_user_names[j] = new TextView(this);
            reviews_values[j] = new TextView(this);

            review_user_names[j].setText(user_name);
            reviews_values[j].setText(review);
            reviews[j].addView(review_user_names[j]);
            reviews[j].addView(reviews_values[j]);

            reviewsList.addView(reviews[j]);

        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    class UpdateUserRatingAsyncTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            User user = (User) params[0];
            String place_id = (String) params[1];
            String rating = (String) params[2];

            try {

                UserQueryBuilder qb = new UserQueryBuilder();
                URL url = new URL(qb.buildUsersUpdateURL(user.getUser_id()));
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.setRequestProperty("Accept", "application/json");

                OutputStreamWriter osw = new OutputStreamWriter(
                        connection.getOutputStream());

                osw.write(qb.addNewRatingbyUser(place_id, rating));
                osw.flush();
                osw.close();
                return connection.getResponseCode() < 205;
            } catch (Exception e) {
                e.getMessage();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(EventsContentActivity.this, "Rating submitted successfully", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(EventsContentActivity.this, "rating failed to submit!!", Toast.LENGTH_LONG).show();
        }
    }

    class UpdatePlaceReviewAsyncTask extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            Place place = (Place) params[0];
            String user_id = (String) params[1];
            String review = (String) params[2];

            try {

                PlaceQueryBuilder qb = new PlaceQueryBuilder(selectedCity);
                URL url = new URL(qb.buildPlacesUpdateURL(place.getPlace_id()));
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.setRequestProperty("Accept", "application/json");

                OutputStreamWriter osw = new OutputStreamWriter(
                        connection.getOutputStream());

                osw.write(qb.addReview(user_id, review));
                osw.flush();
                osw.close();
                return connection.getResponseCode() < 205;
            } catch (Exception e) {
                e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(EventsContentActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                reviewField.setText("");
            } else
                Toast.makeText(EventsContentActivity.this, "review failed to submit!!", Toast.LENGTH_LONG).show();
        }
    }

    class GetUserNameAsyncTask extends AsyncTask<Object, Void, User> {
        String server_output = null;
        String temp_output = null;

        @Override
        protected void onPreExecute() {
            progressBarFooter.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected User doInBackground(Object... arg0) {

            String user_id = (String) arg0[0];
            User user = new User();  //user according to the user_id
            try {

                UserQueryBuilder qb = new UserQueryBuilder();
                URL url = new URL(qb.buildUsersGetURL(user_id));
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                while ((temp_output = br.readLine()) != null) {
                    server_output = temp_output;
                }

                // create a basic db list
                String mongoarray = "{ artificial_basicdb_list: " + server_output + "}";
                Object o = com.mongodb.util.JSON.parse(mongoarray);


                DBObject dbObj = (DBObject) o;
                BasicDBList usersList = (BasicDBList) dbObj.get("artificial_basicdb_list");

                for (Object obj : usersList) {
                    DBObject userObj = (DBObject) obj;

                    User temp = new User();

                    temp.setUser_id(userObj.get("_id").toString());
                    temp.setName(userObj.get("name").toString());
//                    temp.setEmail(userObj.get("email").toString());
//                    temp.setPassword(userObj.get("password").toString());
//                    temp.setSavedplaces(userObj.get("savedplaces").toString());
//
//                    BasicDBList ratingsList = (BasicDBList) userObj.get("ratings");
//                    BasicDBObject[] ratingsArr = ratingsList.toArray(new BasicDBObject[0]);
//                    temp.setRatings(ratingsArr);

                    user = temp;
                }
            } catch (Exception e) {
                e.getMessage();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            progressBarFooter.setVisibility(View.GONE);
            super.onPostExecute(user);
        }
    }

}
