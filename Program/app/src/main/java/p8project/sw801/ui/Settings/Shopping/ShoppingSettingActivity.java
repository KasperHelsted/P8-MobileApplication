package p8project.sw801.ui.Settings.Shopping;


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
import p8project.sw801.databinding.ActivityShoppingSettingBinding;
import p8project.sw801.ui.Settings.SettingsViewModel;
import p8project.sw801.ui.base.BaseActivity;

public class ShoppingSettingActivity extends BaseActivity<ActivityShoppingSettingBinding,ShoppingSettingViewModel> implements ShoppingSettingNavigator, HasSupportFragmentInjector {

    private ActivityShoppingSettingBinding ActivityShoppingSettingBinding;
    private ShoppingSettingViewModel mShoppingSettingViewModel;
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
        return R.layout.activity_shopping_setting;
    }

    @Override
    public ShoppingSettingViewModel getViewModel() {
        mShoppingSettingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ShoppingSettingViewModel.class);
        return mShoppingSettingViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_setting);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {
        //todo:du en fejl
    }
}

