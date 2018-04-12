package p8project.sw801.ui.Settings.GlobalMute;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddGlobalMuteBinding;
import p8project.sw801.ui.Settings.SettingsNavigator;
import p8project.sw801.ui.Settings.SettingsViewModel;
import p8project.sw801.ui.base.BaseActivity;

/**
 * Created by clubd on 21-03-2018.
 */

public class AddGlobalMuteSettingActivity extends BaseActivity<ActivityAddGlobalMuteBinding,SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
    private ActivityAddGlobalMuteBinding mActivityAddGlobalMuteBinding;

    @Inject
    SettingsViewModel mSettingsViewModel;

    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_global_mute;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingsViewModel.class);
        return mSettingsViewModel;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_global_mute);
        mSettingsViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();
        setTitle("Notify me - Add global mute");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}

