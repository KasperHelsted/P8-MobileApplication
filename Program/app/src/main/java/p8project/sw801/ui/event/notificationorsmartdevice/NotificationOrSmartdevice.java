package p8project.sw801.ui.event.notificationorsmartdevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.FragmentNotificationOrSmartdeviceBinding;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDevice;

public class NotificationOrSmartdevice extends BaseFragment<FragmentNotificationOrSmartdeviceBinding, NotificationOrSmartdeviceViewModel> implements NotificationOrSmartdeviceNavigator {

    @Inject
    NotificationOrSmartdeviceViewModel mNotificationOrSmartdeviceViewModel;
    private FragmentNotificationOrSmartdeviceBinding mFragmentNotificationOrSmartdeviceBinding;

    public static final String TAG = NotificationOrSmartdevice.class.getSimpleName();

    private EditText notification;
    private AddEvent addEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentNotificationOrSmartdeviceBinding = getViewDataBinding();
        View view = mFragmentNotificationOrSmartdeviceBinding.getRoot();
        mNotificationOrSmartdeviceViewModel.setNavigator(this);
        addEvent = (AddEvent) getActivity();
        notification = mFragmentNotificationOrSmartdeviceBinding.editTextNotification;
        return view;
    }

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationOrSmartdeviceViewModel.setNavigator(this);
        mFragmentNotificationOrSmartdeviceBinding = getViewDataBinding();
        notification = mFragmentNotificationOrSmartdeviceBinding.editTextNotification;
        addEvent = (AddEvent) getActivity();

    }
    */

    public static NotificationOrSmartdevice newInstance() {
        Bundle args = new Bundle();
        NotificationOrSmartdevice fragment = new NotificationOrSmartdevice();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification_or_smartdevice;
    }

    @Override
    public NotificationOrSmartdeviceViewModel getViewModel() {
        return mNotificationOrSmartdeviceViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void openAddSmartDeviceList() {
        Intent intent = AddEventSmartDevice.newIntent(getContext());
        startActivityForResult(intent, 0);
    }

    @Override
    public void addNotification() {
            Trigger t = new Trigger();
            t.setNotificationText(notification.getText().toString());
            t.setNotification(false);
            addEvent.addMyEvents.add(t);
            addEvent.refreshData();
            getActivity().getSupportFragmentManager().beginTransaction().remove(NotificationOrSmartdevice.this).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            String jsonMyObject ="";
            Bundle result = data.getExtras();
            if (result != null) {
                jsonMyObject = result.getString("key");
            }
            Trigger t = new Gson().fromJson(jsonMyObject, Trigger.class);
            addEvent.addMyEvents.add(t);
            addEvent.refreshData();
            getActivity().getSupportFragmentManager().beginTransaction().remove(NotificationOrSmartdevice.this).commit();
        }
    }
}
