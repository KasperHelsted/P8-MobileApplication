package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {
        @Index("chainId"),
}, foreignKeys = {
        @ForeignKey(entity = Chain.class, parentColumns = "id", childColumns = "chainId", onDelete = CASCADE)
})
public class Store {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "chainId")
    private Integer chainId;

    @ColumnInfo(name = "storeName")
    private String storeName;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @Ignore
    public Store() {
    }

    public Store(Integer chainId, String storeName, double latitude, double longitude) {
        this.chainId = chainId;
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
        Store that = (Store) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.chainId.equals(that.chainId) && this.storeName.equals(that.storeName) && this.latitude == that.latitude && this.longitude == that.longitude)
            return true;

        return false;
    }
}



