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
import java.util.List;

import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GlobalMuteDBUnitTest {

    private GlobalMuteDao mGlobalMuteDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mGlobalMuteDao = mDb.globalMuteDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllGlobalMute() throws Exception{
        // arrange
        GlobalMute globalMute1 = new GlobalMute();
        globalMute1.setName("testGlobalMuteGetAll - GlobalMute 1");
        globalMute1.setId(1);
        globalMute1.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute1.setEndTime(System.currentTimeMillis() + 10);
        globalMute1.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        GlobalMute globalMute2 = new GlobalMute();
        globalMute2.setName("testGlobalMuteGetAll - GlobalMute 2");
        globalMute2.setId(2);
        globalMute2.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute2.setEndTime(System.currentTimeMillis() + 15);
        globalMute2.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        this.mGlobalMuteDao.insertAll(globalMute1,globalMute2);
        List<GlobalMute> globalMuteList;

        // act
        globalMuteList = this.mGlobalMuteDao.getAll();

        // assert
        assertEquals(globalMuteList.get(0).getId(),globalMute1.getId());
        assertEquals(globalMuteList.get(1).getId(),globalMute2.getId());

    }

    @Test
    public void testLoadAllGlobalMutesByIds() throws Exception{
        // arrange
        GlobalMute globalMute1 = new GlobalMute();
        GlobalMute globalMute2 = new GlobalMute();
        globalMute1.setName("testLoadAllGlobalMutesByIds - GlobalMute 1");
        globalMute1.setId(1);
        globalMute1.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute1.setEndTime(System.currentTimeMillis() + 10);
        globalMute1.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        globalMute2.setName("testLoadAllGlobalMutesByIds - GlobalMute 2");
        globalMute2.setId(2);
        globalMute2.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute2.setEndTime(System.currentTimeMillis() + 15);
        globalMute2.setNote("Unit test");

        Integer[] globalMuteIds = new Integer[2];
        globalMuteIds[0] = globalMute1.getId();
        globalMuteIds[1] = globalMute2.getId();
        this.mGlobalMuteDao.insertAll(globalMute1,globalMute2);

        // act
        List<GlobalMute> dbListEvents = this.mGlobalMuteDao.loadAllByIds(globalMuteIds);

        // assert
        assertEquals(dbListEvents.get(0).getId(),globalMute1.getId());
        assertEquals(dbListEvents.get(1).getId(),globalMute2.getId());

    }

    @Test
    public void testLoadGlobalMuteById() throws Exception{
        // arrange
        GlobalMute globalMute = new GlobalMute();
        GlobalMute dbGlobalMute;
        globalMute.setName("testLoadGlobalMuteById - GlobalMute 1");
        globalMute.setId(1);
        globalMute.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute.setEndTime(System.currentTimeMillis() + 10);
        globalMute.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error
        this.mGlobalMuteDao.insert(globalMute);

        // act
        dbGlobalMute = this.mGlobalMuteDao.loadById(1);

        // assert
        assertEquals(dbGlobalMute.getId(),globalMute.getId());

    }

    @Test
    public void testCountGlobalMutes() throws Exception{
        // arrange
        GlobalMute globalMute1 = new GlobalMute();
        GlobalMute globalMute2 = new GlobalMute();
        globalMute1.setName("testCountGlobalMutes - GlobalMute 1");
        globalMute1.setId(1);
        globalMute1.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute1.setEndTime(System.currentTimeMillis() + 10);
        globalMute1.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        globalMute2.setName("testCountGlobalMutes - GlobalMute 2");
        globalMute2.setId(2);
        globalMute2.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute2.setEndTime(System.currentTimeMillis() + 15);
        globalMute2.setNote("Unit test");

        this.mGlobalMuteDao.insertAll(globalMute1,globalMute2);
        int eventCountExpected;
        int eventCountActual = 2;

        // act
        eventCountExpected = this.mGlobalMuteDao.count();

        // assert
        assertEquals(eventCountExpected,eventCountActual);

    }

    @Test
    public void testInsertAllGlobalMutes() throws Exception{
        // arrange
        GlobalMute globalMute1 = new GlobalMute();
        GlobalMute globalMute2 = new GlobalMute();
        globalMute1.setName("testInsertAllGlobalMutes - GlobalMute 1");
        globalMute1.setId(1);
        globalMute1.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute1.setEndTime(System.currentTimeMillis() + 10);
        globalMute1.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        globalMute2.setName("testInsertAllGlobalMutes - GlobalMute 2");
        globalMute2.setId(2);
        globalMute2.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute2.setEndTime(System.currentTimeMillis() + 15);
        globalMute2.setNote("Unit test");

        // act
        this.mGlobalMuteDao.insertAll(globalMute1,globalMute2);

        // assert
        GlobalMute dbGlobalMute1 = this.mGlobalMuteDao.loadById(1);
        GlobalMute dbGlobalMute2 = this.mGlobalMuteDao.loadById(2);
        assertEquals(dbGlobalMute1.getId(),globalMute1.getId());
        assertEquals(dbGlobalMute2.getId(),globalMute2.getId());

    }

    @Test
    public void testInsertGlobalMute() throws Exception{
        // arrange
        GlobalMute globalMute = new GlobalMute();
        globalMute.setName("testInsertGlobalMute - GlobalMute 1");
        globalMute.setId(1);
        globalMute.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute.setEndTime(System.currentTimeMillis() + 10);
        globalMute.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error

        // act
        this.mGlobalMuteDao.insert(globalMute);

        // assert
        GlobalMute dbGlobalMute = this.mGlobalMuteDao.loadById(1);
        assertEquals(dbGlobalMute.getName(),globalMute.getName());


    }

    @Test
    public void testUpdateGlobalMute() throws Exception{
        // arrange
        GlobalMute globalMute = new GlobalMute();
        globalMute.setName("testUpdateGlobalMute - GlobalMute 1");
        globalMute.setId(1);
        globalMute.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute.setEndTime(System.currentTimeMillis() + 10);
        globalMute.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error
        this.mGlobalMuteDao.insert(globalMute);
        globalMute.setName("testNewUpdatedGlobalMute");
        String expected = "testNewUpdatedGlobalMute";

        // act
        this.mGlobalMuteDao.update(globalMute);

        // assert
        GlobalMute actual = this.mGlobalMuteDao.loadById(1);
        assertEquals(expected,actual.getName());

    }


    @Test
    public void testDeleteGlobalMute() throws Exception{
        // arrange
        GlobalMute globalMute = new GlobalMute();
        globalMute.setName("testDeleteGlobalMute - GlobalMute 1");
        globalMute.setId(1);
        globalMute.setStartTime(System.currentTimeMillis()); //Returns the current time in milliseconds
        globalMute.setEndTime(System.currentTimeMillis() + 10);
        globalMute.setNote("Unit test");
        //PredefinedLocationId not set because of foreign key error
        this.mGlobalMuteDao.insert(globalMute);
        GlobalMute globalMuteDb = this.mGlobalMuteDao.loadById(1);
        Integer expectedGlobalMutes = 0;

        // act
        this.mGlobalMuteDao.delete(globalMuteDb);
        Integer actualGlobalMutes = this.mGlobalMuteDao.count();

        // assert
        assertEquals(expectedGlobalMutes,actualGlobalMutes);

    }


}
