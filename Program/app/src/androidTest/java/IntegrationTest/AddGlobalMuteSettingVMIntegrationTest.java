package IntegrationTest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.test.InstrumentationRegistry;

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
import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingViewModel;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class AddGlobalMuteSettingVMIntegrationTest {

    @Mock
    AddGlobalMuteSettingNavigator mAddGlobalMuteSettingNavigator;
    @Mock
    DataManager mMockDataManager;
    private AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private TestScheduler mTestScheduler;
    private PredefinedLocationDao mPredefinedLocationDao;
    private GlobalMuteDao mGlobalMuteDao;
    private AppDatabase mDb;


    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mAddGlobalMuteSettingViewModel = new AddGlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        mAddGlobalMuteSettingViewModel.setNavigator(mAddGlobalMuteSettingNavigator);
    }

    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mPredefinedLocationDao = mDb.predefinedLocationDao();
        mGlobalMuteDao = mDb.globalMuteDao();
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mAddGlobalMuteSettingViewModel = null;
        mAddGlobalMuteSettingNavigator = null;
    }

    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testSubmitGlobalMuteClickAddGlobalMute() throws Exception {
        // arrange

        final ObservableField<String> globulMuteName = new ObservableField<>("test");
        final ObservableField<String> startTime = new ObservableField<>("100");
        final ObservableField<String> endTime = new ObservableField<>("100");
        final ObservableField<String> comment = new ObservableField<>("hello");


        // act
        mAddGlobalMuteSettingViewModel.submitGlobalMuteClick();
        mTestScheduler.triggerActions();
        List<GlobalMute> dbGlobalMute = this.mGlobalMuteDao.getAll();
        String test = "test";

        // assert
        assertEquals(dbGlobalMute.get(0).getName(), test);
    }
}
