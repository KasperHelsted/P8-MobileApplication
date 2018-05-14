package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
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
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.KeyBoardUtil;


/**
 * Created by clubd on 21-03-2018.
 */

public class AddGlobalMuteSettingActivity extends BaseActivity<ActivityAddGlobalMuteBinding, AddGlobalMuteSettingViewModel> implements AddGlobalMuteSettingNavigator, HasSupportFragmentInjector {

    @Inject
    AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private ActivityAddGlobalMuteBinding mActivityAddGlobalMuteBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private BaseViewModel callback;

    /**
     * On create method for AddGlobalMuteSetting. Instantiates the bindings of the viewmodel.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddGlobalMuteSettingViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();
        KeyBoardUtil.setHideKeyboardOnTouch(this, findViewById(R.id.globalmutepage));
    }

    /**
     * Gets the binding variable.
     *
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     *
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_global_mute;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public AddGlobalMuteSettingViewModel getViewModel() {
        return mAddGlobalMuteSettingViewModel;
    }

    /**
     * Method used to send a notification to the user.
     *
     * @param msg The message of the notification.
     */
    @Override
    public void sendNotification(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Fragment injector used when creating new fragments to inflate.
     *
     * @return The fragment injector
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /**
     * Method used to close this page.
     *
     * @param v View to be closed.
     */
    public void closeAddGlobalMute(View v) {
        finish();
    }

    /**
     * Method used to create a new intent of AddGlobalMuteSettingActicity
     *
     * @param context The context of the application.
     * @return The created intent
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddGlobalMuteSettingActivity.class);
        return intent;
    }
}

