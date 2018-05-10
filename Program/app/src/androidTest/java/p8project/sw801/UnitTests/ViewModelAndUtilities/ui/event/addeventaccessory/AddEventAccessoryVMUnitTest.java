package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.addeventaccessory;

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
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessoryNavigator;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessoryViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddEventAccessoryVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddEventAccessoryNavigator mAddEventAccessoryCallback;
    @Mock
    DataManager mMockDataManager;

    private AddEventAccessoryViewModel mAddEventAccessoryViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mAddEventAccessoryViewModel = new AddEventAccessoryViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventAccessoryViewModel.setNavigator(mAddEventAccessoryCallback);

    }

    @Test
    public void testGetListFromDbAddEventAccessory(){
        //Arrange
        SmartDevice s = new SmartDevice();
        s.setId(1);
        s.setInternalIdentifier(1);
        List<HueLightbulbWhite> list = new ArrayList<>();

        doReturn(Observable.just(list)).when(mMockDataManager).getWhiteHueLightsBySmartDeviceId(any(Integer.class));

        //Act
        mAddEventAccessoryViewModel.getListFromDb(s);
        mTestScheduler.triggerActions();
        //Assert
        verify(mAddEventAccessoryCallback).updatelist();

    }

    @Test
    public void testRenderListHueAddEventAccessory() {
        //Arrange
        List<HueLightbulbWhite> hueLightbulbWhites = new ArrayList<>();
        hueLightbulbWhites.add(new HueLightbulbWhite(1, "home", "123", 1));
        //Act
        mAddEventAccessoryViewModel.RenderListHue(hueLightbulbWhites);
        //Assert
        verify(mAddEventAccessoryCallback).updatelist();
    }

    @Test
    public void testRenderListNestAddEventAccessory() {
        //Arrange
        List<NestThermostat> nestThermostats = new ArrayList<>();
        nestThermostats.add(new NestThermostat(1, "living room", "123", 1));
        //Act
        mAddEventAccessoryViewModel.RenderListNest(nestThermostats);
        //Assert
        verify(mAddEventAccessoryCallback).updatelist();
    }


}
