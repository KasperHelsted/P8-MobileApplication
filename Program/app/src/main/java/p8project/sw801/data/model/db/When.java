package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

@Entity(indices = {
        @Index("coordinateId"),
        @Index("eventId"),
}, foreignKeys = {
        @ForeignKey(entity = Coordinate.class, parentColumns = "id", childColumns = "coordinateId", onDelete = CASCADE),
        @ForeignKey(entity = Event.class, parentColumns = "id", childColumns = "eventId", onDelete = CASCADE)
})
public class When implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "coordinateId")
    private Integer coordinateId;

    @ColumnInfo(name = "eventId")
    private Integer eventId;

    @ColumnInfo(name = "timeCondition")
    private Integer timeCondition;

    @ColumnInfo(name = "locationCondition")
    private Integer locationCondition;

    @ColumnInfo(name = "weekdays")
    private byte[] weekdays;

    @ColumnInfo(name = "startHour")
    private Integer startHour;

    @ColumnInfo(name = "startMinute")
    private Integer startMinute;

    @ColumnInfo(name = "endHour")
    private Integer endHour;

    @ColumnInfo(name = "endMinute")
    private Integer endMinute;

    @Ignore
    public When() {
    }

    public When(Integer coordinateId, Integer eventId, Integer timeCondition, Integer locationCondition, byte[] weekdays, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {
        this.coordinateId = coordinateId;
        this.eventId = eventId;
        this.timeCondition = timeCondition;
        this.locationCondition = locationCondition;
        this.weekdays = weekdays;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public byte[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(byte[] weekdays) {
        this.weekdays = weekdays;
    }

    public void setListWeekDays(List<Integer> list) throws IOException{
        // write to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        for (int element : list) {
            out.writeUTF(Integer.toString(element));
        }
        byte[] bytes = baos.toByteArray();
        setWeekdays(bytes);
    }

    public List<Integer> getListWeekDays() throws IOException{
        List<Integer> l = new ArrayList<>();

        byte[] bytes = getWeekdays();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream in = new DataInputStream(bais);
        while (in.available() > 0) {
            String element = in.readUTF();
            l.add(Integer.parseInt(element));

        }
        return l;
    }


    public Integer getTimeCondition() {
        return timeCondition;
    }

    // Position 0 = No Time condition choosen
    // Position 1 = Before this time
    // Position 2 = At this time
    // Position 3 = After this time
    // Position 4 = Between these times

    public void setTimeCondition(Integer timeCondition) {
        this.timeCondition = timeCondition;
    }

    public Integer getLocationCondition() {
        return locationCondition;
    }

    // Position 0 = No Location condition choosen
    // Position 1 = At Location
    // Position 2 = Near Location
    // Position 3 = Leaving Location
    // Position 4 = Predefined Location

    public void setLocationCondition(Integer locationCondition) {
        this.locationCondition = locationCondition;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(Integer coordinateId) {
        this.coordinateId = coordinateId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
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
        When that = (When) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.coordinateId.equals(that.coordinateId) && this.eventId.equals(that.eventId)
                && this.timeCondition.equals(that.timeCondition) &&
                this.locationCondition.equals(that.locationCondition) &&
                this.weekdays.equals(that.weekdays) && this.startHour.equals(that.startHour) &&
                this.startMinute.equals(that.startMinute) && this.endHour.equals(that.endHour) &&
                this.endMinute.equals(that.endMinute))
            return true;

        return false;
    }
}
