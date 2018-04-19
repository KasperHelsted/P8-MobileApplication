package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddGlobalMuteBinding;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.CustomTimePickerCallback;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.TimePickerDialog;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.base.BaseViewModel;


/**
 * Created by clubd on 21-03-2018.
 */

public class AddGlobalMuteSettingActivity extends BaseActivity<ActivityAddGlobalMuteBinding, AddGlobalMuteSettingViewModel> implements AddGlobalMuteSettingNavigator, HasSupportFragmentInjector, CustomTimePickerCallback {

    @Inject
    AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private ActivityAddGlobalMuteBinding mActivityAddGlobalMuteBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private BaseViewModel callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddGlobalMuteSettingViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_global_mute;
    }

    @Override
    public AddGlobalMuteSettingViewModel getViewModel() {
        return mAddGlobalMuteSettingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void showTimePickerDialog(BaseViewModel viewModel) {
        callback = viewModel;

        TimePickerDialog.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void sendNotification(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    public void closeAddGlobalMute(View v) {
        finish();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddGlobalMuteSettingActivity.class);
        return intent;
    }

    @Override
    public void onTimeSet(long datTime) {
        callback.callbackTimePicker(datTime);
    }
}

