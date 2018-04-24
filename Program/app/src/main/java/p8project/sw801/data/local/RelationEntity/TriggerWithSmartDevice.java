package p8project.sw801.data.local.RelationEntity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;
import java.util.Set;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Trigger;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

public class TriggerWithSmartDevice {
    @Embedded
    public Trigger trigger;

    @Relation(parentColumn = "smartDeviceId", entityColumn = "id", entity = SmartDevice.class)
    public List<SmartDevice> smartDevice;
}
