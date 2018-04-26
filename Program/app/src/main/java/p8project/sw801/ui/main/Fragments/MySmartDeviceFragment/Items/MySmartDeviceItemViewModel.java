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

package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.Items;

import android.databinding.ObservableField;

import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class MySmartDeviceItemViewModel {

    public final ObservableField<String> deviceName;

    public final ObservableField<String> active;

    public final ObservableField<Integer> id;

    private final MySmartDeviceItemViewModelListener mListener;

    private final SmartDevice mMySmartDevice;

    public MySmartDeviceItemViewModel(SmartDevice mySmartDevice, MySmartDeviceItemViewModelListener listener) {
        this.mMySmartDevice = mySmartDevice;
        this.mListener = listener;

        deviceName = new ObservableField<>(mySmartDevice.getDeviceName());
        active = new ObservableField<>(mySmartDevice.getActive().toString());
        id = new ObservableField<>(mySmartDevice.getId());
    }

    public void onItemClick() {
        mListener.onItemClick(mMySmartDevice);
    }

    public interface MySmartDeviceItemViewModelListener {

        void onItemClick(SmartDevice smartDevice);
    }
}
