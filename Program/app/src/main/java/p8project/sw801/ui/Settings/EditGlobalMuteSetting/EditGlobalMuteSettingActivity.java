package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

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
import p8project.sw801.databinding.ActivityEditGlobalMuteBinding;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.CustomTimePickerCallback;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.TimePickerDialog;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.base.BaseViewModel;

/**
 * Created by clubd on 21-03-2018.
 */

public class EditGlobalMuteSettingActivity extends BaseActivity<ActivityEditGlobalMuteBinding, EditGlobalMuteSettingViewModel> implements EditGlobalMuteSettingNavigator, HasSupportFragmentInjector,CustomTimePickerCallback {

    /**
     * MVVM setup
     */
    @Inject
    EditGlobalMuteSettingViewModel mEditGlobalMuteSettingViewModel;
    private ActivityEditGlobalMuteBinding mActivityEditGlobalMuteBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private BaseViewModel callback;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_global_mute;
    }

    @Override
    public EditGlobalMuteSettingViewModel getViewModel() {
        return mEditGlobalMuteSettingViewModel;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /**
     * Sets up the activity for editting by getting the id from the intent and load the data
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditGlobalMuteSettingViewModel.setNavigator(this);
        mActivityEditGlobalMuteBinding = getViewDataBinding();

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", 0);
        mEditGlobalMuteSettingViewModel.loadData(id);
    }

    /**
     * 'Displays the viewmodel
     * @param viewModel instance of the viewmodel
     */
    @Override
    public void showTimePickerDialog(BaseViewModel viewModel) {
        callback = viewModel;

        TimePickerDialog.newInstance().show(getSupportFragmentManager());
    }

    /**
     * Method to make toast
     * @param msg msg to display in toast
     */
    @Override
    public void sendNotification(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Close the activity
     * @param v
     */
    public void closeAddGlobalMute(View v) {
        finish();
    }

    /**
     * Start a new intent without result
     * @param context context of intent
     * @return status of the intent
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditGlobalMuteSettingActivity.class);
        return intent;
    }

    /**
     * When the user finishes selecting time
     * a callback to viewmodel to update data is sent
     * @param datTime time as long
     */
    @Override
    public void onTimeSet(long datTime) {
        callback.callbackTimePicker(datTime);
    }
}