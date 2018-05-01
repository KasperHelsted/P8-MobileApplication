package p8project.sw801;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.local.dao.EventDao;
import p8project.sw801.data.local.dao.SmartDeviceDao;
import p8project.sw801.data.local.dao.TriggerDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Trigger;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TriggerDbUnitTest {
    private TriggerDao mTriggerDao;
    private EventDao mEventDao;
    private SmartDeviceDao mSmartDeviceDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mTriggerDao = mDb.triggerDao();
        mEventDao = mDb.eventDao();
        mSmartDeviceDao = mDb.smartDeviceDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        // act
        this.mTriggerDao.insertAll(triggers);
        List<Trigger> dbTrigger = this.mTriggerDao.getAll();

        // assert
        assertEquals(dbTrigger.get(0).getId(), trigger.getId());
        assertEquals(dbTrigger.get(1).getId(), trigger1.getId());
    }

    @Test
    public void testLoadAllByIdsTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        Integer[] ids = new Integer[2];
        ids[0] = trigger.getId();
        ids[1] = trigger1.getId();

        // act
        this.mTriggerDao.insertAll(triggers);
        List<Trigger> dbTrigger = this.mTriggerDao.loadAllByIds(ids);

        // assert
        assertEquals(dbTrigger.get(0).getId(), trigger.getId());
        assertEquals(dbTrigger.get(1).getId(), trigger1.getId());
    }

    @Test
    public void testLoadByIdTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        // act
        this.mTriggerDao.insert(trigger);
        Trigger dbTrigger = this.mTriggerDao.loadById(1);

        // assert
        assertEquals(dbTrigger.getId(), trigger.getId());

    }

    @Test
    public void testCountTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        // act
        this.mTriggerDao.insertAll(triggers);
        Integer expected = 2;
        Integer actual = this.mTriggerDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadAllByEventIdTrigger() throws Exception {
        // arrange

        Event event = new Event();
        event.setId(1);
        this.mEventDao.insert(event);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setEventId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);
        trigger1.setEventId(1);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        // act
        this.mTriggerDao.insertAll(triggers);
        List<Trigger> dbTrigger = this.mTriggerDao.loadAllByEventId(1);

        // assert
        assertEquals(dbTrigger.get(0).getId(), trigger.getId());
        assertEquals(dbTrigger.get(1).getId(), trigger1.getId());
    }

    @Test
    public void testLoadAllBySmartDeviceIdTrigger() throws Exception {
        // arrange

        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        this.mSmartDeviceDao.insert(smartDevice);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setSmartDeviceId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);
        trigger1.setSmartDeviceId(1);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        // act
        this.mTriggerDao.insertAll(triggers);
        List<Trigger> dbTrigger = this.mTriggerDao.loadAllBySmartDeviceId(1);

        // assert
        assertEquals(dbTrigger.get(0).getId(), trigger.getId());
        assertEquals(dbTrigger.get(1).getId(), trigger1.getId());
    }

    @Test
    public void testDeleteTriggerByEventIdTrigger() throws Exception {
        // arrange

        Event event = new Event();
        event.setId(1);
        this.mEventDao.insert(event);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setEventId(1);

        // act
        this.mTriggerDao.insert(trigger);
        this.mTriggerDao.deleteTriggerByEventId(1);
        Integer expected = 0;
        Integer actual = this.mTriggerDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteTriggerBySmartDeviceIdTrigger() throws Exception {
        // arrange

        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        this.mSmartDeviceDao.insert(smartDevice);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setSmartDeviceId(1);

        // act
        this.mTriggerDao.insert(trigger);
        this.mTriggerDao.deleteTriggerBySmartDeviceId(1);
        Integer expected = 0;
        Integer actual = this.mTriggerDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testInsertAllTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        Trigger trigger1 = new Trigger();
        trigger1.setId(2);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);
        triggers.add(trigger1);

        // act
        this.mTriggerDao.insertAll(triggers);
        List<Trigger> dbTrigger = this.mTriggerDao.getAll();

        // assert
        assertEquals(dbTrigger.get(0).getId(), trigger.getId());
        assertEquals(dbTrigger.get(1).getId(), trigger1.getId());
    }

    @Test
    public void testInsertTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        // act
        this.mTriggerDao.insert(trigger);
        Trigger dbTrigger = this.mTriggerDao.loadById(1);

        // assert
        assertEquals(dbTrigger.getId(), trigger.getId());
    }

    @Test
    public void testUpdateTrigger() throws Exception {
        // arrange
        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        this.mSmartDeviceDao.insert(smartDevice);

        SmartDevice smartDevice2 = new SmartDevice();
        smartDevice2.setId(2);
        this.mSmartDeviceDao.insert(smartDevice2);

        Trigger trigger = new Trigger();
        trigger.setId(1);
        trigger.setSmartDeviceId(1);
        this.mTriggerDao.insert(trigger);
        Trigger dbTrigger = this.mTriggerDao.loadById(1);
        dbTrigger.setSmartDeviceId(2);

        // act
        this.mTriggerDao.update(dbTrigger);
        Trigger dbTrigger2 = this.mTriggerDao.loadById(1);
        Integer id = 2;

        // assert
        assertEquals(dbTrigger2.getSmartDeviceId(), id);
    }

    @Test
    public void testDeleteTrigger() throws Exception {
        // arrange

        Trigger trigger = new Trigger();
        trigger.setId(1);

        // act
        this.mTriggerDao.insert(trigger);
        this.mTriggerDao.delete(trigger);
        Integer expected = 0;
        Integer actual = this.mTriggerDao.count();

        // assert
        assertEquals(expected, actual);
    }

}
