package p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import p8project.sw801.R;
import p8project.sw801.databinding.DialogTimePickerBinding;
import p8project.sw801.ui.base.BaseDialog;

public class TimePickerDialog extends BaseDialog implements TimePickerCallback {
    private static final String TAG = TimePickerDialog.class.getSimpleName();
    private CustomTimePickerCallback callback;
    private long time;

    @Inject
    TimePickerViewModel mTimePickerViewModel;

    public static TimePickerDialog newInstance() {
        Bundle bundle = new Bundle();
        return newInstance(bundle);
    }

    public static TimePickerDialog newInstance(Bundle bundle) {
        TimePickerDialog fragment = new TimePickerDialog();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogTimePickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_time_picker, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);

        binding.setViewModel(mTimePickerViewModel);

        mTimePickerViewModel.setNavigator(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getParentFragment() != null && getParentFragment() instanceof CustomTimePickerCallback) {
            callback = (CustomTimePickerCallback) getParentFragment();
        } else if (context instanceof CustomTimePickerCallback) {
            callback = (CustomTimePickerCallback) context;
        } else {
            throw new RuntimeException(String.format("%s must implement CustomTimePickerCallback", context.getClass().getSimpleName()));
        }
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public void confirmDialog() {
        callback.onTimeSet(time);
        dismissDialog(TAG);
    }

    @Override
    public void setTime(long t) {
        time = t;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

}
