package p8project.sw801.ui.Settings;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivitySettingsBinding;
import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.Settings.Shopping.ShoppingSettingActivity;
import p8project.sw801.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
    @Inject
    SettingsViewModel mSettingsViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private ActivitySettingsBinding activitySettingsBinding;
    private EditText editTextGlobul;
    private EditText editTextLoc;
    private EditText editTextShop;

    /**
     * @return the mvvm viewmodel
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Specifices the view the activity belongs to
     *
     * @return the viewId
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    /**
     * @return a viewmodel instance
     */
    @Override
    public SettingsViewModel getViewModel() {
        return mSettingsViewModel;
    }

    /**
     * Used to setup the page for first entry
     * handles MVVM bindings
     *
     * @param savedInstanceState the state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MVVM bindings
        activitySettingsBinding = getViewDataBinding();
        mSettingsViewModel.setNavigator(this);
        //Setup MVVM bindings
        setup();
    }

    /**
     * Connects UI elements to accessiable fields
     */
    private void setup() {
        editTextGlobul = activitySettingsBinding.editTextGlobalmute;
        editTextLoc = activitySettingsBinding.editTextPredefinedlocationsettings;
        editTextShop = activitySettingsBinding.editTextPreferredshoppingsettings;

    }

    /**
     * @return The androidinjector for dagger
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /**
     * ClickEvent that Navigates to GlobalMuteSettingActivity
     */
    @Override
    public void navigateToGlobalMute() {
        Intent intent = new Intent(SettingsActivity.this, GlobalMuteSettingActivity.class);
        startActivity(intent);
    }

    /**
     * ClickEvent that Navigates to LocationSettingActivity
     */
    @Override
    public void navigateToPredefinedLocation() {
        Intent intent = new Intent(SettingsActivity.this, LocationSettingActivity.class);
        startActivity(intent);
    }

    /**
     * ClickEvent that Navigates to ShoppingSettingActivity
     */
    @Override
    public void navigateToPreferedShopping() {
        Intent intent = new Intent(SettingsActivity.this, ShoppingSettingActivity.class);
        startActivity(intent);
    }
}

