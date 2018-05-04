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

import p8project.sw801.data.local.dao.ChainDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.Chain;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ChainDbUnitTest {
    private ChainDao mChainDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mChainDao = mDb.chainDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testGetAllChain() throws Exception {
        // arrange
        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);

        // act
        this.mChainDao.insertAll(chains);
        List<Chain> dbChain = this.mChainDao.getAll();

        // assert
        assertEquals(dbChain.get(0).getBrandName(), chain.getBrandName());
        assertEquals(dbChain.get(1).getBrandName(), chain1.getBrandName());
    }

    @Test
    public void testCountChain() throws Exception {
        // arrange

        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);

        // act
        this.mChainDao.insertAll(chains);
        Integer expected = 2;
        Integer actual = this.mChainDao.count();

        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void testByChainNameChain() throws Exception {
        // arrange

        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);

        // act
        this.mChainDao.insertAll(chains);
        Chain dbChain = this.mChainDao.byChainName(fakta);

        // assert
        assertEquals(dbChain.getBrandName(), chain.getBrandName());
    }

    @Test
    public void testGetActiveChainsChain() throws Exception {
        // arrange

        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setActive(FALSE);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);
        chain1.setActive(TRUE);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);

        // act
        this.mChainDao.insertAll(chains);
        List<Chain> dbChain = this.mChainDao.getActiveChains();

        // assert
        assertEquals(dbChain.get(0).getBrandName(), chain1.getBrandName());
    }

    @Test
    public void testLoadAllByIdsChain() throws Exception {
        // arrange

        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setId(1);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);
        chain1.setId(2);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);

        Integer[] ids = new Integer[2];
        ids[0] = chain.getId();
        ids[1] = chain1.getId();

        // act
        this.mChainDao.insertAll(chains);
        List<Chain> dbChain = this.mChainDao.loadAllByIds(ids);

        // assert
        assertEquals(dbChain.get(0).getBrandName(), chain.getBrandName());
        assertEquals(dbChain.get(1).getBrandName(), chain1.getBrandName());
    }

    @Test
    public void testLoadByIdChain() throws Exception {
        // arrange

        String fakta = "Fakta";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setId(1);

        // act
        this.mChainDao.insert(chain);
        Chain dbChain = this.mChainDao.loadById(1);

        // assert
        assertEquals(dbChain.getBrandName(), chain.getBrandName());

    }

    @Test
    public void testInsertAllChain() throws Exception {
        // arrange
        String fakta = "Fakta";
        String bilka = "Bilka";
        String netto = "Netto";
        String meny = "MENY";

        Chain chain = new Chain();
        chain.setBrandName(fakta);

        Chain chain1 = new Chain();
        chain1.setBrandName(bilka);

        Chain chain2 = new Chain();
        chain2.setBrandName(netto);

        Chain chain3 = new Chain();
        chain3.setBrandName(meny);

        List<Chain> chains = new ArrayList<>();
        chains.add(chain);
        chains.add(chain1);
        chains.add(chain2);
        chains.add(chain3);

        // act
        this.mChainDao.insertAll(chains);
        List<Chain> dbChain = this.mChainDao.getAll();

        // assert
        assertEquals(dbChain.get(0).getBrandName(), chain.getBrandName());
        assertEquals(dbChain.get(1).getBrandName(), chain1.getBrandName());
        assertEquals(dbChain.get(2).getBrandName(), chain2.getBrandName());
        assertEquals(dbChain.get(3).getBrandName(), chain3.getBrandName());

    }

    @Test
    public void testInsertChain() throws Exception {
        // arrange

        String fakta = "Fakta";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setId(1);

        // act
        this.mChainDao.insert(chain);
        Chain dbChain = this.mChainDao.loadById(1);

        // assert
        assertEquals(dbChain.getBrandName(), chain.getBrandName());
    }

    @Test
    public void testUpdateChain() throws Exception {
        // arrange

        String fakta = "Fakta";
        String bilka = "Bilka";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setId(1);
        this.mChainDao.insert(chain);
        Chain dbChain = this.mChainDao.loadById(1);
        dbChain.setBrandName(bilka);

        // act
        this.mChainDao.update(dbChain);
        Chain dbChain2 = this.mChainDao.loadById(1);

        // assert
        assertEquals(dbChain2.getBrandName(), bilka);
    }

    @Test
    public void testDeleteChain() throws Exception {
        // arrange
        String fakta = "Fakta";

        Chain chain = new Chain();
        chain.setBrandName(fakta);
        chain.setId(1);

        // act
        this.mChainDao.insert(chain);
        Chain chainFromDb = this.mChainDao.loadById(1);
        this.mChainDao.delete(chainFromDb);
        Integer expected = 0;
        Integer actual = this.mChainDao.count();

        // assert
        assertEquals(expected, actual);
    }

}
