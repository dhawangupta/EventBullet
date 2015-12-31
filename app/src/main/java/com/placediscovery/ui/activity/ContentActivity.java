package com.placediscovery.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.placediscovery.ImageLoader.ImageLoader;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserQueryBuilder;
import com.placediscovery.MongoLabUser.UserStatus;
import com.placediscovery.R;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //Following is the upper toolbar code which is not needed for now.
        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My title");     //title for the toolbar
        */

        Intent intent = getIntent();
        final int imageviewId = intent.getExtras().getInt("imageviewId");
        final ArrayList<Place> places = (ArrayList<Place>) intent.getExtras().getSerializable("placesObject");

        String place_name = places.get(imageviewId).getName();
        String place_content = places.get(imageviewId).getContent();
        TextView t1 = (TextView)findViewById(R.id.place_name);
        TextView t2 = (TextView)findViewById(R.id.place_content);
        t1.setText(place_name);
        t2.setText(place_content);

        int loader = R.drawable.loader;         //loader image
        // Image url
        String image_url = places.get(imageviewId).getImageURL();
        String hd_url = image_url.substring(0,image_url.length()-6)+".jpg";
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(hd_url, loader, (ImageView)findViewById(R.id.contentPageImage));


        //some toolbar code
        /*
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        */

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserStatus.isLoginStatus()){
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
                }
                else {
                    Toast.makeText(ContentActivity.this, "Please login first", Toast.LENGTH_LONG).show();
                }
            }

        });

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
                return connection.getResponseCode() < 205;

            } catch (Exception e) {
                e.getMessage();
                return false;

            }

        }

    }
}
