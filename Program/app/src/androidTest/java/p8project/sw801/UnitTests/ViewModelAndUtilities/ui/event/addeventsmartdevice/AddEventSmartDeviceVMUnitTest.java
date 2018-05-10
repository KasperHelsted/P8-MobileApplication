package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.addeventsmartdevice;

import android.support.test.runner.AndroidJUnit4;

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
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDeviceNavigator;
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDeviceViewModel;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddEventSmartDeviceVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddEventSmartDeviceNavigator mAddEventSmartDeviceCallback;
    @Mock
    DataManager mMockDataManager;

    private AddEventSmartDeviceViewModel mAddEventSmartDeviceViewModel;
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

        mAddEventSmartDeviceViewModel = new AddEventSmartDeviceViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventSmartDeviceViewModel.setNavigator(mAddEventSmartDeviceCallback);
    }

    @Test
    public void testRenderListHueAddEventAccessory() {
        //Arrange
        SmartDevice smartDevice = new SmartDevice();
        smartDevice.setId(1);
        smartDevice.setInternalIdentifier(1);
        List<SmartDevice> smartDeviceList = new ArrayList<>();
        smartDeviceList.add(smartDevice);

        //Act
        mAddEventSmartDeviceViewModel.RenderList(smartDeviceList);
        //Assert
        verify(mAddEventSmartDeviceCallback).updatelist();
    }
}
