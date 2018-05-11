package p8project.sw801.ui.main;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    /**
     * On create method for MainActivity. Instantiates and sets up all required fields for the page.
     * initializes the viewPager and BurgerMenu
     *
     * @param savedInstanceState The saved instance state if there is one.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);

        initializeViewPager();
        drawer();

        mMainViewModel.firstRunSeeding();
    }

    //--------------------------Burger menu Start-------------------------------------

    /**
     * Method used to setup the BurgerMenu.
     */
    public void drawer() {
        mDrawer = findViewById(R.id.maindrawermenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv = findViewById(R.id.burgermenu);
        nv.setNavigationItemSelectedListener(this);


    }

    /**
     * Method used to return the value of the selected item
     *
     * @param item MenuItem: The selected item.
     * @return superclass implementation called with the selected item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener for handling events on navigation items for the BurgerMenu. Called when an item in the navigation menu is selected.
     * Navigates to the selected items activity or fragment and closes the drawer
     *
     * @param item MenuItem: The selected item
     * @return true to display the item as the selected item
     */
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
            case R.id.menusettings:
                Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingIntent);
                drawerMenu.closeDrawers();
                break;
            case R.id.menuaboutus:
                AlertDialog alertDialogAbout = new AlertDialog.Builder(MainActivity.this).create();
                alertDialogAbout.setTitle("About us");
                alertDialogAbout.setMessage("This application have been created by group SW801f18 at Aalborg University");
                alertDialogAbout.setIcon(R.drawable.ic_dashboard_black_24dp);

                alertDialogAbout.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialogAbout.show();
                break;
            case R.id.menucontactus:
                AlertDialog alertDialogContact = new AlertDialog.Builder(MainActivity.this).create();
                alertDialogContact.setTitle("Contact us");
                alertDialogContact.setMessage("We can be contacted on email: sw801f18@cs.aau.dk");
                alertDialogContact.setIcon(R.drawable.ic_dashboard_black_24dp);

                alertDialogContact.setButton(Dialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialogContact.show();
                break;
        }
        if (intent == null) {
            return false;
        }
        startActivity(intent);
        return false;
    }
    //--------------------------Burger menu End-------------------------------------

    /**
     * Method used on resume.
     * When the app returns to this page, this method is called.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Creates a new MainActivity intent.
     *
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    /**
     * Gets the binding variable.
     *
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     *
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    /**
     * Set the tab of the ViewPager as the current item
     *
     * @param tab
     */
    public void setView(int tab) {
        mActivityMainBinding.mainViewpager.setCurrentItem(tab);
    }

    /**
     * Method used for initialize the ViewPager and set the bindings
     */
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

    /**
     * Method used for changing the tab on the ViewPager to My event when clicked
     */
    @Override
    public void buttonMyEventsOnClick() {
        setView(1);
    }

    /**
     * Method used for changing the tab on the ViewPager to My smart device when clicked
     */
    @Override
    public void buttonMySmartDevicesOnClick() {
        setView(2);
    }


    @Override
    public void onBackPressed() {
        if (mActivityMainBinding.mainViewpager.getCurrentItem() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Close the application")
                    .setMessage("Do you really want to close NotifyMe?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        finish();
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            setView(0);
        }
    }
}

