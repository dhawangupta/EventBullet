package com.placediscovery.flickr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.placediscovery.R;

import java.io.File;

public class FlickrMain extends AppCompatActivity  {



    File fileUri;
    View.OnClickListener mPickClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivityForResult(intent, 102);
        }
    };
    View.OnClickListener mFlickrClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (fileUri == null) {
                Toast.makeText(getApplicationContext(), "Please pick photo",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            Intent intent = new Intent(getApplicationContext(),
                    FlickrjActivity.class);
            intent.putExtra("flickImagePath", fileUri.getAbsolutePath());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_main);

        Button btnFlickr = (Button) findViewById(R.id.btnFlickr);
        btnFlickr.setOnClickListener(mFlickrClickListener);

        Button btnPick = (Button) findViewById(R.id.btnPick);
        btnPick.setOnClickListener(mPickClickListener);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102) {

            if (resultCode == Activity.RESULT_OK) {
                Uri tmp_fileUri = data.getData();

                ((ImageView) findViewById(R.id.imageView1))
                        .setImageURI(tmp_fileUri);

                String selectedImagePath = getPath(tmp_fileUri);
                fileUri = new File(selectedImagePath);
                Debug.e("", "fileUri : " + fileUri.getAbsolutePath());
            }

        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };

//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
        String res = null;

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
