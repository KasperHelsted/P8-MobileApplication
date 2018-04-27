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

import p8project.sw801.data.local.dao.Accessories.HueLightbulbWhiteDao;
import p8project.sw801.data.local.dao.CoordinateDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CoordinateDBUnitTest {
    private CoordinateDao mCoordinateDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mCoordinateDao = mDb.coordinateDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testInsertCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);
        coordinate.setId(1);

        // act
        this.mCoordinateDao.insert(coordinate);

        // assert
        Coordinate dbCoordinate = this.mCoordinateDao.getLast();
        assertEquals(dbCoordinate.getId(), coordinate.getId());
    }

    @Test
    public void testInsertAllCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);
        coordinate.setId(1);

        Coordinate coordinate1 = new Coordinate(0, 0);
        coordinate1.setId(2);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1);

        // assert
        List<Coordinate> dbCoordinate = this.mCoordinateDao.getAll();


        assertEquals(dbCoordinate.get(0).getId(),coordinate.getId());
        assertEquals(dbCoordinate.get(1).getId(),coordinate1.getId());

    }

    @Test
    public void testUpdateCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);
        coordinate.setLatitude(2);

        // act
        this.mCoordinateDao.update(coordinate);
        double expected = 2;
        double actual = coordinate.getLatitude();

        // assert
        assertEquals(expected, actual, 0);
    }

    @Test
    public void testDeleteCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);

        // act
        this.mCoordinateDao.insert(coordinate);
        Coordinate coordinateFromDb = this.mCoordinateDao.getLast();
        this.mCoordinateDao.delete(coordinateFromDb);
        Integer expected = 0;
        Integer actual = this.mCoordinateDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testCountCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);
        Coordinate coordinate1 = new Coordinate(0, 0);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1);
        Integer expected = 2;
        Integer actual = this.mCoordinateDao.count();

        // assert
        assertEquals(expected, actual);
    }


}
