package com.placediscovery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.placediscovery.Interface.FragmentCommunicator;
import com.placediscovery.R;
import com.placediscovery.ui.fragment.DrawerFragment;
import com.placediscovery.ui.fragment.FeedItemFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomePageActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, FragmentCommunicator, View.OnClickListener {

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
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
        setUpNavDrawer();
        setFeed();

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
            button.setTextColor(Color.BLUE);
            button.setBackgroundColor(Color.WHITE);

        }
    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FeedItemFragment(), "ONE");
//        adapter.addFragment(new FeedItemFragment(), "TWO");
//        adapter.addFragment(new FeedItemFragment(), "THREE");
//
//        viewPager.setAdapter(adapter);
//    }


    private void setFeed() {
        FeedItemFragment frag = new FeedItemFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.feed_container, frag, "FeedItemFragment");
        transaction.addToBackStack(null);
        transaction.commit();
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
            startActivity(new Intent(HomePageActivity.this, MapsActivity.class));
        }
        return true;
    }

    @Override
    public void sendObjectFromFragment(Object o) {
        MapsActivity.setEvents(o);
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
        buttons.get(i).setTextColor(Color.WHITE);
        buttons.get(i).setBackgroundColor(Color.BLUE);
    }

    private void removeColors() {
        for (Button button : buttons) {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.BLUE);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
