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

import p8project.sw801.data.local.dao.SmartDeviceDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.SmartDevice;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SmartDeviceDBUnitTest {
    private SmartDeviceDao mSmartDeviceDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mSmartDeviceDao = mDb.smartDeviceDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllSmartDevices() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        SmartDevice smartDevice2 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testGetAllSmartDevices - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        smartDevice2.setActive(true);
        smartDevice2.setDeviceName("testGetAllSmartDevices - SmartDevice 2");
        smartDevice2.setId(2);
        smartDevice2.setInternalIdentifier(2);
        this.mSmartDeviceDao.insertAll(smartDevice1, smartDevice2);
        List<SmartDevice> smartDeviceList;

        // act
        smartDeviceList = this.mSmartDeviceDao.getAll();

        // assert
        assertEquals(smartDeviceList.get(0).getDeviceName(), smartDevice1.getDeviceName());
        assertEquals(smartDeviceList.get(1).getDeviceName(), smartDevice2.getDeviceName());

    }

    @Test
    public void testLoadAllSmartDevicesById() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        SmartDevice smartDevice2 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testLoadAllSmartDevicesById - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        smartDevice2.setActive(true);
        smartDevice2.setDeviceName("testLoadAllSmartDevicesById - SmartDevice 2");
        smartDevice2.setId(2);
        smartDevice2.setInternalIdentifier(2);
        Integer[] smartDeviceIds = new Integer[2];
        smartDeviceIds[0] = smartDevice1.getId();
        smartDeviceIds[1] = smartDevice2.getId();
        this.mSmartDeviceDao.insertAll(smartDevice1, smartDevice2);
        List<SmartDevice> smartDeviceList;

        // act
        smartDeviceList = this.mSmartDeviceDao.loadAllByIds(smartDeviceIds);

        // assert
        assertEquals(smartDeviceList.get(0).getId(), smartDevice1.getId());
        assertEquals(smartDeviceList.get(1).getId(), smartDevice2.getId());


    }

    @Test
    public void testLoadSmartDevicesById() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        SmartDevice dbSmartDevice;
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testLoadSmartDevicesById - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        this.mSmartDeviceDao.insert(smartDevice1);

        // act
        dbSmartDevice = this.mSmartDeviceDao.loadById(1);

        // assert
        assertEquals(dbSmartDevice.getId(), smartDevice1.getId());

    }

    @Test
    public void testGetLastSmartDevice() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testGetLastSmartDevice - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        this.mSmartDeviceDao.insert(smartDevice1);

        // act
        SmartDevice getLastSmartDevice = this.mSmartDeviceDao.getLast();

        // assert
        assertEquals(getLastSmartDevice.getDeviceName(), smartDevice1.getDeviceName());

    }

    @Test
    public void testCountSmartDevices() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        SmartDevice smartDevice2 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testCountSmartDevices - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        smartDevice2.setActive(true);
        smartDevice2.setDeviceName("testCountSmartDevices - SmartDevice 2");
        smartDevice2.setId(2);
        smartDevice2.setInternalIdentifier(2);
        this.mSmartDeviceDao.insertAll(smartDevice1, smartDevice2);
        int smartDeviceCountExpected;
        int smartDeviceCountActual = 2;

        // act
        smartDeviceCountExpected = this.mSmartDeviceDao.count();

        // assert
        assertEquals(smartDeviceCountExpected, smartDeviceCountActual);

    }

    @Test
    public void testInsertAllSmartDevices() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        SmartDevice smartDevice2 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testInsertAllSmartDevices - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        smartDevice2.setActive(true);
        smartDevice2.setDeviceName("testInsertAllSmartDevices - SmartDevice 2");
        smartDevice2.setId(2);
        smartDevice2.setInternalIdentifier(2);

        // act
        this.mSmartDeviceDao.insertAll(smartDevice1, smartDevice2);

        // assert
        SmartDevice dbSmartDevice1 = this.mSmartDeviceDao.loadById(1);
        SmartDevice dbSmartDevice2 = this.mSmartDeviceDao.loadById(2);
        assertEquals(dbSmartDevice1.getId(), smartDevice1.getId());
        assertEquals(dbSmartDevice2.getId(), smartDevice2.getId());

    }

    @Test
    public void testInsertSmartDevice() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testInsertSmartDevice - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);

        // act
        this.mSmartDeviceDao.insert(smartDevice1);

        // assert
        SmartDevice dbSmartDevice = this.mSmartDeviceDao.getLast();
        assertEquals(dbSmartDevice.getDeviceName(), smartDevice1.getDeviceName());

    }

    @Test
    public void testUpdateSmartDevice() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testUpdateSmartDevice - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        this.mSmartDeviceDao.insert(smartDevice1);
        smartDevice1.setDeviceName("testNewUpdatedSmartDevice");
        String expected = "testNewUpdatedSmartDevice";

        // act
        this.mSmartDeviceDao.update(smartDevice1);

        // assert
        SmartDevice actual = this.mSmartDeviceDao.loadById(1);
        assertEquals(expected, actual.getDeviceName());

    }

    @Test
    public void testDeleteSmartDevice() throws Exception {
        //arrange
        SmartDevice smartDevice1 = new SmartDevice();
        smartDevice1.setActive(true);
        smartDevice1.setDeviceName("testUpdateSmartDevice - SmartDevice 1");
        smartDevice1.setId(1);
        smartDevice1.setInternalIdentifier(1);
        this.mSmartDeviceDao.insert(smartDevice1);
        SmartDevice smartDeviceDb = this.mSmartDeviceDao.getLast();
        int expectedSmartDevices = 0;

        // act
        this.mSmartDeviceDao.delete(smartDeviceDb);
        int actualSmartDevices = this.mSmartDeviceDao.count();

        // assert
        assertEquals(expectedSmartDevices, actualSmartDevices);

    }
}

