package p8project.sw801.ui.event.editevent.triggersList;

import p8project.sw801.data.model.db.Trigger;

public class TriggerListEmptyItemViewModel {
    private final TriggerListEmptyItemViewModelListener mListener;

    public TriggerListEmptyItemViewModel(TriggerListEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onItemClick() {

    }

    public void deleteSmartDevice() {

    }

    public interface TriggerListEmptyItemViewModelListener {

        void onItemClick(Trigger trigger);

        void deleteTrigger(Trigger trigger);
    }
}
