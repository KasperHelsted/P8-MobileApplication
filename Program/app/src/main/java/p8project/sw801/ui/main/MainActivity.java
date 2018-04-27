package p8project.sw801.ui.main;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityMainBinding;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.main.Adapters.SectionsPagerAdapter;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    SectionsPagerAdapter mPagerAdapter;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstRunSeeding();

        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);

        initializeViewPager();
        drawer();
    }
    //--------------------------Burger menu-------------------------------------

    private void firstRunSeeding() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean("firstTime", false)) {

            //TODO: https://stackoverflow.com/questions/15061653/run-a-piece-of-code-only-once-when-an-application-is-installed?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

        }
    }

    public void drawer() {


        mDrawer = findViewById(R.id.maindrawermenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv = findViewById(R.id.burgermenu);
        nv.setNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        DrawerLayout drawerMenu = findViewById(R.id.maindrawermenu);
        switch (id) {
            case R.id.menumyevent:
                buttonMyEventsOnClick();
                drawerMenu.closeDrawers();
                break;
            case R.id.menumysmartdevices:
                buttonMySmartDevicesOnClick();
                drawerMenu.closeDrawers();
                break;
            case R.id.menuaddsmartdevies:
                //Intent ac = new Intent(MainActivity.this,MySmartDeviceFragment.class);
                //startActivity(ac);
                //ChangeToSmartDevice();
                drawerMenu.closeDrawers();
                break;
            case R.id.menusettings:
                Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingIntent);
                drawerMenu.closeDrawers();
                break;
            case R.id.menuaboutus:
                AlertDialog alertDialogabout = new AlertDialog.Builder(MainActivity.this).create();
                alertDialogabout.setTitle("About us");
                alertDialogabout.setMessage("This application have been created by group SW801f18 at Aalborg University");
                alertDialogabout.setIcon(R.drawable.ic_dashboard_black_24dp);

                alertDialogabout.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialogabout.show();
                break;
            case R.id.menucontactus:
                AlertDialog alertDialogcontact = new AlertDialog.Builder(MainActivity.this).create();
                alertDialogcontact.setTitle("Contact us");
                alertDialogcontact.setMessage("We can be contacted on email: sw801f18@cs.aau.dk");
                alertDialogcontact.setIcon(R.drawable.ic_dashboard_black_24dp);

                alertDialogcontact.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialogcontact.show();
                break;
        }
        if (intent == null) {
            return false;
        }
        startActivity(intent);
        return false;
    }
    //--------------------------Burger menu-------------------------------------


    @Override
    protected void onResume() {
        super.onResume();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    public void setView(int tab) {
        mActivityMainBinding.mainViewpager.setCurrentItem(tab);
    }

    private void initializeViewPager() {
        //Set page viewer adapter
        mActivityMainBinding.mainViewpager.setAdapter(mPagerAdapter);

        //Create tabs
        mActivityMainBinding.tabs.addTab(mActivityMainBinding.tabs.newTab().setText(getString(R.string.tab_text_1)));
        mActivityMainBinding.tabs.addTab(mActivityMainBinding.tabs.newTab().setText(getString(R.string.tab_text_2)));
        mActivityMainBinding.tabs.addTab(mActivityMainBinding.tabs.newTab().setText(getString(R.string.tab_text_3)));

        //Set off page limit (how many pages we have
        mActivityMainBinding.mainViewpager.setOffscreenPageLimit(mActivityMainBinding.tabs.getTabCount());

        //Sets on page change listner
        mActivityMainBinding.mainViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityMainBinding.tabs));

        // Allows for tabs to change view
        mActivityMainBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setView(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void buttonMyEventsOnClick() {
        setView(1);
    }

    @Override
    public void buttonMySmartDevicesOnClick() {
        setView(2);
    }


}

