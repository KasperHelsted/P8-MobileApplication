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
     * Constructor for the fragment
     *
     * @return The fragment
     */
    public static MySmartDeviceFragment newInstance() {
        Bundle args = new Bundle();

        MySmartDeviceFragment fragment = new MySmartDeviceFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * On resume method. Calls the database to get the newest list of smart devices
     */
    @Override
    public void onResume() {
        getViewModel().fetchMySmartDevices();
        super.onResume();
    }

    /**
     * Gets the binding variable.
     *
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     *
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_smart_device;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public MySmartDeviceViewModel getViewModel() {
        return mMySmartDeviceViewModel;
    }

    /**
     * On create method for MySmartDeviceFragment.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMySmartDeviceViewModel.setNavigator(this);
        mMySmartDeviceAdapter.setListener(this);
    }

    /**
     * On create view method for MySmartDeviceFragment. Instantiates and sets up all required fields for the page.
     *
     * @param inflater           The infater used from the activity to inflate this fragment.
     * @param container          The container containing this fragment.
     * @param savedInstanceState The saved instance state if there is one.
     * @return The inflated view.
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
     * Method used when the user presses the add smart device button.
     * Start a new AddSmartDevice activity.
     */
    @Override
    public void addSmartDevice() {
        Intent intent = new Intent(this.getContext(), AddSmartDeviceActivity.class);
        startActivity(intent);
    }

    /**
     * Method used when the user presses the delete button on a displayed smart device.
     * Opens a confirmation dialog.
     *
     * @param smartDevice The smart device object to be deleted.
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
     * Toggle method for smart devices.
     *
     * @param smartDevice The smart device to toggle.
     */
    @Override
    public void toggleSmartDevice(SmartDevice smartDevice) {
    }

    /**
     * On click method for smart devices.
     *
     * @param smartDevice The clicked smart device.
     */
    @Override
    public void onItemClick(SmartDevice smartDevice) {
    }

    /**
     * Method called to update adapter rendering the list of smart devices.
     *
     * @param smartDeviceList The new list of smart devices.
     */
    @Override
    public void updateSmartDevice(List<SmartDevice> smartDeviceList) {
        mMySmartDeviceAdapter.addItems(smartDeviceList);
    }

    /**
     * Method used to set up the view.
     */
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setLayoutManager(mLayoutManager);
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setItemAnimator(new DefaultItemAnimator());
        mFragmentMySmartDeviceBinding.recyclerViewMysmartdevices.setAdapter(mMySmartDeviceAdapter);
    }

    /**
     * Method used to instantiate an observer used to access the live data objects on the view.
     */
    private void subscribeToLiveData() {
        mMySmartDeviceViewModel.getMySmartDevicesListLiveData().observe(this, smartDevices -> mMySmartDeviceViewModel.addMySmartDevicesItemsToList(smartDevices));
    }


}
