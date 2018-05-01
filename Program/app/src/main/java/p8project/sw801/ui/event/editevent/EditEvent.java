package p8project.sw801.ui.event.editevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityEditEventBinding;
import p8project.sw801.ui.base.BaseActivity;

public class EditEvent extends BaseActivity<ActivityEditEventBinding, EditEventViewModel> implements EditEventNavigator {

    @Inject
    EditEventViewModel mEditEventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditEventViewModel.setNavigator(this);

        mEditEventViewModel.initializeLists();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_event;
    }

    @Override
    public EditEventViewModel getViewModel() {
        return mEditEventViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditEvent.class);
        return intent;
    }

}
