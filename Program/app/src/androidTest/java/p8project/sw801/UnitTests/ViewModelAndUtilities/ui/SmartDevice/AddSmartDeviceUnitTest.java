package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.SmartDevice;

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
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceNavigator;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddSmartDeviceUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddSmartDeviceNavigator addSmartDeviceNavigator;
    @Mock
    DataManager mMockDataManager;

    private AddSmartDeviceViewModel addSmartDeviceViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        addSmartDeviceViewModel = new AddSmartDeviceViewModel(mMockDataManager, testSchedulerProvider);
        addSmartDeviceViewModel.setNavigator(addSmartDeviceNavigator);

    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        addSmartDeviceViewModel = null;
        addSmartDeviceNavigator = null;
    }

    @Test
    public void insertNest(){
        //Arrange
        NestHub nestHub = new NestHub("1", 1, "12", "123", 12l);
        nestHub.setId(1);
        SmartDevice smartDevice = new SmartDevice("Name", true, 1);
        smartDevice.setId(1);
        List<NestThermostat> nestThermostatList = new ArrayList<>();
        NestThermostat nestThermostat = new NestThermostat();
        nestThermostat.setDeviceId("adasda12");
        nestThermostat.setName("name");
        nestThermostat.setSmartDeviceId(1);
        nestThermostat.setNestHubId(1);
        nestThermostat.setId(1);
        nestThermostatList.add(nestThermostat);

        doReturn(Observable.just(true)).when(mMockDataManager).insertSmartDevice(any(SmartDevice.class));
        doReturn(Observable.just(smartDevice)).when(mMockDataManager).getLastSmartDevice();
        doReturn(Observable.just(true)).when(mMockDataManager).insertNestHub(any(NestHub.class));
        doReturn(Observable.just(nestHub)).when(mMockDataManager).getLastInsertedNestHub();
        doReturn(Observable.just(true)).when(mMockDataManager).insertNestThermostat(any(NestThermostat.class));


        //Act
        addSmartDeviceViewModel.insertNest(nestHub, nestThermostatList);
        mTestScheduler.triggerActions();
        //Assert
        verify(addSmartDeviceNavigator).ChangeToSmartDevice();
    }
    @Test
    public void getBridges(){
        //Arrange
        List<HueBridge> hueBridgeList = new ArrayList<>();
        HueBridge hueBridge = new HueBridge();
        hueBridgeList.add(hueBridge);

        doReturn(Observable.just(hueBridgeList)).when(mMockDataManager).getAllHueBridges();
        //Act
        addSmartDeviceViewModel.getBridges();
        mTestScheduler.triggerActions();
        //Assert
        verify(addSmartDeviceNavigator).setupBridges(hueBridgeList);
    }
    @Test
    public void insertBridge(){
        //Arrange
        HueBridge hueBridge = new HueBridge();
        hueBridge.setId(1);
        List<HueLightbulbWhite> hueLightbulbWhites = new ArrayList<>();
        HueLightbulbWhite hueLightbulbWhite = new HueLightbulbWhite();
        hueLightbulbWhites.add(hueLightbulbWhite);

        doReturn(Observable.just(true)).when(mMockDataManager).insertHueBridge(any(HueBridge.class));
        doReturn(Observable.just(hueBridge)).when(mMockDataManager).getLastInsertedHueBridge();
        doReturn(Observable.just(true)).when(mMockDataManager).insertWhiteHueLightbulb(any(HueLightbulbWhite.class));

        //Act
        addSmartDeviceViewModel.insertBridge(hueBridge, 1, hueLightbulbWhites);
        mTestScheduler.triggerActions();
        //Assert
        //Does not return anything
    }
    @Test
    public void insertNesttodb(){
        //Arrange
        NestHub nestHub = new NestHub("1", 1, "12", "123", 12l);
        nestHub.setId(1);
        List<NestThermostat> nestThermostatList = new ArrayList<>();
        NestThermostat nestThermostat = new NestThermostat();
        nestThermostat.setDeviceId("adasda12");
        nestThermostat.setName("name");
        nestThermostat.setSmartDeviceId(1);
        nestThermostat.setNestHubId(1);
        nestThermostat.setId(1);
        nestThermostatList.add(nestThermostat);

        doReturn(Observable.just(true)).when(mMockDataManager).insertNestHub(any(NestHub.class));
        doReturn(Observable.just(nestHub)).when(mMockDataManager).getLastInsertedNestHub();
        doReturn(Observable.just(true)).when(mMockDataManager).insertNestThermostat(any(NestThermostat.class));
        //Act
        addSmartDeviceViewModel.insertNesttodb(nestHub, nestThermostatList, 1);
        mTestScheduler.triggerActions();

        //Assert
        verify(addSmartDeviceNavigator).ChangeToSmartDevice();
    }
    @Test
    public void NestExists(){
        //Arrange
        List<NestHub> nestHubs = new ArrayList<>();
        doReturn(Observable.just(nestHubs)).when(mMockDataManager).getAllNestHubs();
        //Act
        addSmartDeviceViewModel.NestExists();
        mTestScheduler.triggerActions();
        //Assert
        verify(addSmartDeviceNavigator).searchForNest(null);
    }
    @Test
    public void searchBridge(){
        //Arrange
        //Act
        addSmartDeviceViewModel.searchBridge();
        //Assert
        verify(addSmartDeviceNavigator).searchForBridge();
    }
    @Test
    public void searchNest(){
        //Arrange
        List<NestHub> nestHubs = new ArrayList<>();
        //Act
        addSmartDeviceViewModel.searchNest(nestHubs);
        //Assert
        verify(addSmartDeviceNavigator).searchForNest(any(List.class));
    }
    @Test
    public void smartDeviceinsertHandler(){
        //Arrange
        SmartDevice smartDevice = new SmartDevice("Name", true, 1);
        smartDevice.setId(1);
        HueBridge hueBridge = new HueBridge();
        hueBridge.setId(1);
        hueBridge.setSmartDeviceId(1);
        List<HueLightbulbWhite> hueLightbulbWhites = new ArrayList<>();
        HueLightbulbWhite hueLightbulbWhite = new HueLightbulbWhite();
        hueLightbulbWhite.setSmartDeviceId(1);
        hueLightbulbWhites.add(hueLightbulbWhite);

        doReturn(Observable.just(true)).when(mMockDataManager).insertSmartDevice(any(SmartDevice.class));
        doReturn(Observable.just(smartDevice)).when(mMockDataManager).getLastSmartDevice();
        doReturn(Observable.just(true)).when(mMockDataManager).insertHueBridge(any(HueBridge.class));
        doReturn(Observable.just(hueBridge)).when(mMockDataManager).getLastInsertedHueBridge();
        doReturn(Observable.just(true)).when(mMockDataManager).insertWhiteHueLightbulb(any(HueLightbulbWhite.class));

        //Act
        addSmartDeviceViewModel.smartDeviceinsertHandler(smartDevice, hueBridge, hueLightbulbWhites);
        mTestScheduler.triggerActions();
        //Assert
        //Does not return anything


    }



}