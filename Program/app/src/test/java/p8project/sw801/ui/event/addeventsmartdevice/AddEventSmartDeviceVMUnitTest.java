package p8project.sw801.ui.event.addeventsmartdevice;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddEventSmartDeviceVMUnitTest {
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
