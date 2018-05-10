package p8project.sw801.ui.event.notificationorsmartdeviceUnitTest;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdeviceNavigator;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdeviceViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class notificationorsmartdeviceUnitTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    NotificationOrSmartdeviceNavigator notificationOrSmartdeviceNavigator;
    @Mock
    DataManager mMockDataManager;


    private NotificationOrSmartdeviceViewModel notificationOrSmartdeviceViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);


        notificationOrSmartdeviceViewModel = new NotificationOrSmartdeviceViewModel(mMockDataManager, testSchedulerProvider);
        notificationOrSmartdeviceViewModel.setNavigator(notificationOrSmartdeviceNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        notificationOrSmartdeviceViewModel = null;
        notificationOrSmartdeviceNavigator = null;
    }

    @Test
    public void addNotificationMethod(){
        //Arrange
        //Act
        notificationOrSmartdeviceViewModel.addNotificationMethod();
        //Assert
        verify(notificationOrSmartdeviceNavigator).addNotification();



    }

    @Test
    public void openAddSmartDeviceList(){
        //Arrange
        //Act
        notificationOrSmartdeviceViewModel.openAddSmartDeviceList();
        //Assert
        verify(notificationOrSmartdeviceNavigator).openAddSmartDeviceList();
    }

}