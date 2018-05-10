package p8project.sw801.UnitTests.DbUnitTest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.dao.Accessories.HueLightbulbWhiteDao;
import p8project.sw801.data.local.dao.CoordinateDao;
import p8project.sw801.data.local.dao.EventDao;
import p8project.sw801.data.local.dao.EventWithDataDao;
import p8project.sw801.data.local.dao.SmartDeviceDao;
import p8project.sw801.data.local.dao.TriggerDao;
import p8project.sw801.data.local.dao.WhenDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EventWithDataDbUnitTest {
    private EventWithDataDao mEventWithDataDao;
    private EventDao mEventDao;
    private TriggerDao mTriggerDao;
    private WhenDao mWhenDao;
    private CoordinateDao mCoordinateDao;
    private HueLightbulbWhiteDao mHueLightbulbWhiteDao;
    private SmartDeviceDao mSmartDeviceDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mEventWithDataDao = mDb.eventWithDataDao();
        mEventDao = mDb.eventDao();
        mTriggerDao = mDb.triggerDao();
        mWhenDao = mDb.whenDao();
        mCoordinateDao = mDb.coordinateDao();
        mHueLightbulbWhiteDao = mDb.hueLightbulbWhiteDao();
        mSmartDeviceDao = mDb.smartDeviceDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetEventsWithData() throws Exception {
        // arrange

        Event event = new Event();
        event.setId(1);
        this.mEventDao.insert(event);

        Coordinate coordinate = new Coordinate(10, 10);
        coordinate.setId(1);
        this.mCoordinateDao.insert(coordinate);

        Coordinate coordinate1 = new Coordinate(20, 20);
        coordinate1.setId(2);
        this.mCoordinateDao.insert(coordinate1);

        When when = new When();
        when.setId(1);
        when.setEventId(1);
        when.setCoordinateId(1);
        this.mWhenDao.insert(when);


        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        this.mSmartDeviceDao.insert(smartDevice);

        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setId(2);
        this.mSmartDeviceDao.insert(smartDevice1);

        HueLightbulbWhite bulb = new HueLightbulbWhite();
        bulb.setDeviceName("Kitchen");
        bulb.setSmartDeviceId(1);
        this.mHueLightbulbWhiteDao.insertWhiteHueLightbulb(bulb);

        HueLightbulbWhite bulb1 = new HueLightbulbWhite();
        bulb1.setDeviceName("Living room");
        bulb1.setSmartDeviceId(2);
        this.mHueLightbulbWhiteDao.insertWhiteHueLightbulb(bulb1);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setEventId(1);
        trigger.setSmartDeviceId(1);
        this.mTriggerDao.insert(trigger);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);
        trigger1.setEventId(1);
        trigger1.setSmartDeviceId(2);
        this.mTriggerDao.insert(trigger1);

        Event event1 = new Event();
        event1.setId(2);
        this.mEventDao.insert(event1);

        When when1 = new When();
        when1.setId(2);
        when1.setEventId(2);
        when1.setCoordinateId(2);
        this.mWhenDao.insert(when1);

        // act
        List<EventWithData> dbEventWithData = this.mEventWithDataDao.getEventsWithData();

        // assert
        assertEquals(dbEventWithData.get(0).event.getId(), event.getId());
        assertEquals(dbEventWithData.get(1).event.getId(), event1.getId());
        assertEquals(dbEventWithData.get(0).whens.get(0).coordinate.get(0).getLongitude(), coordinate.getLongitude(), 0);
        assertEquals(dbEventWithData.get(0).triggers.get(0).smartDeviceWithDataList.get(0).hueLightbulbWhiteList.get(0).getDeviceName(), bulb. getDeviceName());
        assertEquals(dbEventWithData.get(0).triggers.get(1).smartDeviceWithDataList.get(0).hueLightbulbWhiteList.get(0).getDeviceName(), bulb1. getDeviceName());


    }

    @Test
    public void testGetEventWithData() throws Exception {
        // arrange

        Event event = new Event();
        event.setId(1);
        this.mEventDao.insert(event);

        Coordinate coordinate = new Coordinate(10, 10);
        coordinate.setId(1);
        this.mCoordinateDao.insert(coordinate);

        Coordinate coordinate1 = new Coordinate(20, 20);
        coordinate1.setId(2);
        this.mCoordinateDao.insert(coordinate1);

        When when = new When();
        when.setId(1);
        when.setEventId(1);
        when.setCoordinateId(1);
        this.mWhenDao.insert(when);


        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        this.mSmartDeviceDao.insert(smartDevice);

        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setId(2);
        this.mSmartDeviceDao.insert(smartDevice1);

        HueLightbulbWhite bulb = new HueLightbulbWhite();
        bulb.setDeviceName("Kitchen");
        bulb.setSmartDeviceId(1);
        this.mHueLightbulbWhiteDao.insertWhiteHueLightbulb(bulb);

        HueLightbulbWhite bulb1 = new HueLightbulbWhite();
        bulb1.setDeviceName("Living room");
        bulb1.setSmartDeviceId(2);
        this.mHueLightbulbWhiteDao.insertWhiteHueLightbulb(bulb1);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setEventId(1);
        trigger.setSmartDeviceId(1);
        this.mTriggerDao.insert(trigger);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);
        trigger1.setEventId(1);
        trigger1.setSmartDeviceId(2);
        this.mTriggerDao.insert(trigger1);

        // act
        EventWithData dbEventWithData = this.mEventWithDataDao.getEventWithData(1);

        // assert
        assertEquals(dbEventWithData.event.getId(), event.getId());
        assertEquals(dbEventWithData.whens.get(0).coordinate.get(0).getLongitude(), coordinate.getLongitude(), 0);
        assertEquals(dbEventWithData.triggers.get(0).smartDeviceWithDataList.get(0).hueLightbulbWhiteList.get(0).getDeviceName(), bulb. getDeviceName());
        assertEquals(dbEventWithData.triggers.get(1).smartDeviceWithDataList.get(0).hueLightbulbWhiteList.get(0).getDeviceName(), bulb1. getDeviceName());
    }


}
