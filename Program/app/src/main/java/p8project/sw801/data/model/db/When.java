package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
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
    private int startHour;

    @ColumnInfo(name = "startMinute")
    private int startMinute;

    @ColumnInfo(name = "endHour")
    private int endHour;

    @ColumnInfo(name = "endMinute")
    private int endMinute;

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

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
