package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabPlace.PlaceQueryBuilder;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserQueryBuilder;
import com.placediscovery.MongoLabUser.UserStatus;
import com.placediscovery.R;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


public class ContentActivity extends AppCompatActivity implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;   //this is imageslider used
    private RatingBar ratingBar;
    String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mDemoSlider = (SliderLayout)findViewById(R.id.contentPageImageSlider);

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

        final Place selectedPlace = places.get(imageviewId);
        String place_name = selectedPlace.getName();
        String place_content = selectedPlace.getContent();
        String image_url = selectedPlace.getImageURL();
        final double currentRating = Double.parseDouble(selectedPlace.getAverageRating());
        final int currentCount = Integer.parseInt(selectedPlace.getCount());

        TextView t1 = (TextView)findViewById(R.id.place_name);
        TextView t2 = (TextView)findViewById(R.id.place_content);
        t1.setText(place_name);
        t2.setText(place_content);

        String[] image_urls = image_url.split(",");

        if(image_urls.length<=1) {
            // following is old imageloader code
            int loader = R.drawable.loader;         //loader image
            String hd_url = image_url.substring(0, image_url.length() - 6) + ".jpg";
            // ImageLoader class instance
            ImageLoader imgLoader = new ImageLoader(getApplicationContext());
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
            imgLoader.DisplayImage(hd_url, loader, (ImageView) findViewById(R.id.contentPageImage));

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
        /*
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        */

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);

        /*
        * This is the listener for rating bar, edit it to change functionality
        * */
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                //TODO: add for rating by user and also change to suitable UI for Rating
                //if (UserStatus.isLoginStatus()) {
                    int newCount = currentCount+1;
                    double newRating = (currentRating*currentCount+rating)/newCount;
                    selectedPlace.setCount(String.valueOf(newCount));
                    selectedPlace.setAverageRating(String.valueOf(newRating));

                    UpdatePlace tsk = new UpdatePlace();
                    tsk.execute(selectedPlace);
                //}

                Toast.makeText(ContentActivity.this, "New Rating: " + rating,
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStatus.isLoginStatus()) {
                    UserStatus userStatus = new UserStatus();
                    User loggedInUser = new User(userStatus);

                    /*TODO: place_id or name of place (we have to devise a method to display saved places in SavedPlaces.java
                    * TODO: check for adding same place multiple items
                    *
                    * */
                    loggedInUser.setSavedplaces(loggedInUser.getSavedplaces() + "," + places.get(imageviewId).getPlace_id());

                    UpdateUser tsk = new UpdateUser();
                    tsk.execute(loggedInUser);

                    Toast.makeText(ContentActivity.this, "Added to List", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    /**
     * AsyncTask to update a given place
     * @author KYAZZE MICHAEL
     * @edited Dhawan Gupta
     *
     */
    final class UpdatePlace extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {
            Place place = (Place) params[0];

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

                osw.write(qb.setPlaceData(place));
                osw.flush();
                osw.close();
                if(connection.getResponseCode() <205)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            } catch (Exception e) {
                e.getMessage();
                return false;
            }

        }

    }

    /**
     * AsyncTask to update a given user
     * @author KYAZZE MICHAEL
     * @edited Dhawan Gupta
     *
     */
    final class UpdateUser extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {
            User user = (User) params[0];

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

                osw.write(qb.setUserData(user));
                osw.flush();
                osw.close();
                if(connection.getResponseCode() <205)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            } catch (Exception e) {
                e.getMessage();
                return false;
            }

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

}
