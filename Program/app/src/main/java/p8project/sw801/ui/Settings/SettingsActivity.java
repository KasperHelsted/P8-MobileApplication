package p8project.sw801.ui.Settings;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivitySettingsBinding;
import p8project.sw801.ui.Settings.GlobalMute.GlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.Settings.Shopping.ShoppingSettingActivity;
import p8project.sw801.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding,SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
private ActivitySettingsBinding mActivitySettingsBinding;
private SettingsViewModel mSettingsViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingsViewModel.class);
        return mSettingsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Notify Me - Settings");
        setContentView(R.layout.activity_settings);
        mSettingsViewModel.setNavigator(this);
        mActivitySettingsBinding = getViewDataBinding();

        //--------------------------Navigation bar----------------------------------
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //--------------------------Navigation bar----------------------------------

        //--------------------------Burger menu-------------------------------------
        //drawerLayout = findViewById(R.id.settingsactivity);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationView nv = findViewById(R.id.burgermenu);
        //nv.setNavigationItemSelectedListener(this);
        //--------------------------Burger menu-------------------------------------

        //----------------Rest of the code

        EditText globalmute = findViewById(R.id.editText_globalmute);
        globalmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, GlobalMuteSettingActivity.class);
                startActivity(intent);
            }
        });

        EditText predefinedlocation = findViewById(R.id.editText_predefinedlocationsettings);
        predefinedlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, LocationSettingActivity.class);
                startActivity(intent);
            }
        });

        EditText preferredshopping = findViewById(R.id.editText_preferredshoppingsettings);
        preferredshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ShoppingSettingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void handleError(Throwable throwable) {
        //todo: DU EN FEJL!
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

