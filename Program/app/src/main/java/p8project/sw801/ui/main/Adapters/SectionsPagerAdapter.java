/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package p8project.sw801.ui.main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragment;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragment;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;

/**
 * Created by amitshekhar on 10/07/17.
 */
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