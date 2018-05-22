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
import p8project.sw801.data.local.dao.WhenDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.When;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WhenDBUnitTest {
    private WhenDao mWhenDao;
    private EventDao mEventDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mWhenDao = mDb.whenDao();
        mEventDao = mDb.eventDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllWhens() throws Exception {
        // arrange
        When when1 = new When();
        When when2 = new When();
        when1.setId(1);
        when2.setId(2);
        this.mWhenDao.insertAll(when1, when2);
        List<When> whenList;

        // act
        whenList = this.mWhenDao.getAll();

        // assert
        assertEquals(whenList.get(0).getId(), when1.getId());
        assertEquals(whenList.get(1).getId(), when2.getId());

    }

    @Test
    public void testLoadAllWhensByIds() throws Exception {
        // arrange
        When when1 = new When();
        When when2 = new When();
        when1.setId(1);
        when2.setId(2);
        Integer[] whenIds = new Integer[2];
        whenIds[0] = when1.getId();
        whenIds[1] = when2.getId();
        this.mWhenDao.insertAll(when1, when2);
        List<When> whenList;

        // act
        whenList = this.mWhenDao.loadAllByIds(whenIds);

        // assert
        assertEquals(whenList.get(0).getId(), when1.getId());
        assertEquals(whenList.get(1).getId(), when2.getId());

    }

    @Test
    public void testLoadByWhenId() throws Exception {
        // arrange
        When when1 = new When();
        when1.setId(1);
        When dbWhen;
        this.mWhenDao.insert(when1);

        // act
        dbWhen = this.mWhenDao.loadById(1);

        // assert
        assertEquals(dbWhen.getId(), when1.getId());

    }

    @Test
    public void testCountWhens() throws Exception {
        // arrange
        When when1 = new When();
        When when2 = new When();
        when1.setId(1);
        when2.setId(2);
        this.mWhenDao.insertAll(when1, when2);
        int whenCountExpected = 2;
        int whenCountActual;

        // act
        whenCountActual = this.mWhenDao.count();

        // assert
        assertEquals(whenCountExpected, whenCountActual);

    }

    @Test
    public void testDeleteWhenByEventId() throws Exception {
        // arrange
        When when1 = new When();
        Event eventHelper = new Event();
        eventHelper.setId(10);
        this.mEventDao.insert(eventHelper);
        when1.setId(1);
        when1.setEventId(10);
        this.mWhenDao.insert(when1);
        int expectedWhens = 0;

        // act
        this.mWhenDao.deleteWhenByEventId(10);
        int actualWhens = this.mWhenDao.count();

        // assert
        assertEquals(expectedWhens, actualWhens);

    }

    @Test
    public void testInsertAllWhens() throws Exception {
        // arrange
        When when1 = new When();
        When when2 = new When();
        when1.setId(1);
        when2.setId(2);

        // act
        this.mWhenDao.insertAll(when1, when2);

        // assert
        When dbWhen1 = this.mWhenDao.loadById(1);
        When dbWhen2 = this.mWhenDao.loadById(2);
        assertEquals(dbWhen1.getId(), when1.getId());
        assertEquals(dbWhen2.getId(), when2.getId());

    }

    @Test
    public void testInsertWhen() throws Exception {
        // arrange
        When when1 = new When();
        when1.setId(1);

        // act
        this.mWhenDao.insert(when1);

        // assert
        When dbWhen = this.mWhenDao.loadById(1);
        assertEquals(dbWhen.getId(), when1.getId());

    }

    @Test
    public void testUpdateWhen() throws Exception {
        // arrange
        When when1 = new When();
        when1.setId(1);
        when1.setEndHour(100);
        this.mWhenDao.insert(when1);
        When dbWhen = this.mWhenDao.loadById(1);
        dbWhen.setEndHour(5);
        int expected = 5;

        // act
        this.mWhenDao.update(dbWhen);

        // assert
        When actualWhen = this.mWhenDao.loadById(1);
        int actualId = actualWhen.getEndHour();
        assertEquals(actualId, expected);

    }

    @Test
    public void testDeleteWhen() throws Exception {
        // arrange
        When when1 = new When();
        when1.setId(1);
        this.mWhenDao.insert(when1);
        int expectedWhens = 0;

        // act
        this.mWhenDao.delete(when1);
        int actualWhens = this.mWhenDao.count();

        // assert
        assertEquals(expectedWhens, actualWhens);

    }
}
