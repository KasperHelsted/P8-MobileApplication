package p8project.sw801.ui.Settings.Location.EditLocation;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityEditLocationSettingBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;

/**
 * Created by clubd on 22-03-2018.
 */

public class EditLocationSettingActivity extends BaseActivity<ActivityEditLocationSettingBinding,EditLocationViewModel> implements EditLocationNavigator, HasSupportFragmentInjector {
    private Bundle addressBundle;
    private Address address;
    private TextView addressTextView;
    private TextView nameTextView;
    private Button confirmButton;

    private ActivityEditLocationSettingBinding mActivityEditLocationSettingBinding;
    @Inject
    EditLocationViewModel mEditLocationViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_location_setting;
    }

    @Override
    public EditLocationViewModel getViewModel() {
        return mEditLocationViewModel;
    }

    private String locationSettingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditLocationViewModel.setNavigator(this);
        mActivityEditLocationSettingBinding = getViewDataBinding();
        setUpBindings();
        

        Intent i = getIntent();
        locationSettingName = i.getStringExtra(locationSettingName);

        final TextView textView = findViewById(R.id.textView_editlocationsettingname);
        textView.setText(locationSettingName);

        final EditText editTextName = findViewById(R.id.textInputLocationName);
        editTextName.setText(locationSettingName);

        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Remove focus and hide keyboard

                    textView.setText(editTextName.getText());
                    findViewById(R.id.editLocationSettingLayout).requestFocus();

                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    handled = true;
                }
                return handled;
            }
        });
    }

    private void setUpBindings() {
        addressTextView  = mActivityEditLocationSettingBinding.addLocation;
        confirmButton = mActivityEditLocationSettingBinding.buttonEditLocationSettingConfirm;
        nameTextView = mActivityEditLocationSettingBinding.textViewChangeLocation;
    }

    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(EditLocationSettingActivity.this);
        startActivityForResult(intent,13);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submitEditEventClick() {
        String locName = nameTextView.getText().toString();
        Address addressToSend = address;
        mEditLocationViewModel.submitLocationToDatabase(locName,addressToSend);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (13): {
                if (resultCode == Activity.RESULT_OK) {
                    addressBundle = data.getBundleExtra("address");
                    address = addressBundle.getParcelable("address");
                    addressTextView.setText(address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2));
                }
                break;
            }
        }
    }
}