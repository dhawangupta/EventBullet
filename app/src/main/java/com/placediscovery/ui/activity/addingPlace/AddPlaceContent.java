//created by ARIMIT

package com.placediscovery.ui.activity.addingPlace;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.google.android.gms.maps.model.LatLng;
import com.placediscovery.AWSClasses.Util;
import com.placediscovery.HelperClasses.Constants;
import com.placediscovery.MongoLabPlace.CreatePlaceAsyncTask;
import com.placediscovery.MongoLabPlace.Place;
import com.placediscovery.R;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

import static com.placediscovery.AWSClasses.Constants.BUCKET_NAME;


public class AddPlaceContent extends AppCompatActivity {
    // TAG for logging;
    private static final String TAG = "UploadActivity";
    public Place userAddedPlace;
    protected EditText place_title;
    protected EditText place_details;
    protected Button submit_button;
    String selected_city_for_user_places;
    private LatLng userPlaceLatLng;
    private String imageURLfromAWS = "";     //TODO:we have to find its value from AWS


    private double lat, lon;

    // The TransferUtility is the primary class for managing transfer to S3
    private TransferUtility transferUtility;

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_content);

        Intent intent = getIntent();

        lat = intent.getDoubleExtra(Constants.Latitutude, 0);
        lon = intent.getDoubleExtra(Constants.Longitude, 0);
        userPlaceLatLng=new LatLng(lat,lon);
        Toast.makeText(this,String.valueOf(lat),Toast.LENGTH_LONG).show();
        // Initializes TransferUtility, always do this before using it.
        transferUtility = Util.getTransferUtility(this);

        place_title = (EditText) findViewById(R.id.place_name);
        place_details = (EditText) findViewById(R.id.place_content);
        submit_button = (Button) findViewById(R.id.btn_submit);
        ImageView iv = (ImageView) findViewById(R.id.add_image);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Place");  //title for the toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        selected_city_for_user_places = intent.getExtras().getString("selectedCity") + "_users";

        //Creting place added by user
        userAddedPlace = new Place();

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });         //back icon added

        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= 19) {
                    // For Android versions of KitKat or later, we use a
                    // different intent to ensure
                    // we can get the file path from the returned intent URI
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                } else {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                }

                intent.setType("image/*");

                startActivityForResult(intent, 0);

//                Toast.makeText(AddPlaceContent.this, "Add Image", Toast.LENGTH_SHORT).show();

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userAddedPlace.setLatitude(String.valueOf(userPlaceLatLng.latitude));
                userAddedPlace.setLongitude(String.valueOf(userPlaceLatLng.longitude));
                userAddedPlace.setName(String.valueOf(place_title.getText()));
                userAddedPlace.setContent(String.valueOf(place_details.getText()));
                userAddedPlace.setImageURL(imageURLfromAWS);
                CreatePlaceAsyncTask tsk = new CreatePlaceAsyncTask(selected_city_for_user_places);
                tsk.execute(userAddedPlace);
                // submit place details to database
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            try {
                String path = getPath(uri);
                beginUpload(path);
            } catch (URISyntaxException e) {
                Toast.makeText(this,
                        "Unable to get the file from the given URI.  See error log for details",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable to upload file from the given uri", e);
            }
        }
    }

    /*
     * Begins to upload the file specified by the file path.
     */
    private void beginUpload(String filePath) {
        if (filePath == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file",
                    Toast.LENGTH_LONG).show();
            return;
        }
        File file = new File(filePath);
        TransferObserver observer = transferUtility.upload(BUCKET_NAME, file.getName(),
                file);

        HashMap<String, Object> map = new HashMap<>();
//        Util.fillMap(map, observer, false);
        observer.setTransferListener(new UploadListener());
    }

    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = Objects.requireNonNull(cursor).getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    /*
     * A TransferListener class that can listen to a upload task and be notified
     * when the status changes.
     */
    private class UploadListener implements TransferListener {

        // Simply updates the UI list when notified.
        @Override
        public void onError(int id, Exception e) {
            Log.e(TAG, "Error during upload: " + id, e);

        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
        }
    }
}