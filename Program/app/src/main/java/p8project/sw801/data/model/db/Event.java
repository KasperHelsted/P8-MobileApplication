package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "Active")
    @NonNull
    private Boolean active;

    @Ignore
    public Event() {
    }

    public Event(String name, Boolean active) {
        this.name = name;
        this.active = active;
    }

    public int gethashcode(){
        int hash = 1;
        hash = hash * 17 + id;
        hash = hash * 31 + name.hashCode();
        return hash;
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

    public Boolean getActive() {return active;}

    public void setActive(Boolean active) {this.active = active;}

    @Override
    public boolean equals(Object object) {
        // Checks if object is from same instance
        if (this == object)
            return true;

        // Checks if object is null or if the two classes is of same type
        if (object == null || getClass() != object.getClass())
            return false;

        // We can assume that the object we are comparing is of same type so we can cast
        Event that = (Event) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.name.equals(that.name) && this.active.equals(that.active))
            return true;

        return false;
    }
}
