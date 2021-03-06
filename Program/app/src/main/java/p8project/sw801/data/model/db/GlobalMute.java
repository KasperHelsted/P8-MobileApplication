package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity(indices = {
        @Index("predefinedLocationId"),
}, foreignKeys = {
        @ForeignKey(entity = PredefinedLocation.class, parentColumns = "id", childColumns = "predefinedLocationId", onDelete = CASCADE)
})
public class GlobalMute {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "startTime")
    private Long startTime;

    @ColumnInfo(name = "endTime")
    private Long endTime;

    @ColumnInfo(name = "predefinedLocationId")
    private Integer predefinedLocationId;

    @ColumnInfo(name = "note")
    private String note;

    @Ignore
    public GlobalMute() {
    }

    public GlobalMute(String name, Long startTime, Long endTime, Integer predefinedLocationId, String note) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.predefinedLocationId = predefinedLocationId;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPredefinedLocationId() {
        return predefinedLocationId;
    }

    public void setPredefinedLocationId(Integer predefinedLocationId) {
        this.predefinedLocationId = predefinedLocationId;
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
        GlobalMute that = (GlobalMute) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.name.equals(that.name) && this.startTime.equals(that.startTime) && this.endTime.equals(that.endTime) && this.note.equals(that.note))
            return true;

        return false;
    }

}
