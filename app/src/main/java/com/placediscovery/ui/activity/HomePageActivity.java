package com.placediscovery.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.placediscovery.Interface.FragmentCommunicator;
import com.placediscovery.R;
import com.placediscovery.ui.fragment.DrawerFragment;
import com.placediscovery.ui.fragment.FeedItemFragment;
import com.placediscovery.ui.fragment.MapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class HomePageActivity extends AppCompatActivity implements
        Toolbar.OnMenuItemClickListener, FragmentCommunicator, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private List<Button> buttons;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home_page_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        buttons = new ArrayList<>();
        initializeButtons();
        setUpNavDrawer();



    }

    private void initializeButtons() {
        buttons.add((Button) findViewById(R.id.button1));
        buttons.add((Button) findViewById(R.id.button2));
        buttons.add((Button) findViewById(R.id.button3));
        buttons.add((Button) findViewById(R.id.button4));
        buttons.add((Button) findViewById(R.id.button5));
        buttons.add((Button) findViewById(R.id.button6));
        for (Button button : buttons) {
            button.setOnClickListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                button.setTextColor(HomePageActivity.this.getColor(R.color.primary));
            } else {
                button.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.primary));
            }
            button.setBackgroundColor(Color.WHITE);

        }
    }


    private void setUpNavDrawer() {

        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home_nav_draw);
        drawerFragment.setUp(R.id.fragment_home_nav_draw, drawerLayout, toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_login) {
            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
        }
        if (item.getItemId() == R.id.map_icon) {
//            startActivity(new Intent(HomePageActivity.this, MapsActivity.class));
            MapFragment fragment = new MapFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.feed_container, fragment, "MapFragment");
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (item.getItemId() == R.id.home_icon) {
//            FragmentManager fm = getSupportFragmentManager();
//            Toast.makeText(this, String.valueOf(fm.getBackStackEntryCount()),Toast.LENGTH_LONG).show();
//            if (fm.getBackStackEntryCount() > 0) {
//                fm.popBackStack();
//            }
            onBackPressed();
        }


        return true;
    }

    @Override
    public void sendObjectFromFragment(Object o) {
        MapFragment.setEvents(o);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                removeColors();
                setColor(0);
                break;
            }
            case R.id.button2: {
                removeColors();
                setColor(1);
                break;
            }
            case R.id.button3: {
                removeColors();
                setColor(2);
                break;
            }
            case R.id.button4: {
                removeColors();
                setColor(3);
                break;
            }
            case R.id.button5: {
                removeColors();
                setColor(4);
                break;
            }
            case R.id.button6: {
                removeColors();
                setColor(5);
                break;
            }
        }
    }

    private void setColor(int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buttons.get(i).setBackgroundColor(HomePageActivity.this.getColor(R.color.primary));
        } else {
            buttons.get(i).setBackgroundColor(ContextCompat.getColor(HomePageActivity.this, R.color.primary));
        }
        buttons.get(i).setTextColor(Color.WHITE);

    }

    private void removeColors() {
        for (Button button : buttons) {
            button.setBackgroundColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                button.setTextColor(HomePageActivity.this.getColor(R.color.primary));
            } else {
                button.setTextColor(ContextCompat.getColor(HomePageActivity.this, R.color.primary));
            }
        }
    }

}
