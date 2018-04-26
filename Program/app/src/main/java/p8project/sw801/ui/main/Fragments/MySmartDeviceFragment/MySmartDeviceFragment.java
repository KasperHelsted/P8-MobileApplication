package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.databinding.FragmentMySmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceActivity;
import p8project.sw801.ui.base.BaseFragment;

public class MySmartDeviceFragment extends BaseFragment<FragmentMySmartDeviceBinding, MySmartDeviceViewModel> implements MySmartDeviceNavigator, MySmartDeviceAdapter.MySmartDeviceListener {
    @Inject
    MySmartDeviceAdapter mMySmartDeviceAdapter;
    FragmentMySmartDeviceBinding mFragmentMySmartDeviceBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    MySmartDeviceViewModel mMySmartDeviceViewModel;

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
        return R.layout.fragment_my_smart_device;
    }


    @Override
    public MySmartDeviceViewModel getViewModel() {
        return mMySmartDeviceViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMySmartDeviceViewModel.setNavigator(this);
        mMySmartDeviceAdapter.setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentMySmartDeviceBinding = getViewDataBinding();

        View view = mFragmentMySmartDeviceBinding.getRoot();
        mMySmartDeviceViewModel.setNavigator(this);

        setUp();
        subscribeToLiveData();

        return view;
    }

    @Override
    public void addSmartDevice() {
        Intent intent = new Intent(this.getContext(), AddSmartDeviceActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteSmartDevice(SmartDevice smartDevice) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete smart device")
                .setMessage("Do you really want to delete " + smartDevice.getDeviceName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> System.out.println("DELETE THIS SMART DEVICE!"))
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void toggleSmartDevice(SmartDevice smartDevice) {
        //TODO: toggle stuffers
        System.out.println("TOGGLE");
    }

    @Override
    public void onItemClick(SmartDevice smartDevice) {
        //TODO: toggle stuffers
        System.out.println("Click?");
    }

    @Override
    public void updateSmartDevice(List<SmartDevice> smartDeviceList) {
        mMySmartDeviceAdapter.addItems(smartDeviceList);
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setLayoutManager(mLayoutManager);
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setItemAnimator(new DefaultItemAnimator());
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setAdapter(mMySmartDeviceAdapter);
    }

    private void subscribeToLiveData() {
        mMySmartDeviceViewModel.getMySmartDevicesListLiveData().observe(this, smartDevices -> mMySmartDeviceViewModel.addMySmartDevicesItemsToList(smartDevices));
    }


}
