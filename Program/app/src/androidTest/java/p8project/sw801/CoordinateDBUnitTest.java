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

import p8project.sw801.data.local.dao.CoordinateDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Coordinate;

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


        assertEquals(dbCoordinate.get(0).getId(), coordinate.getId());
        assertEquals(dbCoordinate.get(1).getId(), coordinate1.getId());

    }

    @Test
    public void testUpdateCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(0, 0);
        this.mCoordinateDao.insert(coordinate);
        Coordinate dbCoordinate = this.mCoordinateDao.getLast();
        dbCoordinate.setLatitude(2);

        // act
        this.mCoordinateDao.update(dbCoordinate);
        Coordinate dbCoordinate2 = this.mCoordinateDao.getLast();
        double expected = 2;
        double actual = dbCoordinate2.getLatitude();

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

    @Test
    public void testGetLastWithLimitCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(1, 1);
        Coordinate coordinate1 = new Coordinate(2, 2);
        Coordinate coordinate2 = new Coordinate(3, 3);
        Coordinate coordinate3 = new Coordinate(4, 4);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1, coordinate2, coordinate3);

        // assert
        List<Coordinate> dbCoordinate = this.mCoordinateDao.getLast(2);

        assertEquals(dbCoordinate.get(0).getLatitude(), coordinate3.getLatitude(),0);
        assertEquals(dbCoordinate.get(0).getLongitude(), coordinate3.getLongitude(),0);

        assertEquals(dbCoordinate.get(1).getLatitude(), coordinate2.getLatitude(),0);
        assertEquals(dbCoordinate.get(1).getLongitude(), coordinate2.getLongitude(),0);
    }

    @Test
    public void testGetLastCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(1, 1);
        Coordinate coordinate1 = new Coordinate(2, 2);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1);

        // assert
        Coordinate dbCoordinate = this.mCoordinateDao.getLast();

        assertEquals(dbCoordinate.getLatitude(), coordinate1.getLatitude(),0);
        assertEquals(dbCoordinate.getLongitude(), coordinate1.getLongitude(),0);
    }

    @Test
    public void testLoadByIdCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(1, 1);
        coordinate.setId(1);

        // act
        this.mCoordinateDao.insert(coordinate);

        // assert
        Coordinate dbCoordinate = this.mCoordinateDao.loadById(1);

        assertEquals(dbCoordinate.getId(), coordinate.getId());
    }

    @Test
    public void testLoadAllByIdsCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(1, 1);
        coordinate.setId(1);

        Coordinate coordinate1 = new Coordinate(2, 2);
        coordinate1.setId(2);

        Coordinate coordinate2 = new Coordinate(3, 3);
        coordinate2.setId(3);

        Coordinate coordinate3 = new Coordinate(4, 4);
        coordinate3.setId(4);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1, coordinate2, coordinate3);

        Integer[] ids = new Integer[4];
        ids[0] = coordinate.getId();
        ids[1] = coordinate1.getId();
        ids[2] = coordinate2.getId();
        ids[3] = coordinate3.getId();

        // assert
        List<Coordinate> dbCoordinate = this.mCoordinateDao.loadAllByIds(ids);

        assertEquals(dbCoordinate.get(3).getLatitude(), coordinate3.getLatitude(),0);
        assertEquals(dbCoordinate.get(3).getLongitude(), coordinate3.getLongitude(),0);

        assertEquals(dbCoordinate.get(2).getLatitude(), coordinate2.getLatitude(),0);
        assertEquals(dbCoordinate.get(2).getLongitude(), coordinate2.getLongitude(),0);

        assertEquals(dbCoordinate.get(1).getLatitude(), coordinate1.getLatitude(),0);
        assertEquals(dbCoordinate.get(1).getLongitude(), coordinate1.getLongitude(),0);

        assertEquals(dbCoordinate.get(0).getLatitude(), coordinate.getLatitude(),0);
        assertEquals(dbCoordinate.get(0).getLongitude(), coordinate.getLongitude(),0);
    }

    @Test
    public void testGetAllCoordinate() throws Exception {
        // arrange
        Coordinate coordinate = new Coordinate(1, 1);
        Coordinate coordinate1 = new Coordinate(2, 2);
        Coordinate coordinate2 = new Coordinate(3, 3);
        Coordinate coordinate3 = new Coordinate(4, 4);

        // act
        this.mCoordinateDao.insertAll(coordinate, coordinate1, coordinate2, coordinate3);

        // assert
        List<Coordinate> dbCoordinate = this.mCoordinateDao.getAll();

        assertEquals(dbCoordinate.get(3).getLatitude(), coordinate3.getLatitude(),0);
        assertEquals(dbCoordinate.get(3).getLongitude(), coordinate3.getLongitude(),0);

        assertEquals(dbCoordinate.get(2).getLatitude(), coordinate2.getLatitude(),0);
        assertEquals(dbCoordinate.get(2).getLongitude(), coordinate2.getLongitude(),0);

        assertEquals(dbCoordinate.get(1).getLatitude(), coordinate1.getLatitude(),0);
        assertEquals(dbCoordinate.get(1).getLongitude(), coordinate1.getLongitude(),0);

        assertEquals(dbCoordinate.get(0).getLatitude(), coordinate.getLatitude(),0);
        assertEquals(dbCoordinate.get(0).getLongitude(), coordinate.getLongitude(),0);
    }








}
