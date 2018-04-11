package p8project.sw801.data.local.RelationEntity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

public class EventWithData {
    @Embedded
    public Event event;

    @Relation(parentColumn = "id", entityColumn = "eventId", entity = When.class)
    public List<WhenWithCoordinate> whens;

    @Relation(parentColumn = "id", entityColumn = "eventId", entity = Trigger.class)
    public List<TriggerWithSmartDevice> triggers;
}