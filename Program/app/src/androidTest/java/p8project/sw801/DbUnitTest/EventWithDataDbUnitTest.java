package p8project.sw801.DbUnitTest;

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

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.dao.EventDao;
import p8project.sw801.data.local.dao.EventWithDataDao;
import p8project.sw801.data.local.dao.TriggerDao;
import p8project.sw801.data.local.dao.WhenDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EventWithDataDbUnitTest {
    private EventWithDataDao mEventWithDataDao;
    private EventDao mEventDao;
    private TriggerDao mTriggerDao;
    private WhenDao mWhenDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mEventWithDataDao = mDb.eventWithDataDao();
        mEventDao = mDb.eventDao();
        mTriggerDao = mDb.triggerDao();
        mWhenDao = mDb.whenDao();
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

        Event event1 = new Event();
        event1.setId(2);
        this.mEventDao.insert(event1);


        // act
        List<EventWithData> dbEventWithData = this.mEventWithDataDao.getEventsWithData();

        // assert
        assertEquals(dbEventWithData.get(0).event.getId(), event.getId());
        assertEquals(dbEventWithData.get(1).event.getId(), event1.getId());
    }

    @Test
    public void testGetEventWithData() throws Exception {
        // arrange

        Event event = new Event();
        event.setId(1);
        this.mEventDao.insert(event);

        // act
        EventWithData dbEventWithData = this.mEventWithDataDao.getEventWithData(1);

        // assert
        assertEquals(dbEventWithData.event.getId(), event.getId());
    }


}
