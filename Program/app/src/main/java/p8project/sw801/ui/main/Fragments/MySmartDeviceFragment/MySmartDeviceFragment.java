package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.databinding.ActivityMySmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceActivity;
import p8project.sw801.ui.SmartDevice.SmartDeviceAdapter;
import p8project.sw801.ui.base.BaseFragment;


public class MySmartDeviceFragment extends BaseFragment<ActivityMySmartDeviceBinding, MySmartDeviceFragmentViewModel> implements MySmartDeviceFragmentNavigator {
    @Inject
    MySmartDeviceFragmentViewModel mMySmartDeviceFragmentViewModel;
    private ActivityMySmartDeviceBinding mActivityMySmartDeviceBinding;

    ArrayList<String> smartDevices;
    //Setup of burger menu
    private ListView listview;
    ArrayList<SmartDevice> mySmartdevices;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mActivityMySmartDeviceBinding  = getViewDataBinding();
        view = mActivityMySmartDeviceBinding.getRoot();
        mMySmartDeviceFragmentViewModel.setNavigator(this);
        setUp();
        return view;

    }

    private void setUp(){
        ImageView add = mActivityMySmartDeviceBinding.imageViewMyeventadd;
        listview = (ListView) mActivityMySmartDeviceBinding.listViewMysmartdevices;

        //------Creation of list of Events
        mySmartdevices = new ArrayList<>();
        mySmartdevices.addAll(mMySmartDeviceFragmentViewModel.getSmartDeviceObservableList());
        if (mySmartdevices == null){

        }
        else{
            SmartDeviceAdapter myAdapter = new SmartDeviceAdapter(view.getContext(), mySmartdevices, MySmartDeviceFragment.this);
            listview.setAdapter(myAdapter);
        }

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
    public void updatelist(){
        setUp();
    }

    public void deleteSmartDevice(SmartDevice sd){
        mMySmartDeviceFragmentViewModel.deleteSmartDevice(sd);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            mMySmartDeviceFragmentViewModel.getListFromDb();
        }
    }
}
