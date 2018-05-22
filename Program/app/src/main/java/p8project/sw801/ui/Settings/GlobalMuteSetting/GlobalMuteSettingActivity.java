package p8project.sw801.ui.Settings.GlobalMuteSetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.databinding.ActivityGlobalMuteBinding;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;
import p8project.sw801.ui.base.BaseActivity;

public class GlobalMuteSettingActivity extends BaseActivity<ActivityGlobalMuteBinding, GlobalMuteSettingViewModel> implements GlobalMuteSettingNavigator {

    /**
     * MVVM setup
     */
    @Inject
    GlobalMuteSettingViewModel mGlobalMuteSettingViewModel;
    ArrayList<GlobalMute> globalMuteSettings;
    private ActivityGlobalMuteBinding mActivityGlobalMuteBinding;
    private ListView listview;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_global_mute;
    }

    @Override
    public GlobalMuteSettingViewModel getViewModel() {
        return mGlobalMuteSettingViewModel;
    }


    /**
     * Setup of the activity onCreation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGlobalMuteBinding = getViewDataBinding();
        mGlobalMuteSettingViewModel.setNavigator(this);
        setUp();
    }

    /**
     * updates the list of global mutes
     */
    public void updatelist() {
        setUp();
    }

    /**
     * Asks the user if they are sure they want to delete a global mute and responds
     *
     * @param globalMute globalmute to delete
     */
    public void deleteGlobalMute(GlobalMute globalMute) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Globalmute")
                .setMessage("Do you really want to delete the Global mute: " + globalMute.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> mGlobalMuteSettingViewModel.deleteGlobalMute(globalMute))
                .setNegativeButton(android.R.string.no, null).show();
    }


    /**
     * Opens the addglobalmute page
     */
    @Override
    public void openAddGlobalMuteSettingActivity() {
        Intent intent = AddGlobalMuteSettingActivity.newIntent(GlobalMuteSettingActivity.this);
        startActivityForResult(intent, 0);
    }

    /**
     * Setup of MVVM + update of list of globalmutes
     */
    private void setUp() {
        listview = mActivityGlobalMuteBinding.listViewMyglobalmutesettings;

        //------Creation of list of smart devices
        globalMuteSettings = new ArrayList<GlobalMute>();
        globalMuteSettings.addAll(mGlobalMuteSettingViewModel.getGlobalMuteObservableList());

        if (globalMuteSettings == null) {

        } else {
            GlobalMuteSettingAdapter myAdapter = new GlobalMuteSettingAdapter(this, globalMuteSettings);
            listview.setAdapter(myAdapter);
        }

    }

    /**
     * OnResume -> Update data
     */
    @Override
    protected void onResume() {
        super.onResume();
        mGlobalMuteSettingViewModel.getListFromDb();
    }
}