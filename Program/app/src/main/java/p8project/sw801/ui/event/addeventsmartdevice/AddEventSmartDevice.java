package p8project.sw801.ui.event.addeventsmartdevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventSmartDevice extends BaseActivity<ActivityAddEventBinding, AddEventSmartDeviceViewModel> implements AddEventSmartDeviceNavigator {
    @Inject
    AddEventSmartDeviceViewModel mAddEventSmartDeviceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mActivityAddEventBinding = getViewDataBinding();
        mAddEventSmartDeviceViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_smart_device_list;
    }

    @Override
    public AddEventSmartDeviceViewModel getViewModel() {
        return mAddEventSmartDeviceViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventSmartDevice.class);
        return intent;
    }

}
