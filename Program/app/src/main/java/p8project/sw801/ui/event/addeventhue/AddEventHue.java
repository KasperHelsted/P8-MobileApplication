package p8project.sw801.ui.event.addeventhue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventHueBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventHue extends BaseActivity<ActivityAddEventHueBinding, AddEventHueViewModel> implements AddEventHueNavigator {

    @Inject
    AddEventHueViewModel mAddEventHueViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventHueViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_hue;
    }

    @Override
    public AddEventHueViewModel getViewModel() {
        return mAddEventHueViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventHue.class);
        return intent;
    }
}
