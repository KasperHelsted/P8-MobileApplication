package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.ui.SmartDevice.AddSmartDeviceActivity;
import p8project.sw801.R;
import p8project.sw801.BR;
import p8project.sw801.databinding.ActivityMySmartDeviceBinding;
import p8project.sw801.ui.base.BaseFragment;


public class MySmartDeviceFragment extends BaseFragment<ActivityMySmartDeviceBinding, MySmartDeviceFragmentViewModel> implements MySmartDeviceFragmentNavigator {
    @Inject
    MySmartDeviceFragmentViewModel mMySmartDeviceFragmentViewModel;
    private ActivityMySmartDeviceBinding mActivityMySmartDeviceBinding;

    ArrayList<String> smartDevices;
    //Setup of burger menu
    private ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivityMySmartDeviceBinding  = getViewDataBinding();
        View view = mActivityMySmartDeviceBinding.getRoot();
        mMySmartDeviceFragmentViewModel.setNavigator(this);
        setUp();
        return view;

    }

    private void setUp(){
        ImageView add = mActivityMySmartDeviceBinding.imageViewMyeventadd;
        listview = (ListView) mActivityMySmartDeviceBinding.listViewMysmartdevices;

        //------Creation of list of smart devices
//        UserPreference.getSmartDeviceList(rootView.getContext()).observe(this, new Observer<List<SmartDevice>>() {
//            @Override
//            public void onChanged(@Nullable List<SmartDevice> smartDevices) {
//                SmartDeviceAdapter smartDeviceAdapter = new SmartDeviceAdapter(
//                        rootView.getContext(),
//                        smartDevices
//                );
//
//                listview.setAdapter(smartDeviceAdapter);
//
//            }
//        });


        //------Creation of list of smart devices

        //Add new smart device


    }

    public void addNewSmartDevice(){
        Intent intent = new Intent(this.getContext(), AddSmartDeviceActivity.class);
        startActivity(intent);
    }
    public static MySmartDeviceFragment newInstance() {
        Bundle args = new Bundle();
        MySmartDeviceFragment fragment = new MySmartDeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_smart_device;
    }

    @Override
    public MySmartDeviceFragmentViewModel getViewModel() {
        return mMySmartDeviceFragmentViewModel;
    }

    //TODO add onResume to redraw list after edit
}
