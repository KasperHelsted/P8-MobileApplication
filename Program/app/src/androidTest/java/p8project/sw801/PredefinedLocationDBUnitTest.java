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

import p8project.sw801.data.local.dao.CoordinateDao;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PredefinedLocationDBUnitTest {
    private CoordinateDao mCoordinateDao;
    private PredefinedLocationDao mPredefinedLocationDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mPredefinedLocationDao = mDb.predefinedLocationDao();
        mCoordinateDao = mDb.coordinateDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testUpdatePredefinedLocation() throws Exception {
        String firstName = "FIRSTNAME";
        String secondName = "SECONDNAME";

        this.mCoordinateDao.insert(new Coordinate(0, 0));
        Coordinate coordinate = this.mCoordinateDao.getLast();

        PredefinedLocation predefinedLocation = new PredefinedLocation();
        predefinedLocation.setCoordinateId(coordinate.getId());

        predefinedLocation.setName(firstName);

        this.mPredefinedLocationDao.insert(predefinedLocation);

        PredefinedLocation predefinedLocation1FromDB = this.mPredefinedLocationDao.getLast();

        assertEquals(predefinedLocation1FromDB.getName(), firstName);

        predefinedLocation1FromDB.setName(secondName);

        this.mPredefinedLocationDao.update(predefinedLocation1FromDB);

        PredefinedLocation predefinedLocation1FromDBSecond = this.mPredefinedLocationDao.getLast();

        assertEquals(predefinedLocation1FromDBSecond.getName(), secondName);
    }

}
