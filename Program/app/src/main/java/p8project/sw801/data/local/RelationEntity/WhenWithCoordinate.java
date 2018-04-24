package p8project.sw801.data.local.RelationEntity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;
import java.util.Set;

import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

public class WhenWithCoordinate {
    @Embedded
    public When when;

    @Relation(parentColumn = "coordinateId", entityColumn = "id", entity = Coordinate.class)
    public List<Coordinate> coordinate;
}