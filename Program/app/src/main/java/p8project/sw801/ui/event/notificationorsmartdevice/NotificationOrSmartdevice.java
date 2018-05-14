package p8project.sw801.ui.event.notificationorsmartdevice;

import android.content.Intent;
import android.os.Bundle;
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
import p8project.sw801.utils.KeyBoardUtil;

public class NotificationOrSmartdevice extends BaseFragment<FragmentNotificationOrSmartdeviceBinding, NotificationOrSmartdeviceViewModel> implements NotificationOrSmartdeviceNavigator {

    @Inject
    NotificationOrSmartdeviceViewModel mNotificationOrSmartdeviceViewModel;
    private FragmentNotificationOrSmartdeviceBinding mFragmentNotificationOrSmartdeviceBinding;

    public static final String TAG = NotificationOrSmartdevice.class.getSimpleName();

    private EditText notification;
    private AddEvent addEvent;

    /**
     * On create view method for NotificationOrSmartdevice. Instantiates and sets up all required fields for the page.
     * @param inflater The infater used from the activity to inflate this fragment.
     * @param container The container containing this fragment.
     * @param savedInstanceState The saved instance state if there is one.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentNotificationOrSmartdeviceBinding = getViewDataBinding();
        View view = mFragmentNotificationOrSmartdeviceBinding.getRoot();
        mNotificationOrSmartdeviceViewModel.setNavigator(this);
        addEvent = (AddEvent) getActivity();
        notification = mFragmentNotificationOrSmartdeviceBinding.editTextNotification;

        KeyBoardUtil.setHideKeyboardOnTouch(getContext(), view.findViewById(R.id.notificationorsmartdevice));
        return view;
    }

    /**
     * Constructor for the fragment.
     * @return
     */
    public static NotificationOrSmartdevice newInstance() {
        Bundle args = new Bundle();
        NotificationOrSmartdevice fragment = new NotificationOrSmartdevice();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the binding variable.
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification_or_smartdevice;
    }

    /**
     * Get the instance of the view model.
     * @return Instance of the view model.
     */
    @Override
    public NotificationOrSmartdeviceViewModel getViewModel() {
        return mNotificationOrSmartdeviceViewModel;
    }

    /**
     * Method used to close the fragment.
     */
    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    /**
     * Method used to start the activity used when choosing a smart device trigger.
     */
    @Override
    public void openAddSmartDeviceList() {
        Intent intent = AddEventSmartDevice.newIntent(getContext());
        startActivityForResult(intent, 0);
    }

    /**
     * Method used when the user chooses to add a notification as text.
     * This method constructes a new trigger and adds it to the list of trigger from the AddEvent page.
     */
    @Override
    public void addNotification() {
            Trigger t = new Trigger();
            t.setNotificationText(notification.getText().toString());
            t.setNotification(false);
            t.setAction(0);
            addEvent.addMyEvents.add(t);
            addEvent.refreshData();
            getActivity().getSupportFragmentManager().beginTransaction().remove(NotificationOrSmartdevice.this).commit();
    }

    /**
     * Method used to catch the results from other activites that have been created from this one.
     * The returned result is either a trigger for a hue light or a trigger for a nest thermostat. This result is added to the list of trigger from the AddEvent page.
     * @param requestCode The code used when creating the returned activity.
     * @param resultCode The result code from the returned activity.
     * @param data The intent attached to the returning activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {
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
