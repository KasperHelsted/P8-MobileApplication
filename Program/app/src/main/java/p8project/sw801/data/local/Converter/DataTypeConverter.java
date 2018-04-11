package p8project.sw801.data.local.Converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

public class DataTypeConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
