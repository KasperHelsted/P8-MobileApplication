package p8project.sw801.ui.MySmartDeviceFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceNavigator;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class MySmartDeviceFragmentViewModelTest {
    @Mock
    MySmartDeviceNavigator mySmartDeviceFragmentCallback;
    @Mock
    DataManager mMockDataManager;


    private MySmartDeviceViewModel mySmartDeviceViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);


        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .getAllSmartDevices();

        mySmartDeviceViewModel = new MySmartDeviceViewModel(mMockDataManager, testSchedulerProvider);
        mySmartDeviceViewModel.setNavigator(mySmartDeviceFragmentCallback);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mySmartDeviceViewModel = null;
        mySmartDeviceFragmentCallback = null;
    }






}