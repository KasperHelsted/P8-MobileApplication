package p8project.sw801.ui.MySmartDeviceFragment;

import android.arch.lifecycle.MutableLiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceNavigator;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
    //Arrange
    //Act
    //Assert

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

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new SmartDevice("Name", Boolean.TRUE, 1));
            return null;
        }).when(mMockDataManager).deleteSmartDevice(any(SmartDevice.class));

        //Act
        try{
            mySmartDeviceViewModel.deleteDevice(smartDevice);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }

    }
}