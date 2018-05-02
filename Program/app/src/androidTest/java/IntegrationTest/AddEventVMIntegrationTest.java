package IntegrationTest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import IntegrationTest.utils.rx.TestSchedulerProvider;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.event.addevent.AddEventNavigator;
import p8project.sw801.ui.event.addevent.AddEventViewModel;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class AddEventVMIntegrationTest {

    @Mock
    AddEventNavigator mAddEventNavigator;
    @Mock
    DataManager mMockDataManager;
    private AddEventViewModel mAddEventViewModel;
    private TestScheduler mTestScheduler;
    private PredefinedLocationDao mPredefinedLocationDao;
    private AppDatabase mDb;


    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mAddEventViewModel = new AddEventViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventViewModel.setNavigator(mAddEventNavigator);
    }

    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mPredefinedLocationDao = mDb.predefinedLocationDao();
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mAddEventViewModel = null;
        mAddEventNavigator = null;
    }

    public void closeDb() throws IOException {
        mDb.close();
    }


}
