package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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
}
