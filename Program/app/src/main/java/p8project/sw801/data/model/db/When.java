package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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

    @ColumnInfo(name = "intCondition")
    private Integer intCondition;

    @ColumnInfo(name = "weekday")
    private Integer weekday;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "startTime")
    private Long startTime;

    @ColumnInfo(name = "endTime")
    private Long endTime;

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

    public Condition getCondition() {
        return Condition.values()[intCondition];
    }

    public void setCondition(Condition condition) {
        this.intCondition = condition.ordinal();
    }


    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
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

    public Integer getIntCondition() {
        return intCondition;
    }

    public void setIntCondition(Integer intCondition) {
        this.intCondition = intCondition;
    }

    public enum Condition {
        I_ARRIVE(1),
        I_LEAVE(2),
        I_AM_NEAR(3),
        I_AM_AT(4),
        NOT_AT_LOCATION(5);

        private int code;

        Condition(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
