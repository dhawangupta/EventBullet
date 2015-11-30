
package br.liveo.ndrawer.ui.activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import br.liveo.ndrawer.R;
import br.liveo.ndrawer.ui.fragment.AFragment;
import br.liveo.ndrawer.ui.fragment.MainFragment;
import br.liveo.ndrawer.ui.fragment.ViewPagerFragment;

public class HomePage extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;

    @Override
    public void onInt(Bundle savedInstanceState) {

        // User Information

        if(Name_value!=null){
        this.userName.setText(Name_value);
        this.userEmail.setText(Email_value);
        }else{
        this.userName.setText("Abc");
        this.userEmail.setText("abc@abc.com");}
        this.userPhoto.setImageResource(R.drawable.arimit_icon);
        this.userBackground.setImageResource(R.drawable.ic_user_background_first);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.add_money), R.mipmap.ic_send);
        mHelpLiveo.add(getString(R.string.recharge), R.mipmap.ic_send_black_24dp);
        mHelpLiveo.add(getString(R.string.home), R.mipmap.ic_home);
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add(getString(R.string.others), R.mipmap.ic_delete_black_24dp);
        mHelpLiveo.add(getString(R.string.settings), R.mipmap.ic_settings_black_24dp);

        with(this).startingPosition(2) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
                //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                //.colorItemDefault(R.color.nliveo_blue_colorPrimary) //Inform the standard color name, icon and counter
                //.colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
                //.backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                //.colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                //{optional} - SubHeader Customization
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                //.colorLineSeparator(R.color.nliveo_blue_colorPrimary)

                //.footerItem(R.string.settings, R.mipmap.ic_settings_black_24dp)

                //{optional} - Footer Customization
                //.footerNameColor(R.color.nliveo_blue_colorPrimary)
                //.footerIconColor(R.color.nliveo_blue_colorPrimary)
                //.footerBackground(R.color.nliveo_white)

                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                //.setOnClickFooter(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
        //this.setElevationToolBar(position != 4 ? 15 : 0);
    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        switch (position){
            case 0:
                startActivity(new Intent(this, ChooseCity.class));
                return;

//            case 1:
//                startActivity(new Intent(this, ContentActivity.class));
//                return;

            case 2:
                mFragment = new ViewPagerFragment();
                break;

            default:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                break;
        }

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }

        setElevationToolBar(position != 2 ? 15 : 0);
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Add photo", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };



//    private View.OnClickListener onClickFooter = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//            closeDrawer();
//        }
//    };
}

