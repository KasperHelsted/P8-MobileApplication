package p8project.sw801.ui.event.choosenotificationorsmartdevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.FragmentChooseNotificationOrSmartdeviceBinding;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.custom.FragmentCallback;
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDevice;

public class ChooseNotificationOrSmartDevice extends BaseFragment<FragmentChooseNotificationOrSmartdeviceBinding, ChooseNotificationOrSmartDeviceViewModel> implements ChooseNotificationOrSmartDeviceNavigator {
    @Inject
    ChooseNotificationOrSmartDeviceViewModel mChooseNotificationOrSmartDeviceViewModel;

    public static final String TAG = ChooseNotificationOrSmartDevice.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChooseNotificationOrSmartDeviceViewModel.setNavigator(this);
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_choose_notification_or_smartdevice;
    }

    @Override
    public ChooseNotificationOrSmartDeviceViewModel getViewModel() {
        return mChooseNotificationOrSmartDeviceViewModel;
    }

    public static ChooseNotificationOrSmartDevice newInstance() {
        Bundle args = new Bundle();

        ChooseNotificationOrSmartDevice fragment = new ChooseNotificationOrSmartDevice();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void goBack() {
    }

    @Override
    public void openAddSmartDeviceList() {
        Intent intent = AddEventSmartDevice.newIntent(getContext());
        getActivity().startActivityForResult(intent, 3);
    }

    @Override
    public void addNotification(String notificationText) {
        if (getActivity() instanceof FragmentCallback) {
            ((FragmentCallback) getActivity()).fragmentCallback(
                    0,
                    notificationText
            );
        }
    }
}
