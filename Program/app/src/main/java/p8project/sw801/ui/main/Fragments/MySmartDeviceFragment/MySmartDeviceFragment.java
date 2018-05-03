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

    /**
     *
     * @return
     */
    public static MySmartDeviceFragment newInstance() {
        Bundle args = new Bundle();

        MySmartDeviceFragment fragment = new MySmartDeviceFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     *
     */
    @Override
    public void onResume() {
        getViewModel().fetchMySmartDevices();
        super.onResume();
    }

    /**
     *
     * @return
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_smart_device;
    }

    /**
     *
     * @return
     */
    @Override
    public MySmartDeviceViewModel getViewModel() {
        return mMySmartDeviceViewModel;
    }

    /**
     *
     * @param throwable
     */
    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMySmartDeviceViewModel.setNavigator(this);
        mMySmartDeviceAdapter.setListener(this);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     *
     */
    @Override
    public void addSmartDevice() {
        Intent intent = new Intent(this.getContext(), AddSmartDeviceActivity.class);
        startActivity(intent);
    }

    /**
     *
     * @param smartDevice
     */
    @Override
    public void deleteSmartDevice(SmartDevice smartDevice) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete smart device")
                .setMessage("Do you really want to delete " + smartDevice.getDeviceName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> getViewModel().deleteDevice(smartDevice))
                .setNegativeButton(android.R.string.no, null).show();
    }

    /**
     *
     * @param smartDevice
     */
    @Override
    public void toggleSmartDevice(SmartDevice smartDevice) {
        System.out.println("TOGGLE");
    }

    /**
     *
     * @param smartDevice
     */
    @Override
    public void onItemClick(SmartDevice smartDevice) {
        System.out.println("Click?");
    }

    /**
     *
     * @param smartDeviceList
     */
    @Override
    public void updateSmartDevice(List<SmartDevice> smartDeviceList) {
        mMySmartDeviceAdapter.addItems(smartDeviceList);
    }

    /**
     *
     */
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setLayoutManager(mLayoutManager);
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setItemAnimator(new DefaultItemAnimator());
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setAdapter(mMySmartDeviceAdapter);
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        mMySmartDeviceViewModel.getMySmartDevicesListLiveData().observe(this, smartDevices -> mMySmartDeviceViewModel.addMySmartDevicesItemsToList(smartDevices));
    }


}
