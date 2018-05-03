package p8project.sw801.ui.event.editevent.triggersList;

import android.databinding.ObservableField;

import p8project.sw801.data.model.db.Trigger;

public class TriggerListItemViewModel {
    public final ObservableField<String> tester = new ObservableField<>();

    private final TriggerListItemViewModelListener mListener;

    private final Trigger mTrigger;

    public TriggerListItemViewModel(Trigger trigger, TriggerListItemViewModelListener listener) {
        this.mTrigger = trigger;
        this.mListener = listener;

        tester.set(trigger.getNotificationText());
    }

    public void onItemClick() {
        System.out.println("CLICK");
    }

    public void deleteTrigger() {
        mListener.deleteTrigger(mTrigger);
    }

    public interface TriggerListItemViewModelListener {

        void onItemClick(Trigger trigger);

        void deleteTrigger(Trigger trigger);
    }
}
