package p8project.sw801.ui.event.addeventnest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventNestBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventNest extends BaseActivity<ActivityAddEventNestBinding, AddEventNestViewModel> implements AddEventNestNavigator {

    @Inject
    AddEventNestViewModel mAddEventNestViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventNestViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_nest;
    }

    @Override
    public AddEventNestViewModel getViewModel() {
        return mAddEventNestViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventNest.class);
        return intent;
    }
}
