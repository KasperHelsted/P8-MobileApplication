package p8project.sw801.ui.main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragment;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragment;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;

    public SectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 3;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return MyEventsFragment.newInstance();
            case 2:
                return MySmartDeviceFragment.newInstance();
            default:
                return null;
        }
    }

}