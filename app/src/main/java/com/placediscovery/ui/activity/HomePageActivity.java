package com.placediscovery.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.placediscovery.R;
import com.placediscovery.ui.fragment.FragmentDrawer;

public class HomePageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setUpNavDrawer();
    }

    private void setUpNavDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentDrawer drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_home_nav_draw);
        drawerFragment.setUp(R.id.fragment_home_nav_draw, drawerLayout, toolbar);


    }

//    public void onDrawerItemClicked(int index) {
//        mPager.setCurrentItem(index);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
}


//
//package com.placediscovery.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Toast;
//
//import com.placediscovery.MongoLabUser.UserStatus;
//import com.placediscovery.R;
//import com.placediscovery.ui.activity.addingPlace.AddPlaceSelectCity;
//import com.placediscovery.ui.fragment.MainFragment;
//import com.placediscovery.ui.fragment.ViewPagerFragment;
//
//import br.liveo.Model.HelpLiveo;
//import br.liveo.interfaces.OnItemClickListener;
//import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
//import br.liveo.navigationliveo.NavigationLiveo;
//
//public class HomePagActivity extends NavigationLiveo implements OnItemClickListener {
//
//    private HelpLiveo mHelpLiveo;
//    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
//        @Override
//        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
//        }
//    };
//    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(getApplicationContext(), "Add photo", Toast.LENGTH_SHORT).show();
//            closeDrawer();
//        }
//    };
//
//    @Override
//    public void onInt(Bundle savedInstanceState) {
//
//        // User Information
//        if (UserStatus.LoginStatus) {
//            UserStatus userStatus = new UserStatus();
//            this.userName.setText(UserStatus.getName());
//            this.userEmail.setText(UserStatus.getEmail());
//        } else {
//            this.userName.setText("Please Log In");
//            this.userEmail.setText("");
//            this.userName.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // Start the Signup activity
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//
//                }
//            });
//        }
//        this.userPhoto.setImageResource(R.drawable.user_image);
//        this.userBackground.setImageResource(R.drawable.ic_user_background_first);
//
//        // Creating items navigation
//        mHelpLiveo = new HelpLiveo();
//        mHelpLiveo.add(getString(R.string.choose_city), R.mipmap.ic_send);
//        mHelpLiveo.add(getString(R.string.add_place), R.mipmap.ic_send_black_24dp);
//        mHelpLiveo.add(getString(R.string.saved_place), R.mipmap.ic_delete_black_24dp);
//        mHelpLiveo.addSeparator(); // Item separator
//        mHelpLiveo.add(getString(R.string.home), R.mipmap.ic_home);
//        mHelpLiveo.add(getString(R.string.settings), R.mipmap.ic_settings_black_24dp);
//        mHelpLiveo.add("Log out", R.mipmap.icon_logout);
//
//        with(this).startingPosition(2) //Starting position in the list
//                .addAllHelpItem(mHelpLiveo.getHelp())
//
//                        //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
//                        //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
//                        //.colorItemDefault(R.color.blue_colorPrimary) //Inform the standard color name, icon and counter
//                        //.colorItemSelected(R.color.purple_colorPrimary) //State the name of the color, icon and meter when it is selected
//                        //.backgroundList(R.color.black_light) //Inform the list of background color
//                        //.colorLineSeparator(R.color.transparent) //Inform the color of the subheader line
//
//                        //{optional} - SubHeader Customization
//                .colorItemSelected(R.color.blue_colorPrimary)
//                .colorNameSubHeader(R.color.blue_colorPrimary)
//                        //.colorLineSeparator(R.color.blue_colorPrimary)
//
//                        //.footerItem(R.string.settings, R.mipmap.ic_settings_black_24dp)
//
//                        //{optional} - Footer Customization
//                        //.footerNameColor(R.color.blue_colorPrimary)
//                        //.footerIconColor(R.color.blue_colorPrimary)
//                        //.footerBackground(R.color.white)
//
//                .setOnClickUser(onClickPhoto)
//                .setOnPrepareOptionsMenu(onPrepare)
//                        //.setOnClickFooter(onClickFooter)
//                .build();
//
//        int position = this.getCurrentPosition();
//        //this.setElevationToolBar(position != 4 ? 15 : 0);
//    }
//
//    @Override
//    public void onItemClick(int position) {
//        Fragment mFragment;
//        FragmentManager mFragmentManager = getSupportFragmentManager();
//
//        switch (position) {
//            case 0:
//                startActivity(new Intent(this, ChooseCity.class));
//                return;
//
//            case 1:
//                startActivity(new Intent(this, AddPlaceSelectCity.class));
//                return;
//
//            case 2:
//                mFragment = new ViewPagerFragment();
//                break;
//
//            case 6: {
//                this.userName.setText("Please Log In");
//                this.userEmail.setText("");
//                this.userName.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // Start the Signup activity
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
//
//                    }
//                });
//                mFragment = new ViewPagerFragment();
//                break;
//            }
//            default:
//                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
//                break;
//        }
//
//        if (mFragment != null) {
//            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
//        }
//
//        setElevationToolBar(position != 2 ? 15 : 0);
//    }
//
//
////    private View.OnClickListener onClickFooter = new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
////            closeDrawer();
////        }
////    };
//}
//
