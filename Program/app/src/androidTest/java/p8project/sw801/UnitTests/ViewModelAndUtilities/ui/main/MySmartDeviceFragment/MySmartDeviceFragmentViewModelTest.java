package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.main.MySmartDeviceFragment;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceNavigator;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceViewModel;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MySmartDeviceFragmentViewModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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

        List<SmartDevice> s = new ArrayList<>();
        s.add(new SmartDevice("nametemp", Boolean.TRUE, 1));

        doReturn(Observable.just(s))
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

    @Test
    public void addSmartDevice(){
        //Arrange
        //Act
        mySmartDeviceViewModel.addSmartDevice();
        //Assert
        verify(mySmartDeviceFragmentCallback).addSmartDevice();
    }

    @Test
    public void addMySmartDevicesItemsToList(){
        //Arrange
        List<SmartDevice> smartDevices= new ArrayList<>();
        smartDevices.add(new SmartDevice("Name", Boolean.TRUE, 1));
        //Act
        mySmartDeviceViewModel.addMySmartDevicesItemsToList(smartDevices);
        //Assert
        assertTrue(mySmartDeviceViewModel.mySmartDevicesObservableArrayList.contains(smartDevices.get(0)));
    }

    @Test
    public void deleteDevice(){
        //Arrange
        SmartDevice smartDevice = new SmartDevice("Name", Boolean.TRUE, 1);
        smartDevice.setId(1);
        List<SmartDevice> smartDevices = new ArrayList<>();
        smartDevices.add(smartDevice);

        doReturn(Observable.just(true)).when(mMockDataManager).deleteSmartDevice(any(SmartDevice.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteHueBridgeBySmartDeviceId(any(Integer.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteWhiteHueLightsBySmartDeviceId(any(Integer.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteRGBHueLightsBySmartDeviceId(any(Integer.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteNestHubBySmartDeviceId(any(Integer.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteNestThermostatBySmartDeviceId(any(Integer.class));
        doReturn(Observable.just(true)).when(mMockDataManager).deleteTriggerBySmartDeviceId(any(Integer.class));


        //Act
        mySmartDeviceViewModel.deleteDevice(smartDevice);
        mTestScheduler.triggerActions();

        //Assert when act is called
        //Does not return anything to compare
    }
}