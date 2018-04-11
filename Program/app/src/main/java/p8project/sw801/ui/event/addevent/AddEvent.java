package p8project.sw801.ui.event.addevent;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdevice;


public class AddEvent extends BaseActivity<ActivityAddEventBinding, AddEventViewModel> implements AddEventNavigator {
    @Inject
    AddEventViewModel mAddEventViewModel;
    private ActivityAddEventBinding mActivityAddEventBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEventBinding = getViewDataBinding();
        mAddEventViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event;
    }

    @Override
    public AddEventViewModel getViewModel() {
        return mAddEventViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(AddEvent.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showNotificationOrSmartdevice() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.main_content, NotificationOrSmartdevice.newInstance(), NotificationOrSmartdevice.TAG)
                .commit();

    }
}
