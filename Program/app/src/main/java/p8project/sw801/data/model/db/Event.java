package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "IntentId")
    private String IntentId;

    @ColumnInfo(name = "AlarmId")
    private String AlarmId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntentId() {
        return IntentId;
    }

    public void setIntentId(String intentId) {
        IntentId = intentId;
    }

    public String getAlarmId() {
        return AlarmId;
    }

    public void setAlarmId(String alarmID) {
        AlarmId = alarmID;
    }
}
