package p8project.sw801.ui.main.Fragments.HomeFragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;
import p8project.sw801.R;
import p8project.sw801.BR;
import p8project.sw801.databinding.ActivityHomeBinding;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.main.MainActivity;
import dagger.android.support.AndroidSupportInjection;


//Implements Navigationviewlistener
public class HomeFragment extends BaseFragment<ActivityHomeBinding, HomeFragmentViewModel> implements HomeFragmentNavigator  {


    @Inject
    HomeFragmentViewModel mHomeFragmentViewModel;
    private ActivityHomeBinding mActivityHomeBinding;

    public static final String TAG = HomeFragment.class.getSimpleName();
    private Button buttonCreate;
    private Button buttonMyEvents;
    private Button buttonMySmartDevices;
    private Button buttonSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivityHomeBinding  = getViewDataBinding();
        View view = mActivityHomeBinding.getRoot();
        mHomeFragmentViewModel.setNavigator(this);
        setUp();
        return view;
    }

    private void setUp(){


        buttonCreate = mActivityHomeBinding.buttonCreate;
        buttonMyEvents = mActivityHomeBinding.buttonMyEvents;
        buttonMySmartDevices = mActivityHomeBinding.buttonMySmartDevices;
        buttonSettings = mActivityHomeBinding.buttonSettings;

    }

    public void buttonCreateOnClick(){
        Intent intent = new Intent(getActivity(), AddEvent.class);
        startActivity(intent);
    }
    public void buttonMyEventsOnClick(){
        ((MainActivity)getActivity()).ChangeToEvents();
    }
    public void buttonMySmartDevicesOnClick(){
        ((MainActivity)getActivity()).ChangeToSmartDevice();
    }
    public void buttonSettingsOnClick(){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public HomeFragmentViewModel getViewModel() {
        return mHomeFragmentViewModel;
    }

}