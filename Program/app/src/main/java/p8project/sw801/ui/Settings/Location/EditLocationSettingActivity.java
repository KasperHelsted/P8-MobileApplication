package p8project.sw801.ui.Settings.Location;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
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
import p8project.sw801.ui.MapsActivity;
import p8project.sw801.ui.Settings.SettingsNavigator;
import p8project.sw801.ui.Settings.SettingsViewModel;
import p8project.sw801.ui.base.BaseActivity;

/**
 * Created by clubd on 22-03-2018.
 */

public class EditLocationSettingActivity extends BaseActivity<ActivityEditLocationSettingBinding,SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
    private ActivityEditLocationSettingBinding mActivityEditLocationSettingBinding;
    private SettingsViewModel mSettingsViewModel;
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
    public SettingsViewModel getViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingsViewModel.class);
        return mSettingsViewModel;
    }

    private String locationSettingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location_setting);
        mSettingsViewModel.setNavigator(this);
        mActivityEditLocationSettingBinding = getViewDataBinding();

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


        Button confirmButton = findViewById(R.id.button_editLocationSettingConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();

                //TODO change in database

                finish();
            }
        });

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}