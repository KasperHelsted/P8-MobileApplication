package p8project.sw801.ui.SmartDevice;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityEditSmartDeviceBinding;
import p8project.sw801.ui.base.BaseActivity;

public class EditSmartDeviceActivity extends BaseActivity<ActivityEditSmartDeviceBinding, AddSmartDeviceViewModel> implements AddSmartDeviceNavigator, HasSupportFragmentInjector {
    private Integer deviceId;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private ActivityEditSmartDeviceBinding mActivityEditSmartDeviceBinding;
    private AddSmartDeviceViewModel mAddSmartDeviceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEditSmartDeviceBinding = getViewDataBinding();
        mAddSmartDeviceViewModel.setNavigator(this);
        setUp();
    }

    private void setUp(){
        //setContentView(R.layout.activity_edit_smart_device);
        //Intent i = getIntent();

        //deviceId = i.getIntExtra("device_id", 0);

        //UserPreference.getSmartDeviceById(getApplicationContext(), deviceId).observe(this, new Observer<SmartDevice>() {
        //    @Override
        //    public void onChanged(@Nullable final SmartDevice smartDevice) {
        //        if (smartDevice == null) {
        //            finish();
        //        }
        final TextView textView = findViewById(R.id.textView_editdeviceName);
        //textView.setText(smartDevice.getDeviceName());
        final EditText editTextName = findViewById(R.id.editText_editname);
        //editTextName.setText(smartDevice.getDeviceName());
        final Switch enabledDisabled = findViewById(R.id.switch_deviceswitch);
        //enabledDisabled.setChecked(smartDevice.getActive());
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Remove focus and hide keyboard

                    textView.setText(editTextName.getText());
                    findViewById(R.id.editSmartDeviceLayout).requestFocus();

                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    handled = true;
                }
                return handled;
            }
        });

        Button confirmButton = findViewById(R.id.button_editSmartdeviceConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                Boolean active = enabledDisabled.isChecked();

                //smartDevice.setDeviceName(name);
                //smartDevice.setActive(active);

                //UserPreference.updateSmartDevice(getApplicationContext(), smartDevice);
                finish();
            }
        });
    }

    @Override
    public void handleError(Throwable throwable)
    {
        // handle error
    }
    public static Intent newIntent(android.content.Context context) {
        Intent intent = new Intent(context, EditSmartDeviceActivity.class);
        return intent;
    }


    public int getBindingVariable() {
        return BR.viewModel;
    }


    public int getLayoutId() {
        return R.layout.activity_edit_smart_device;
    }

    @Override
    public AddSmartDeviceViewModel getViewModel() {
        mAddSmartDeviceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddSmartDeviceViewModel.class);
        return mAddSmartDeviceViewModel;
    }

    public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
        return true;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

}

