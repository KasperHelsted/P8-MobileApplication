package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

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
public class When {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "coordinateId")
    private Integer coordinateId;

    @ColumnInfo(name = "eventId")
    private Integer eventId;

    @ColumnInfo(name = "radius")
    private Integer radius;

    @ColumnInfo(name = "timeCondition")
    private Integer timeCondition;
    @ColumnInfo(name = "locationCondition")
    private Integer locationCondition;
    @ColumnInfo(name = "weekdays")
    private List<Integer> weekdays;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "startTime")
    private Long startTime;
    @ColumnInfo(name = "endTime")
    private Long endTime;

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

    public List<Integer> getWeekdays() {
        return weekdays;
    }


    public void setWeekdays(List<Integer> weekdays) {
        this.weekdays = weekdays;
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

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
