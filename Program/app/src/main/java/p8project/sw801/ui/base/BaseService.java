package p8project.sw801.ui.base;

import android.arch.persistence.room.Room;
import android.content.Context;

import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.utils.AppConstants;

public class BaseService {
    public AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                AppConstants.DB_NAME
        ).fallbackToDestructiveMigration().build();
    }
}
