package p8project.sw801.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import p8project.sw801.data.local.Converter.DataTypeConverter;
import p8project.sw801.data.local.dao.Accessories.HueBridgeDao;
import p8project.sw801.data.local.dao.Accessories.HueLightbulbRGBDao;
import p8project.sw801.data.local.dao.Accessories.HueLightbulbWhiteDao;
import p8project.sw801.data.local.dao.Accessories.NestHubDao;
import p8project.sw801.data.local.dao.Accessories.NestThermostatDao;
import p8project.sw801.data.local.dao.ChainDao;
import p8project.sw801.data.local.dao.CoordinateDao;
import p8project.sw801.data.local.dao.EventDao;
import p8project.sw801.data.local.dao.EventWithDataDao;
import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.dao.SmartDeviceDao;
import p8project.sw801.data.local.dao.StoreDao;
import p8project.sw801.data.local.dao.TriggerDao;
import p8project.sw801.data.local.dao.WhenDao;
import p8project.sw801.data.model.db.Chain;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbRGB;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.data.model.db.Store;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 3/15/2018.
 */

@Database(entities = {
        Coordinate.class,
        Event.class,
        When.class,
        Trigger.class,
        GlobalMute.class,
        PredefinedLocation.class,
        SmartDevice.class,
        HueBridge.class,
        NestHub.class,
        NestThermostat.class,
        HueLightbulbRGB.class,
        HueLightbulbWhite.class,
        Store.class,
        Chain.class
}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "database";

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DATABASE_NAME
        ).fallbackToDestructiveMigration().build();
    }


    public abstract CoordinateDao coordinateDao();

    public abstract EventDao eventDao();

    public abstract GlobalMuteDao globalMuteDao();

    public abstract PredefinedLocationDao predefinedLocationDao();

    public abstract SmartDeviceDao smartDeviceDao();

    public abstract EventWithDataDao eventWithDataDao();

    public abstract TriggerDao triggerDao();

    public abstract WhenDao whenDao();

    public abstract ChainDao chainDao();

    public abstract StoreDao storeDao();

    public abstract HueBridgeDao hueBridgeDao();

    public abstract HueLightbulbRGBDao hueLightbulbRGBDao();

    public abstract HueLightbulbWhiteDao hueLightbulbWhiteDao();

    public abstract NestHubDao nestHubDao();

    public abstract NestThermostatDao nestThermostatDao();


}
