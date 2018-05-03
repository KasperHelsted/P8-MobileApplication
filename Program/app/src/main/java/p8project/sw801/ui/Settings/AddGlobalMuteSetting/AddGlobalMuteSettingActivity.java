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

    /**
     * On create method for AddGlobalMuteSetting. Instantiates the bindings of the viewmodel.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddGlobalMuteSettingViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();
    }

    /**
     * Gets the binding variable.
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_global_mute;
    }

    /**
     * Get the instance of the view model.
     * @return Instance of the view model.
     */
    @Override
    public AddGlobalMuteSettingViewModel getViewModel() {
        return mAddGlobalMuteSettingViewModel;
    }

    /**
     * Method called to create a new time picker fragment.
     * @param viewModel The instance of the viewmodel used for returning the value of the time picker.
     */
    @Override
    public void showTimePickerDialog(BaseViewModel viewModel) {
        callback = viewModel;

        TimePickerDialog.newInstance().show(getSupportFragmentManager());
    }

    /**
     * Method used to send a notification to the user.
     * @param msg The message of the notification.
     */
    @Override
    public void sendNotification(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Fragment injector used when creating new fragments to inflate.
     * @return The fragment injector
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /**
     * Method used to close this page.
     * @param v
     */
    public void closeAddGlobalMute(View v) {
        finish();
    }

    /**
     * Method used to create a new intent of AddGlobalMuteSettingActicity
     * @param context The context of the application.
     * @return The created intent
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddGlobalMuteSettingActivity.class);
        return intent;
    }

    /**
     * Method called when a user have picked a time in the time picker fragment.
     * Returns the time picked to the viewmodel.
     * @param datTime The time chosen by the user.
     */
    @Override
    public void onTimeSet(long datTime) {
        callback.callbackTimePicker(datTime);
    }
}

