package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MongoLabPlace.Event;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserStatus;
import com.placediscovery.R;

public class EventsContentActivity extends AppCompatActivity implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    User loggedInUser;
    //    float userRating;
//    EditText reviewField;
    Event event;
    //    ProgressBar progressBarFooter;
    private SliderLayout mDemoSlider;   //this is imageslider used
//    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_content);

        final UserStatus userStatus = new UserStatus();
        loggedInUser = new User(userStatus);

        mDemoSlider = (SliderLayout) findViewById(R.id.eventContentPageImageSlider);
        Intent intent = getIntent();
        event = (Event) intent.getExtras().getSerializable("event");

        //Following is the upper toolbar code which is not needed for now.
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle("My title");     //title for the toolbar


//        final int imageviewId = intent.getExtras().getInt("imageviewId");
//        final ArrayList<Place> places = (ArrayList<Place>) intent.getExtras()
//                .getSerializable("placesObject");
//        selectedCity = intent.getExtras().getString("selectedCity");

//        selectedPlace = places.get(imageviewId);

        String event_name = event.getName();
        String event_content = event.getContent();
        String image_url = event.getImageURL();
//        final double currentRating = Double.parseDouble(event.getAverageRating());
//        final int currentCount = Integer.parseInt(event.getCount());
        String timings = event.getTimings();

        try {
            // Converting eventTimings into x ago format
            timings = DateUtils.formatDateTime(getApplicationContext(),Long.parseLong(timings),
                    DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_WEEKDAY);
        }catch (Exception e){}


        String ticket = event.getTicket();
        String type = event.getType();
        String duration = event.getDuration();
        String web = event.getWeb();
        String organizer = event.getOrganizer();
        String contact = event.getContact();
        String venue = event.getVenue();

        TextView t1 = (TextView) findViewById(R.id.event_name);
//        TextView t2 = (TextView) findViewById(R.id.currentratingtext);
//        TextView countText = (TextView) findViewById(R.id.count);
//        final TextView rateThis = (TextView) findViewById(R.id.ratethis);
        TextView t3 = (TextView) findViewById(R.id.event_content);
        LinearLayout timingsLayout = (LinearLayout) findViewById(R.id.event_timings);
        TextView timingsValue = (TextView) findViewById(R.id.event_timingsValue);
        LinearLayout typeLayout = (LinearLayout) findViewById(R.id.event_type);
        TextView typeValue = (TextView) findViewById(R.id.event_typeValue);
        LinearLayout ticketLayout = (LinearLayout) findViewById(R.id.event_ticket);
        TextView ticketValue = (TextView) findViewById(R.id.event_ticketValue);
        LinearLayout durationLayout = (LinearLayout) findViewById(R.id.event_duration);
        TextView durationValue = (TextView) findViewById(R.id.event_durationValue);
        LinearLayout venueLayout = (LinearLayout) findViewById(R.id.event_venue);
        TextView venueValue = (TextView) findViewById(R.id.event_venueValue);
        LinearLayout organizerLayout = (LinearLayout) findViewById(R.id.event_organizer);
        TextView organizerValue = (TextView) findViewById(R.id.event_organizerValue);
        LinearLayout webLayout = (LinearLayout) findViewById(R.id.event_web);
        TextView webValue = (TextView) findViewById(R.id.event_webValue);
        LinearLayout contactLayout = (LinearLayout) findViewById(R.id.event_contact);
        TextView contactValue = (TextView) findViewById(R.id.event_contactValue);
//        reviewField = (EditText) findViewById(R.id.event_reviewTextField);
//        Button reviewSubmitBtn = (Button) findViewById(R.id.event_reviewBtn);

        t1.setText(event_name);
//        t2.setText(currentRating + "/5");
        t3.setText(Html.fromHtml(event_content));
//        countText.setText("(" + currentCount + ")");

        if (timings.equals(""))
            timingsLayout.setVisibility(LinearLayout.GONE);
        else
            timingsValue.setText(" " + timings);
        if (ticket.equals(""))
            ticketLayout.setVisibility(LinearLayout.GONE);
        else
            ticketValue.setText(" " + ticket);
        if (type.equals(""))
            typeLayout.setVisibility(LinearLayout.GONE);
        else
            typeValue.setText(" " + type);
        if (duration.equals(""))
            durationLayout.setVisibility(LinearLayout.GONE);
        else
            durationValue.setText(" " + duration);
        if (venue.equals(""))
            venueLayout.setVisibility(LinearLayout.GONE);
        else
            venueValue.setText(" " + venue);
        if (web.equals(""))
            webLayout.setVisibility(LinearLayout.GONE);
        else
            webValue.setText(" " + web);
        if (organizer.equals(""))
            organizerLayout.setVisibility(LinearLayout.GONE);
        else
            organizerValue.setText(" " + organizer);
        if (contact.equals(""))
            contactLayout.setVisibility(LinearLayout.GONE);
        else
            contactValue.setText(" " + contact);


//        rateThis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (UserStatus.isLoginStatus()) {
//                    Dialog ratingDialog = new Dialog(ContentActivity.this);
//                    ratingDialog.setContentView(R.layout.dialog_ratingplace);
//                    ratingDialog.setCancelable(true);
//
//                    ratingBar = (RatingBar) ratingDialog.findViewById(R.id.ratingbar);
//                    /*
//                    * This is the listener for rating bar, edit it to change functionality
//                    * */
//                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                        @Override
//                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//
//                            //TODO: add for rating by user and also change to suitable UI for Rating
//                            userRating = rating;
//                            int newCount = currentCount + 1;
//                            double newRating = (currentRating * currentCount + rating) / newCount;
//                            event.setCount(String.valueOf(newCount));
//                            event.setAverageRating(String.valueOf(newRating));
//
//                            Toast.makeText(ContentActivity.this, "New Rating: " + rating,
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    Button submitBtn = (Button) ratingDialog.findViewById(R.id.rating_dialog_button);
//                    submitBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            UpdatePlaceAsyncTask tsk = new UpdatePlaceAsyncTask(selectedCity);
//                            tsk.execute(event);
//
//                            UpdateUserRatingAsyncTask task = new UpdateUserRatingAsyncTask();
//                            task.execute(loggedInUser, event.getPlace_id(), Float.toString(userRating));
//                        }
//                    });
//
//                    ratingDialog.show();
//
//                } else {
//                    Toast.makeText(ContentActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
        String[] image_urls = image_url.split(",");

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
        if(image_urls.length==1)
            mDemoSlider.stopAutoCycle();
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);       //replace "Stack" by other transformers to implement different kind of slider animations
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

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
//                    Toast.makeText(ContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        });

//        try {
//            setUpReviews();
//        }catch (Exception e){}

//        final String review = reviewField.getText().toString();
//        reviewSubmitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserStatus.LoginStatus) {
//                    UpdatePlaceReviewAsyncTask task = new UpdatePlaceReviewAsyncTask();
//                    task.execute(event, loggedInUser.getUser_id(), review);
//                } else
//                    Toast.makeText(ContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
//            }
//        });

    }

//    void setUpReviews() {
//
//        BasicDBObject[] reviewsObject = event.getReviews();
//        //dynamically creating textviews for reviews
//        // Inflate the footer
//        View footer = getLayoutInflater().inflate(
//                R.layout.footer_progress, null);
//        // Find the progressbar within footer
//        progressBarFooter = (ProgressBar)
//                footer.findViewById(R.id.pbFooterLoading);
//
//        ListView reviewsList = (ListView) findViewById(R.id.reviewsList);
//        reviewsList.addFooterView(progressBarFooter);
//        TextView[] review_user_names = new TextView[reviewsObject.length];
//        TextView[] reviews_values = new TextView[reviewsObject.length];
//        LinearLayout[] reviews = new LinearLayout[reviewsObject.length];
//        for (int j = 0; j < reviewsObject.length; j++) {
//            DBObject reviewObj = reviewsObject[j];
//            String user_id = reviewObj.get("user_id").toString();
//            String review = reviewObj.get("review").toString();
//
//            GetUserNameAsyncTask task = new GetUserNameAsyncTask();
//            String user_name = null;
//            try {
//                user_name = task.execute(user_id).get().getName();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//            reviews[j] = new LinearLayout(this);
//            reviews[j].setOrientation(LinearLayout.VERTICAL);
//            review_user_names[j] = new TextView(this);
//            reviews_values[j] = new TextView(this);
//
//            review_user_names[j].setText(user_name);
//            reviews_values[j].setText(review);
//            reviews[j].addView(review_user_names[j]);
//            reviews[j].addView(reviews_values[j]);
//
//            reviewsList.addView(reviews[j]);
//
//        }
//
//    }

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

//    class UpdateUserRatingAsyncTask extends AsyncTask<Object, Void, Boolean> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Boolean doInBackground(Object... params) {
//            User user = (User) params[0];
//            String place_id = (String) params[1];
//            String rating = (String) params[2];
//
//            try {
//
//                UserQueryBuilder qb = new UserQueryBuilder();
//                URL url = new URL(qb.buildUsersUpdateURL(user.getUser_id()));
//                HttpURLConnection connection = (HttpURLConnection) url
//                        .openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Content-Type",
//                        "application/json");
//                connection.setRequestProperty("Accept", "application/json");
//
//                OutputStreamWriter osw = new OutputStreamWriter(
//                        connection.getOutputStream());
//
//                osw.write(qb.addNewRatingbyUser(place_id, rating));
//                osw.flush();
//                osw.close();
//                return connection.getResponseCode() < 205;
//            } catch (Exception e) {
//                e.getMessage();
//                return false;
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            if (aBoolean) {
//                Toast.makeText(ContentActivity.this, "Rating submitted successfully", Toast.LENGTH_SHORT).show();
//
//            } else
//                Toast.makeText(ContentActivity.this, "rating failed to submit!!", Toast.LENGTH_LONG).show();
//        }
//    }

//    class UpdatePlaceReviewAsyncTask extends AsyncTask<Object, Void, Boolean> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Boolean doInBackground(Object... params) {
//            Place place = (Place) params[0];
//            String user_id = (String) params[1];
//            String review = (String) params[2];
//
//            try {
//
//                PlaceQueryBuilder qb = new PlaceQueryBuilder(selectedCity);
//                URL url = new URL(qb.buildPlacesUpdateURL(place.getPlace_id()));
//                HttpURLConnection connection = (HttpURLConnection) url
//                        .openConnection();
//                connection.setRequestMethod("PUT");
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Content-Type",
//                        "application/json");
//                connection.setRequestProperty("Accept", "application/json");
//
//                OutputStreamWriter osw = new OutputStreamWriter(
//                        connection.getOutputStream());
//
//                osw.write(qb.addReview(user_id, review));
//                osw.flush();
//                osw.close();
//                return connection.getResponseCode() < 205;
//            } catch (Exception e) {
//                e.getMessage();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            if (aBoolean) {
//                Toast.makeText(ContentActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
//                reviewField.setText("");
//            } else
//                Toast.makeText(ContentActivity.this, "review failed to submit!!", Toast.LENGTH_LONG).show();
//        }
//    }

//    class GetUserNameAsyncTask extends AsyncTask<Object, Void, User> {
//        String server_output = null;
//        String temp_output = null;
//
//        @Override
//        protected void onPreExecute() {
//            progressBarFooter.setVisibility(View.VISIBLE);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected User doInBackground(Object... arg0) {
//
//            String user_id = (String) arg0[0];
//            User user = new User();  //user according to the user_id
//            try {
//
//                UserQueryBuilder qb = new UserQueryBuilder();
//                URL url = new URL(qb.buildUsersGetURL(user_id));
//                HttpURLConnection conn = (HttpURLConnection) url
//                        .openConnection();
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Accept", "application/json");
//
//                if (conn.getResponseCode() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + conn.getResponseCode());
//                }
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(
//                        (conn.getInputStream())));
//
//                while ((temp_output = br.readLine()) != null) {
//                    server_output = temp_output;
//                }
//
//                // create a basic db list
//                String mongoarray = "{ artificial_basicdb_list: " + server_output + "}";
//                Object o = com.mongodb.util.JSON.parse(mongoarray);
//
//
//                DBObject dbObj = (DBObject) o;
//                BasicDBList usersList = (BasicDBList) dbObj.get("artificial_basicdb_list");
//
//                for (Object obj : usersList) {
//                    DBObject userObj = (DBObject) obj;
//
//                    User temp = new User();
//
//                    temp.setUser_id(userObj.get("_id").toString());
//                    temp.setName(userObj.get("name").toString());
////                    temp.setEmail(userObj.get("email").toString());
////                    temp.setPassword(userObj.get("password").toString());
////                    temp.setSavedplaces(userObj.get("savedplaces").toString());
////
////                    BasicDBList ratingsList = (BasicDBList) userObj.get("ratings");
////                    BasicDBObject[] ratingsArr = ratingsList.toArray(new BasicDBObject[0]);
////                    temp.setRatings(ratingsArr);
//
//                    user = temp;
//                }
//            } catch (Exception e) {
//                e.getMessage();
//            }
//            return user;
//        }
//
//        @Override
//        protected void onPostExecute(User user) {
//            progressBarFooter.setVisibility(View.GONE);
//            super.onPostExecute(user);
//        }
//    }

}
