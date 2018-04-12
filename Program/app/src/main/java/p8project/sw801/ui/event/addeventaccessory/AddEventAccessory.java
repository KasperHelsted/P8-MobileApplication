package p8project.sw801.ui.event.addeventaccessory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventAccessoryBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventAccessory extends BaseActivity<ActivityAddEventAccessoryBinding, AddEventAccessoryViewModel> implements AddEventAccessoryNavigator {
    @Inject
    AddEventAccessoryViewModel mAddEventAccessoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventAccessoryViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_accessory;
    }

    @Override
    public AddEventAccessoryViewModel getViewModel() {
        return mAddEventAccessoryViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventAccessory.class);
        return intent;
    }

}
