package p8project.sw801.ui.event.editevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityEditEventBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.editevent.triggersList.TriggerListAdapter;

public class EditEvent extends BaseActivity<ActivityEditEventBinding, EditEventViewModel> implements EditEventNavigator, TriggerListAdapter.TriggerListListener {
    /**
     * MVVM setup
     */
    @Inject
    TriggerListAdapter mTriggerListAdapter;
    ActivityEditEventBinding mActivityEditEventBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    EditEventViewModel mEditEventViewModel;

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

    /**'
     * Sets up the activity with data to edit
     * @param savedInstanceState
     */
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

    /**
     * Dynamic method to create new intent
     * @param context Context
     * @return intent status
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditEvent.class);
        return intent;
    }

    @Override
    public void addTrigger() {

    }

    /**
     * Deletes a trigger
     * @param trigger trigger to delete
     */
    @Override
    public void deleteTrigger(Trigger trigger) {
        mEditEventViewModel.eventTriggersObservableArrayList.remove(trigger);
    }

    @Override
    public void onItemClick(Trigger trigger) {

    }

    /**
     * Initial setup
     */
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mActivityEditEventBinding.recyclerViewMyTriggers.setLayoutManager(mLayoutManager);
        mActivityEditEventBinding.recyclerViewMyTriggers.setItemAnimator(new DefaultItemAnimator());
        mActivityEditEventBinding.recyclerViewMyTriggers.setAdapter(mTriggerListAdapter);
    }

    /**
     * Subscription to live data
     */
    private void subscribeToLiveData() {
        mEditEventViewModel.getEventTriggersListLiveData().observe(this, triggers -> mEditEventViewModel.addTriggersToList(triggers));
    }

}
