package p8project.sw801.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import p8project.sw801.data.local.Converter.DataTypeConverter;
import p8project.sw801.data.model.db.Smartdevice.Controllers.*;
import p8project.sw801.data.model.db.Smartdevice.Accessories.*;
import p8project.sw801.data.local.dao.*;
import p8project.sw801.data.model.db.*;
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

    public abstract AccessoriesDao accessoriesDao();

}
