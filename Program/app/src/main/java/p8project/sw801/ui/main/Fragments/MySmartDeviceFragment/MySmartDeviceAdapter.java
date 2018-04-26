package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.databinding.ItemMySmartDeviceEmptyViewBinding;
import p8project.sw801.databinding.ItemMySmartDeviceViewBinding;
import p8project.sw801.ui.base.BaseViewHolder;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.Items.MySmartDeviceEmptyItemViewModel;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.Items.MySmartDeviceItemViewModel;

public class MySmartDeviceAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<SmartDevice> mSmartDeviceList;

    private MySmartDeviceListener mListener;

    public MySmartDeviceAdapter(List<SmartDevice> smartDeviceList) {
        this.mSmartDeviceList = smartDeviceList;
    }

    @Override
    public int getItemCount() {
        if (mSmartDeviceList != null && mSmartDeviceList.size() > 0) {
            return mSmartDeviceList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSmartDeviceList != null && !mSmartDeviceList.isEmpty()) {
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
                ItemMySmartDeviceViewBinding blogViewBinding = ItemMySmartDeviceViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

                return new MySmartDeviceViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemMySmartDeviceEmptyViewBinding emptyViewBinding = ItemMySmartDeviceEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<SmartDevice> smartDeviceList) {
        mSmartDeviceList.addAll(smartDeviceList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mSmartDeviceList.clear();
    }

    public void setListener(MySmartDeviceListener listener) {
        this.mListener = listener;
    }

    public interface MySmartDeviceListener {

        void onRetryClick();
    }

    public class MySmartDeviceViewHolder extends BaseViewHolder implements MySmartDeviceItemViewModel.MySmartDeviceItemViewModelListener {

        private ItemMySmartDeviceViewBinding mBinding;

        private MySmartDeviceItemViewModel mMySmartDeviceItemViewModel;

        public MySmartDeviceViewHolder(ItemMySmartDeviceViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final SmartDevice smartDevice = mSmartDeviceList.get(position);
            mMySmartDeviceItemViewModel = new MySmartDeviceItemViewModel(smartDevice, this);

            mBinding.setViewModel(mMySmartDeviceItemViewModel);

            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(SmartDevice smartDevice) {
            System.out.println("CLICKED MySmartDeviceViewHolder");
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements MySmartDeviceEmptyItemViewModel.MySmartDeviceEmptyItemViewModelListener {

        private ItemMySmartDeviceEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemMySmartDeviceEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            MySmartDeviceEmptyItemViewModel emptyItemViewModel = new MySmartDeviceEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            System.out.println("RETRY!!!");
            mListener.onRetryClick();
        }
    }

}
