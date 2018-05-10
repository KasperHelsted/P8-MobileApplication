package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity(indices = {
        @Index("coordinateId"),
}, foreignKeys = @ForeignKey(
        entity = Coordinate.class, parentColumns = "id", childColumns = "coordinateId", onDelete = CASCADE
))
public class PredefinedLocation {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "coordinateId")
    private Integer coordinateId;

    public PredefinedLocation() {
    }

    public PredefinedLocation(String name) {
        this.name = name;
    }

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

    public Integer getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(Integer coordinateId) {
        this.coordinateId = coordinateId;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object object) {
        // Checks if object is from same instance
        if (this == object)
            return true;

        // Checks if object is null or if the two classes is of same type
        if (object == null || getClass() != object.getClass())
            return false;

        // We can assume that the object we are comparing is of same type so we can cast
        PredefinedLocation that = (PredefinedLocation) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.name.equals(that.name) && this.coordinateId.equals(that.coordinateId))
            return true;

        return false;
    }
}
