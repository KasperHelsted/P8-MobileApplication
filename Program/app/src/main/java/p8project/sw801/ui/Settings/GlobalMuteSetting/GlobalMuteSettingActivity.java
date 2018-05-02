package p8project.sw801.ui.Settings.GlobalMuteSetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.databinding.ActivityGlobalMuteBinding;

import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;

import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.MyEventAdapter;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragment;

public class GlobalMuteSettingActivity extends BaseActivity<ActivityGlobalMuteBinding, GlobalMuteSettingViewModel> implements GlobalMuteSettingNavigator {

    @Inject
    GlobalMuteSettingViewModel mGlobalMuteSettingViewModel;
    private ActivityGlobalMuteBinding mActivityGlobalMuteBinding;

    private ListView listview;
    ArrayList<GlobalMute> globalMuteSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGlobalMuteBinding = getViewDataBinding();
        mGlobalMuteSettingViewModel.setNavigator(this);
        setUp();
    }

    public void updatelist(){
        setUp();
    }

    public void deleteGlobalMute(GlobalMute globalMute){
        new AlertDialog.Builder(this)
                .setTitle("Delete Globalmute")
                .setMessage("Do you really want to delete the Global mute: " + globalMute.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) ->mGlobalMuteSettingViewModel.deleteGlobalMute(globalMute))
                .setNegativeButton(android.R.string.no, null).show();
    }

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

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openAddGlobalMuteSettingActivity() {
        Intent intent = AddGlobalMuteSettingActivity.newIntent(GlobalMuteSettingActivity.this);
        startActivityForResult(intent,0);
    }

    private void setUp(){
        listview = (ListView) mActivityGlobalMuteBinding.listViewMyglobalmutesettings;

        //------Creation of list of smart devices
        globalMuteSettings = new ArrayList<GlobalMute>();
        globalMuteSettings.addAll(mGlobalMuteSettingViewModel.getGlobalMuteObservableList());

        if (globalMuteSettings == null){

        }
        else{
            GlobalMuteSettingAdapter myAdapter = new GlobalMuteSettingAdapter(this, globalMuteSettings);
            listview.setAdapter(myAdapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlobalMuteSettingViewModel.getListFromDb();
    }
}