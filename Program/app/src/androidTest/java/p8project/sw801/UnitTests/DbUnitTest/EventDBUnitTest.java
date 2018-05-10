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

import p8project.sw801.data.local.dao.EventDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Event;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EventDBUnitTest {
    private EventDao mEventDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mEventDao = mDb.eventDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetLastEvent() throws Exception{
        // arrange
        Event event = new Event();
        event.setName("testGetLastEvent");
        event.setActive(true);
        this.mEventDao.insert(event);

        // act
        Event getLastEvent = this.mEventDao.getLastEvent();

        // assert
        assertEquals(getLastEvent.getName(), event.getName());

    }

    @Test
    public void testInsertEvent() throws Exception {
        // arrange
        Event event = new Event();
        event.setActive(true);
        event.setName("testInsertEvent");

        // act
        this.mEventDao.insert(event);

        // assert
        Event dbEvent = this.mEventDao.getLastEvent();
        assertEquals(dbEvent.getName(), event.getName());
    }

    @Test
    public void testInsertAllEvents() throws Exception{
        // arrange
        Event event1 = new Event();
        Event event2 = new Event();
        event1.setActive(true);
        event1.setName("testInsertAllEvents - Event1");
        event1.setId(1);
        event2.setActive(true);
        event2.setName("testInsertAllEvents - Event2");
        event2.setId(2);

        // act
        this.mEventDao.insertAll(event1,event2);

        // assert
        Event dbEvent1 = this.mEventDao.loadById(1);
        Event dbEvent2 = this.mEventDao.loadById(2);
        assertEquals(dbEvent1.getId(),event1.getId());
        assertEquals(dbEvent2.getId(),event2.getId());

    }

    @Test
    public void testGetAllEvents() throws Exception{
        // arrange
        Event event1 = new Event();
        Event event2 = new Event();
        event1.setActive(true);
        event1.setName("testGetAllEvents - Event1");
        event1.setId(1);
        event2.setActive(true);
        event2.setName("testGetAllEvents - Event2");
        event2.setId(2);
        this.mEventDao.insertAll(event1,event2);
        List<Event> eventList;

        // act
        eventList = this.mEventDao.getAll();

        // assert
        assertEquals(eventList.get(0).getName(),event1.getName());
        assertEquals(eventList.get(1).getName(),event2.getName());

    }

    @Test
    public void testCountEvents() throws Exception{
        // arrange
        Event event1 = new Event();
        Event event2 = new Event();
        event1.setActive(true);
        event1.setName("testCountEvents - Event1");
        event1.setId(1);
        event2.setActive(true);
        event2.setName("testCountEvents - Event2");
        event2.setId(2);
        this.mEventDao.insertAll(event1,event2);
        int eventCountExpected;
        int eventCountActual = 2;

        // act
        eventCountExpected = this.mEventDao.count();

        // assert
        assertEquals(eventCountExpected,eventCountActual);

    }

    @Test
    public void testDeleteEvent() throws Exception{
        // arrange
        Event event = new Event();
        event.setActive(true);
        event.setName("testDeleteEvent - Event1");
        this.mEventDao.insert(event);
        Event eventDb = this.mEventDao.getLastEvent();
        int expectedEvents = 0;

        // act
        this.mEventDao.delete(eventDb);
        int actualEvents = this.mEventDao.count();

        // assert
        assertEquals(expectedEvents, actualEvents);

    }

    @Test
    public void testUpdateEvent() throws Exception{
        // arrange
        Event event = new Event();
        event.setActive(true);
        event.setName("testUpdateEvent - Event1");
        event.setId(1);
        this.mEventDao.insert(event);
        event.setName("testNewUpdatedEvent");
        String expected = "testNewUpdatedEvent";

        // act
        this.mEventDao.update(event);

        // assert
        Event actual = this.mEventDao.loadById(1);
        assertEquals(expected,actual.getName());

    }

    @Test
    public void testLoadEventById() throws Exception{
        // arrange
        Event event = new Event();
        Event dbEvent;
        event.setActive(true);
        event.setName("testLoadEventById - Event1");
        event.setId(1);
        this.mEventDao.insert(event);

        // act
        dbEvent = this.mEventDao.loadById(1);

        // assert
        assertEquals(dbEvent.getId(),event.getId());

    }

    @Test
    public void testLoadAllEventsById() throws Exception{
        // arrange
        Event event1 = new Event();
        Event event2 = new Event();
        event1.setActive(true);
        event1.setName("testLoadAllEventById - Event1");
        event1.setId(1);
        event2.setActive(true);
        event2.setName("testLoadAllEventById - Event2");
        event2.setId(2);
        Integer[] eventIds = new Integer[2];
        eventIds[0] = event1.getId();
        eventIds[1] = event2.getId();
        this.mEventDao.insertAll(event1,event2);

        // act
        List<Event> dbListEvents = this.mEventDao.loadAllByIds(eventIds);

        // assert
        assertEquals(dbListEvents.get(0).getId(),event1.getId());
        assertEquals(dbListEvents.get(1).getId(),event2.getId());

    }


    }

