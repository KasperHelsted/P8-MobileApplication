package p8project.sw801;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.ui.main.MainNavigator;
import p8project.sw801.ui.main.MainViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static java.lang.Boolean.TRUE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DbUnitTest {
    @Mock
    MainNavigator mMainNavigator;
    @Mock
    DataManager mMockDataManager;
    private MainViewModel mMainViewModel;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mMainViewModel = new MainViewModel(mMockDataManager, testSchedulerProvider);
        mMainViewModel.setNavigator(mMainNavigator);
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mMainViewModel = null;
        mMainNavigator = null;
    }


    @Test
    public void testGetLightsByBridgeIdDB() throws Exception {
        // arrange
        HueLightbulbWhite testBulb = new HueLightbulbWhite();
        testBulb.setId(1);
        testBulb.setHueBridgeId(12);
        testBulb.setDeviceName("light");
        testBulb.setSmartDeviceId(123);
        testBulb.setDeviceId("test");

        HueLightbulbWhite testBulb2 = new HueLightbulbWhite();
        testBulb.setId(2);
        testBulb.setHueBridgeId(12);
        testBulb.setDeviceName("dark");
        testBulb.setSmartDeviceId(1234);
        testBulb.setDeviceId("test2");

        this.mMockDataManager.insertAllHueLights(testBulb, testBulb2);

        // act
        Observable<Boolean> actual = this.mMockDataManager.insertAllHueLights(testBulb, testBulb2);


        // assert
        actual.subscribe(aBoolean -> {
            assertTrue(aBoolean);
        });

    }
}
