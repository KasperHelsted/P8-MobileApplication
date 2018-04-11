package p8project.sw801.data.model.Others;


import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.dao.SmartDeviceDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.SmartDevice;

public abstract class UserPreference {
    public static LiveData<List<GlobalMute>> getGlobalMuteList(Context currentContext) {
        //return AppDatabase.getInstance(currentContext).globalMuteDao().getAll();
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public static void addToGlobalMuteList(Context currentContext, GlobalMute globalMute) {
        final GlobalMuteDao globalMuteDao = AppDatabase.getInstance(currentContext).globalMuteDao();

        new AsyncTask<GlobalMute, Void, Void>() {
            @Override
            protected Void doInBackground(GlobalMute... globalMutes) {
                globalMuteDao.insertAll(globalMutes);

                return null;
            }

        }.execute(globalMute);
    }

    @SuppressLint("StaticFieldLeak")
    public static void removeFromGlobalMuteList(Context currentContext, GlobalMute globalMute) {
        final GlobalMuteDao globalMuteDao = AppDatabase.getInstance(currentContext).globalMuteDao();

        new AsyncTask<GlobalMute, Void, Void>() {
            @Override
            protected Void doInBackground(GlobalMute... globalMutes) {
                for (GlobalMute globalMute : globalMutes) {
                    globalMuteDao.delete(globalMute);
                }

                return null;
            }

        }.execute(globalMute);
    }

    public static LiveData<List<PredefinedLocation>> getPredefinedLocationList(Context currentContext) {
        return null;
        //return AppDatabase.getInstance(currentContext).predefinedLocationDao().getAll();
    }

    @SuppressLint("StaticFieldLeak")
    public static void addToPredefinedLocationList(Context currentContext, PredefinedLocation predefinedLocation) {
        final PredefinedLocationDao predefinedLocationDao = AppDatabase.getInstance(currentContext).predefinedLocationDao();

        new AsyncTask<PredefinedLocation, Void, Void>() {
            @Override
            protected Void doInBackground(PredefinedLocation... predefinedLocations) {
                predefinedLocationDao.insertAll(predefinedLocations);

                return null;
            }

        }.execute(predefinedLocation);
    }

    @SuppressLint("StaticFieldLeak")
    public static void removeFromPredefinedLocationList(Context currentContext, PredefinedLocation predefinedLocation) {
        final PredefinedLocationDao predefinedLocationDao = AppDatabase.getInstance(currentContext).predefinedLocationDao();

        new AsyncTask<PredefinedLocation, Void, Void>() {
            @Override
            protected Void doInBackground(PredefinedLocation... predefinedLocations) {
                for (PredefinedLocation predefinedLocation : predefinedLocations) {
                    predefinedLocationDao.delete(predefinedLocation);
                }

                return null;
            }

        }.execute(predefinedLocation);
    }

    public static List<SmartDevice> getSmartDeviceList(Context currentContext) {
        return AppDatabase.getInstance(currentContext).smartDeviceDao().getAll();
    }

    public static SmartDevice getSmartDeviceById(Context currentContext, Integer id) {
        return AppDatabase.getInstance(currentContext).smartDeviceDao().loadById(id);
    }

    @SuppressLint("StaticFieldLeak")
    public static void updateSmartDevice(Context currentContext, SmartDevice smartDevice) {
        final SmartDeviceDao smartDeviceDao = AppDatabase.getInstance(currentContext).smartDeviceDao();

        new AsyncTask<SmartDevice, Void, Void>() {
            @Override
            protected Void doInBackground(SmartDevice... smartDevices) {
                for (SmartDevice smartDevice : smartDevices) {
                    smartDeviceDao.update(smartDevice);
                }

                return null;
            }

        }.execute(smartDevice);
    }


    @SuppressLint("StaticFieldLeak")
    public static void addToSmartDeviceList(Context currentContext, SmartDevice smartDevice) {
        final SmartDeviceDao smartDeviceDao = AppDatabase.getInstance(currentContext).smartDeviceDao();

        new AsyncTask<SmartDevice, Void, Void>() {
            @Override
            protected Void doInBackground(SmartDevice... smartDevices) {
                smartDeviceDao.insertAll(smartDevices);

                return null;
            }

        }.execute(smartDevice);
    }

    @SuppressLint("StaticFieldLeak")
    public static void removeFromSmartDeviceList(Context currentContext, SmartDevice smartDevice) {
        final SmartDeviceDao smartDeviceDao = AppDatabase.getInstance(currentContext).smartDeviceDao();

        new AsyncTask<SmartDevice, Void, Void>() {
            @Override
            protected Void doInBackground(SmartDevice... smartDevices) {
                for (SmartDevice smartDevice : smartDevices) {
                    smartDeviceDao.delete(smartDevice);
                }

                return null;
            }

        }.execute(smartDevice);
    }



    /*
    private ArrayList<PreferredShopping> PrefShopList;


    public ArrayList<PreferredShopping> getPrefShopList() {
        return PrefShopList;
    }

    public void setPrefShopList(PreferredShopping prefShop) {
        PrefShopList.add(prefShop);
    }
    */
}