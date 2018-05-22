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

import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.PredefinedLocation;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PredefinedLocationDBUnitTest {
    private PredefinedLocationDao mPredefinedDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mPredefinedDao = mDb.predefinedLocationDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllPredefinedLocations() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation1 = new PredefinedLocation();
        PredefinedLocation predefinedLocation2 = new PredefinedLocation();
        predefinedLocation1.setName("testGetAllPredefinedLocations - PredefinedLocation 1");
        predefinedLocation1.setId(1);
        //CoordinateId not set because of foreign key error

        predefinedLocation2.setName("testGetAllPredefinedLocations - PredefinedLocation 2");
        predefinedLocation2.setId(2);
        //CoordinateId not set because of foreign key error
        this.mPredefinedDao.insertAll(predefinedLocation1, predefinedLocation2);
        List<PredefinedLocation> predefinedLocationList;

        // act
        predefinedLocationList = this.mPredefinedDao.getAll();

        // assert
        assertEquals(predefinedLocationList.get(0).getName(), predefinedLocation1.getName());
        assertEquals(predefinedLocationList.get(1).getName(), predefinedLocation2.getName());

    }

    @Test
    public void testLoadAllPredefinedLocationsByIds() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation1 = new PredefinedLocation();
        PredefinedLocation predefinedLocation2 = new PredefinedLocation();
        predefinedLocation1.setName("testLoadAllPredefinedLocationsByIds - PredefinedLocation 1");
        predefinedLocation1.setId(1);
        //CoordinateId not set because of foreign key error

        predefinedLocation2.setName("testLoadAllPredefinedLocationsByIds - PredefinedLocation 2");
        predefinedLocation2.setId(2);
        //CoordinateId not set because of foreign key error
        Integer[] predefinedLocationsIds = new Integer[2];
        predefinedLocationsIds[0] = predefinedLocation1.getId();
        predefinedLocationsIds[1] = predefinedLocation2.getId();
        this.mPredefinedDao.insertAll(predefinedLocation1, predefinedLocation2);

        // act
        List<PredefinedLocation> dbListPredefinedLocations = this.mPredefinedDao.loadAllByIds(predefinedLocationsIds);

        // assert
        assertEquals(dbListPredefinedLocations.get(0).getId(), predefinedLocation1.getId());
        assertEquals(dbListPredefinedLocations.get(1).getId(), predefinedLocation2.getId());
    }

    @Test
    public void testLoadPredefinedLocationsById() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation();
        PredefinedLocation dbPredefinedLocation;
        predefinedLocation.setName("testLoadPredefinedLocationsById - PredefinedLocation 1");
        predefinedLocation.setId(1);
        //CoordinateId not set because of foreign key error
        this.mPredefinedDao.insert(predefinedLocation);

        // act
        dbPredefinedLocation = this.mPredefinedDao.loadById(1);

        // assert
        assertEquals(dbPredefinedLocation.getId(), predefinedLocation.getId());
    }

    @Test
    public void testCountPredefinedLocations() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation1 = new PredefinedLocation();
        PredefinedLocation predefinedLocation2 = new PredefinedLocation();
        predefinedLocation1.setName("testCountPredefinedLocations - PredefinedLocation 1");
        predefinedLocation1.setId(1);
        //CoordinateId not set because of foreign key error

        predefinedLocation2.setName("testCountPredefinedLocations - PredefinedLocation 2");
        predefinedLocation2.setId(2);
        //CoordinateId not set because of foreign key error
        this.mPredefinedDao.insertAll(predefinedLocation1, predefinedLocation2);
        int predefinedLocationCountExpected;
        int predefinedLocationCountActual = 2;

        // act
        predefinedLocationCountExpected = this.mPredefinedDao.countPredefinedLocations();

        // assert
        assertEquals(predefinedLocationCountExpected, predefinedLocationCountActual);
    }

    @Test
    public void testInsertAllPredefinedLocations() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation1 = new PredefinedLocation();
        PredefinedLocation predefinedLocation2 = new PredefinedLocation();
        predefinedLocation1.setName("testInsertAllPredefinedLocations - PredefinedLocation 1");
        predefinedLocation1.setId(1);
        //CoordinateId not set because of foreign key error

        predefinedLocation2.setName("testInsertAllPredefinedLocations - PredefinedLocation 2");
        predefinedLocation2.setId(2);
        //CoordinateId not set because of foreign key error

        // act
        this.mPredefinedDao.insertAll(predefinedLocation1, predefinedLocation2);

        // assert
        PredefinedLocation dbPredefinedLocation1 = this.mPredefinedDao.loadById(1);
        PredefinedLocation dbPredefinedLocation2 = this.mPredefinedDao.loadById(2);
        assertEquals(dbPredefinedLocation1.getId(), predefinedLocation1.getId());
        assertEquals(dbPredefinedLocation2.getId(), predefinedLocation2.getId());
    }

    @Test
    public void testInsertPredefinedLocation() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation();
        predefinedLocation.setName("testInsertPredefinedLocation - PredefinedLocation 1");
        predefinedLocation.setId(1);
        //CoordinateId not set because of foreign key error

        // act
        this.mPredefinedDao.insert(predefinedLocation);

        // assert
        PredefinedLocation dbPredefinedLocation = this.mPredefinedDao.loadById(1);
        assertEquals(predefinedLocation.getName(), dbPredefinedLocation.getName());
    }

    @Test
    public void testUpdatePredefinedLocation() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation();
        predefinedLocation.setName("testUpdatePredefinedLocation - PredefinedLocation 1");
        predefinedLocation.setId(1);
        //CoordinateId not set because of foreign key error
        this.mPredefinedDao.insert(predefinedLocation);
        predefinedLocation.setName("testNewUpdatedPredefinedLocation");
        String expected = "testNewUpdatedPredefinedLocation";

        // act
        this.mPredefinedDao.update(predefinedLocation);

        // assert
        PredefinedLocation actual = this.mPredefinedDao.loadById(1);
        assertEquals(expected, actual.getName());

    }

    @Test
    public void testDeletePredefinedLocation() throws Exception {
        // arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation();
        predefinedLocation.setName("testUpdatePredefinedLocation - PredefinedLocation 1");
        predefinedLocation.setId(1);
        //CoordinateId not set because of foreign key error
        this.mPredefinedDao.insert(predefinedLocation);
        Integer expectedPredefinedLocations = 0;

        // act
        this.mPredefinedDao.delete(predefinedLocation);
        Integer actualPredefinedLocations = this.mPredefinedDao.countPredefinedLocations();

        // assert
        assertEquals(actualPredefinedLocations, expectedPredefinedLocations);
    }

}
