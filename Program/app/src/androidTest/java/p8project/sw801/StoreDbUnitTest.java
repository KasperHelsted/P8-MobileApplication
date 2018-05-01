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

import p8project.sw801.data.local.dao.StoreDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Store;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StoreDbUnitTest {
    private StoreDao mStoreDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mStoreDao = mDb.storeDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetStoreByNameStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);

        // act
        this.mStoreDao.insert(store);
        Store dbStore = this.mStoreDao.getStoreByName(fakta);

        // assert
        assertEquals(dbStore.getStoreName(), store.getStoreName());
    }

    @Test
    public void testGetAllStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";
        String bilka = "Bilka, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);

        Store store1 = new Store();
        store1.setStoreName(bilka);

        // act
        this.mStoreDao.insertAll(store, store1);
        List<Store> dbStore = this.mStoreDao.getAll();

        // assert
        assertEquals(dbStore.get(0).getStoreName(), store.getStoreName());
        assertEquals(dbStore.get(1).getStoreName(), store1.getStoreName());
    }

    @Test
    public void testCountStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";
        String bilka = "Bilka, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);

        Store store1 = new Store();
        store1.setStoreName(bilka);

        // act
        this.mStoreDao.insertAll(store, store1);
        Integer expected = 2;
        Integer actual = this.mStoreDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadAllByIdsStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";
        String bilka = "Bilka, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);
        store.setId(1);

        Store store1 = new Store();
        store1.setStoreName(bilka);
        store1.setId(2);

        Integer[] ids = new Integer[2];
        ids[0] = store.getId();
        ids[1] = store1.getId();

        // act
        this.mStoreDao.insertAll(store, store1);
        List<Store> dbStore = this.mStoreDao.loadAllByIds(ids);

        // assert
        assertEquals(dbStore.get(0).getStoreName(), store.getStoreName());
        assertEquals(dbStore.get(1).getStoreName(), store1.getStoreName());
    }

    @Test
    public void testLoadByIdStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);
        store.setId(1);

        // act
        this.mStoreDao.insert(store);
        Store dbStore = this.mStoreDao.loadById(1);

        // assert
        assertEquals(dbStore.getStoreName(), store.getStoreName());
    }

    @Test
    public void testInsertAllStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";
        String bilka = "Bilka, Aalborg Oest";
        String netto = "Netto, Aalborg Oest";
        String meny = "MENY, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);

        Store store1 = new Store();
        store1.setStoreName(bilka);

        Store store2 = new Store();
        store2.setStoreName(netto);

        Store store3 = new Store();
        store3.setStoreName(meny);

        // act
        this.mStoreDao.insertAll(store, store1, store2, store3);
        List<Store> dbStore = this.mStoreDao.getAll();

        // assert
        assertEquals(dbStore.get(0).getStoreName(), store.getStoreName());
        assertEquals(dbStore.get(1).getStoreName(), store1.getStoreName());
        assertEquals(dbStore.get(2).getStoreName(), store2.getStoreName());
        assertEquals(dbStore.get(3).getStoreName(), store3.getStoreName());
    }

    @Test
    public void testInsertStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);
        store.setId(1);

        // act
        this.mStoreDao.insert(store);
        Store dbStore = this.mStoreDao.loadById(1);

        // assert
        assertEquals(dbStore.getStoreName(), store.getStoreName());
    }

    @Test
    public void testUpdateStore() throws Exception {
        // arrange

        String fakta = "Fakta, Aalborg Oest";
        String bilka = "Bilka, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);
        store.setId(1);
        this.mStoreDao.insert(store);
        Store dbStore = this.mStoreDao.loadById(1);
        dbStore.setStoreName(bilka);

        // act
        this.mStoreDao.update(dbStore);
        Store dbStore2 = this.mStoreDao.loadById(1);

        // assert
        assertEquals(dbStore2.getStoreName(), bilka);
    }

    @Test
    public void testDeleteStore() throws Exception {
        // arrange
        String fakta = "Fakta, Aalborg Oest";

        Store store = new Store();
        store.setStoreName(fakta);
        store.setId(1);

        // act
        this.mStoreDao.insert(store);
        Store storeFromDb = this.mStoreDao.loadById(1);
        this.mStoreDao.delete(storeFromDb);
        Integer expected = 0;
        Integer actual = this.mStoreDao.count();

        // assert
        assertEquals(expected, actual);
    }
}
