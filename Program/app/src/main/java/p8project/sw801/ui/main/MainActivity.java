package p8project.sw801.ui.main;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityMainBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.main.Adapters.SectionsPagerAdapter;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    SectionsPagerAdapter mPagerAdapter;

    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);

        initializeViewPager();
    }

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
}

