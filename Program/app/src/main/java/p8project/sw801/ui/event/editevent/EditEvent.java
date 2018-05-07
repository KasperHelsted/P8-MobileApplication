package p8project.sw801.ui.event.editevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityEditEventBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.choosenotificationorsmartdevice.ChooseNotificationOrSmartDevice;
import p8project.sw801.ui.event.editevent.triggersList.TriggerListAdapter;

public class EditEvent extends BaseActivity<ActivityEditEventBinding, EditEventViewModel> implements EditEventNavigator, TriggerListAdapter.TriggerListListener, HasSupportFragmentInjector {
    @Inject
    TriggerListAdapter mTriggerListAdapter;
    ActivityEditEventBinding mActivityEditEventBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    EditEventViewModel mEditEventViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditEventViewModel.setNavigator(this);
        mTriggerListAdapter.setListener(this);

        mActivityEditEventBinding = getViewDataBinding();

        mEditEventViewModel.loadInitialEvent(
                getIntent().getIntExtra("event_id", -1)
        );

        setUp();
        subscribeToLiveData();
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

    @Override
    public void addEventTrigger() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.placementfragment, ChooseNotificationOrSmartDevice.newInstance(), ChooseNotificationOrSmartDevice.TAG)
                .commit();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditEvent.class);
        return intent;
    }

    @Override
    public void addTrigger() {

    }

    @Override
    public void deleteTrigger(Trigger trigger) {
        mEditEventViewModel.deleteTrigger(trigger);
    }

    @Override
    public void onItemClick(Trigger trigger) {

    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mActivityEditEventBinding.recyclerViewMyTriggers.setLayoutManager(mLayoutManager);
        mActivityEditEventBinding.recyclerViewMyTriggers.setItemAnimator(new DefaultItemAnimator());
        mActivityEditEventBinding.recyclerViewMyTriggers.setAdapter(mTriggerListAdapter);
    }

    private void subscribeToLiveData() {
        mEditEventViewModel.getEventTriggersListLiveData().observe(this, triggers -> mEditEventViewModel.addTriggersToList(triggers));
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
