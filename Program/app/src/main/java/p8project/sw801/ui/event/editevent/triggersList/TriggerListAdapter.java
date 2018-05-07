package p8project.sw801.ui.event.editevent.triggersList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ItemTriggerListBinding;
import p8project.sw801.databinding.ItemTriggerListEmptyBinding;
import p8project.sw801.ui.base.BaseViewHolder;

public class TriggerListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Trigger> mTriggerList;

    private TriggerListListener mListener;

    public TriggerListAdapter(List<Trigger> triggerList) {
        this.mTriggerList = triggerList;
    }

    @Override
    public int getItemCount() {
        if (mTriggerList != null && mTriggerList.size() > 0) {
            return mTriggerList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTriggerList != null && !mTriggerList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemTriggerListBinding triggerViewBinding = ItemTriggerListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

                return new TriggerListViewHolder(triggerViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemTriggerListEmptyBinding emptyViewBinding = ItemTriggerListEmptyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

                return new EmptyTriggerListViewHolder(emptyViewBinding);
        }


    }

    public void addItems(List<Trigger> triggerList) {
        mTriggerList.addAll(triggerList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mTriggerList.clear();
    }

    public void setListener(TriggerListListener listener) {
        this.mListener = listener;
    }

    public interface TriggerListListener {
        void addTrigger();

        void deleteTrigger(Trigger trigger);

        void onItemClick(Trigger trigger);
    }

    public class TriggerListViewHolder extends BaseViewHolder implements TriggerListItemViewModel.TriggerListItemViewModelListener {
        private ItemTriggerListBinding mBinding;

        private TriggerListItemViewModel mTriggerListItemViewModel;

        public TriggerListViewHolder(ItemTriggerListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Trigger trigger = mTriggerList.get(position);
            mTriggerListItemViewModel = new TriggerListItemViewModel(trigger, this);

            mBinding.setViewModel(mTriggerListItemViewModel);

            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(Trigger trigger) {

        }

        @Override
        public void deleteTrigger(Trigger trigger) {
            mListener.deleteTrigger(trigger);
        }
    }

    public class EmptyTriggerListViewHolder extends BaseViewHolder implements TriggerListEmptyItemViewModel.TriggerListEmptyItemViewModelListener {

        private ItemTriggerListEmptyBinding mBinding;

        public EmptyTriggerListViewHolder(ItemTriggerListEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            TriggerListEmptyItemViewModel emptyItemViewModel = new TriggerListEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onItemClick(Trigger trigger) {

        }

        @Override
        public void deleteTrigger(Trigger trigger) {

        }
    }
}
