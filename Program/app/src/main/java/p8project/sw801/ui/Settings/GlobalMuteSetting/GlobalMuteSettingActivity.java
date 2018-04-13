package p8project.sw801.ui.Settings.GlobalMuteSetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityGlobalMuteBinding;

import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;

import p8project.sw801.ui.base.BaseActivity;

public class GlobalMuteSettingActivity extends BaseActivity<ActivityGlobalMuteBinding, GlobalMuteSettingViewModel> implements GlobalMuteSettingNavigator {

    @Inject
    GlobalMuteSettingViewModel mGlobalMuteSettingViewModel;
    private ActivityGlobalMuteBinding mActivityGlobalMuteBinding;

    private ListView listview;
    ArrayList<String> globalMuteSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityGlobalMuteBinding = getViewDataBinding();
        mGlobalMuteSettingViewModel.setNavigator(this);



        listview = (ListView) this.findViewById(R.id.listView_myglobalmutesettings);

        //------Creation of list of smart devices
        globalMuteSettings = new ArrayList<String>();
        globalMuteSettings.add("Off at home");

        GlobalMuteSettingAdapter myAdapter = new GlobalMuteSettingAdapter(this, globalMuteSettings);


        listview.setAdapter(myAdapter);
        //------Creation of list of smart devices

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
}