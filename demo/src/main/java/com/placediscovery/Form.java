package com.placediscovery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

public class Form extends AppCompatActivity {

    double lat;
    double lon;
    TextView tv;
    EditText addPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent intent=getIntent();
        lat=intent.getDoubleExtra(Constants.Latitutude,0);
        lon=intent.getDoubleExtra(Constants.Longitude,0);
        tv=(TextView)findViewById(R.id.tv);
        tv.setText(String.valueOf(lat));
        addPlace=(EditText)findViewById(R.id.place);
        sendPlace();
    }

    private void sendPlace() {
        String place=addPlace.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
