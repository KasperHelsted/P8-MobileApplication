package p8project.sw801.ui.SmartDevice.EditSmartDevice;

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

import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.databinding.ActivityEditSmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceNavigator;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceViewModel;
import p8project.sw801.ui.base.BaseActivity;

public class EditSmartDeviceActivity extends BaseActivity<ActivityEditSmartDeviceBinding, EditSmartDeviceViewModel> implements EditSmartDeviceNavigator{

    @Inject
    EditSmartDeviceViewModel mEditSmartDeviceViewModel;
    private ActivityEditSmartDeviceBinding mActivityEditSmartDeviceBinding;

    private TextView greyedOutDeviceName;
    private EditText inputName;
    private SmartDevice mSmartdevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEditSmartDeviceBinding = getViewDataBinding();
        mEditSmartDeviceViewModel.setNavigator(this);
        setUp();
    }

    private void setUp(){
        greyedOutDeviceName = mActivityEditSmartDeviceBinding.textViewEditdeviceName;
        inputName = mActivityEditSmartDeviceBinding.editTextEditname;

        mSmartdevice = fetchData();

        greyedOutDeviceName.setText(mSmartdevice.getDeviceName());
        inputName.setText(mSmartdevice.getDeviceName());


        //Change grey Title as the user writes a new name
        inputName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Remove focus and hide keyboard

                    greyedOutDeviceName.setText(inputName.getText());
                    mActivityEditSmartDeviceBinding.editSmartDeviceLayout.requestFocus();

                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    handled = true;
                }
                return handled;
            }
        });

        Button confirmButton = mActivityEditSmartDeviceBinding.buttonEditSmartdeviceConfirm;
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
    public EditSmartDeviceViewModel getViewModel() {
        return mEditSmartDeviceViewModel;
    }

    private SmartDevice fetchData(){
        String jsonMyObject ="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("device");
        }
        SmartDevice myObject = new Gson().fromJson(jsonMyObject, SmartDevice.class);
        return myObject;
    }

    @Override
    public void confirmPress(){
        mSmartdevice.setDeviceName(inputName.getText().toString());
        mEditSmartDeviceViewModel.saveToDb(mSmartdevice);
    }

    @Override
    public void close(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}

