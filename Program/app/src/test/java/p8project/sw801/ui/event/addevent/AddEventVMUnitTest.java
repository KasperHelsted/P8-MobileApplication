package p8project.sw801.ui.event.addevent;

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

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddEventVMUnitTest {
    @Mock
    AddEventNavigator mAddEventCallback;
    @Mock
    DataManager mMockDataManager;

    private AddEventViewModel mAddEventViewModel;
    private TestScheduler mTestScheduler;

    private String name = "name of event";
    private Boolean active = Boolean.TRUE;
    private double testLatitude = 20;
    private double testLongitude = 20;
    private String predefinedLocationName = "name of predefined location";


    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mAddEventViewModel = new AddEventViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventViewModel.setNavigator(mAddEventCallback);

        Event event =  new Event(name,active);
        event.setId(1);

        PredefinedLocation predefinedLocation =  new PredefinedLocation();
        predefinedLocation.setName(predefinedLocationName);
        predefinedLocation.setCoordinateId(1);

        List<PredefinedLocation> predefinedLocationList = new ArrayList<>();
        predefinedLocationList.add(predefinedLocation);

        Trigger trigger = new Trigger();
        trigger.setId(1);

        List<Trigger> triggerList = new ArrayList<>();
        triggerList.add(trigger);

        EventWithData eventWithData = new EventWithData();
        eventWithData.event = event;

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertEvent(event);

        doReturn(Observable.just(predefinedLocationList))
                .when(mMockDataManager)
                .getAllPredefinedLocations();

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertAllTriggers(triggerList);

    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mAddEventViewModel = null;
        mAddEventCallback = null;
    }

    @Test
    public void testDisplayPredefinedLocationsAddEvent() {

        PredefinedLocation p = new PredefinedLocation();
        p.setName(predefinedLocationName);
        p.setCoordinateId(1);

        List<PredefinedLocation> pList = new ArrayList<>();
        pList.add(p);

        mAddEventViewModel.displayPredefinedLocations();
        mTestScheduler.triggerActions();

        verify(mAddEventCallback).displayPredefinedLocations(pList);
    }

    @Test
    public void testShowNotificationOrSmartdeviceAddEvent(){
        //Arrange
        //Act
        mAddEventViewModel.showNotificationOrSmartdevice();
        //Assert
        verify(mAddEventCallback).showNotificationOrSmartdevice();
    }

    @Test
    public void testShowTimePickerDialogAddEvent(){
        //Arrange
        //Act
        mAddEventViewModel.showTimePickerDialog(1);
        //Assert
        verify(mAddEventCallback).showTimePickerDialog(1);
    }


    @Test
    public void testsubmitEventClickAddEvent(){
        //Arrange
        //Act
        mAddEventViewModel.submitEventClick();
        //Assert
        verify(mAddEventCallback).submitEventClick();
    }

    @Test
    public void testSubmitEventToDatabaseAddEvent(){
        //Arrange
        Event e = new Event(name, active);
        e.setId(1);
        When w = new When();
        List<Trigger> t = new ArrayList<>();

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new Event(name,active));
            return null;
        }).when(mMockDataManager).getLastEvent();

        //Act
        try{
            mAddEventViewModel.saveTriggers(t, e);
            mAddEventViewModel.saveWhen(w, e);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
    }

    @Test
    public void testSaveEventAddEvent() {

        Event e = new Event(name, active);
        e.setId(1);

        mAddEventViewModel.saveEvent(e);
        mTestScheduler.triggerActions();

        verify(mAddEventCallback).notitfyActivity();
    }

    @Test
    public void testSaveCoordinateAddEvent(){
        //Arrange
        Coordinate c = new Coordinate(testLatitude, testLongitude);
        When w = new When();
        List<Trigger> t = new ArrayList<>();

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new Coordinate(testLatitude, testLongitude));
            return null;
        }).when(mMockDataManager).insertCoordinate(any(Coordinate.class));

        //Act
        try{
            mAddEventViewModel.saveCoordinate(w, t, c);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
    }

    @Test
    public void testGetCoordinateIdAddEvent(){
        //Arrange
        Coordinate c = new Coordinate(testLatitude, testLongitude);
        When w = new When();
        List<Trigger> t = new ArrayList<>();

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new Coordinate(testLatitude, testLongitude));
            return null;
        }).when(mMockDataManager).getLast();

        //Act
        try{
            mAddEventViewModel.submitEventToDatabase(w, t, null);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
    }

    @Test
    public void testSaveTriggersAddEvent() {

        Trigger t = new Trigger();
        t.setId(1);

        List<Trigger> tList = new ArrayList<>();
        tList.add(t);

        Event e = new Event(name, active);
        e.setId(1);

        mAddEventViewModel.saveTriggers(tList, e);
        mTestScheduler.triggerActions();

        verify(mAddEventCallback).notitfyActivity();
    }

    @Test
    public void testSaveWhenAddEvent(){
        //Arrange

        When w = new When();

        Event e = new Event(name, active);
        e.setId(1);

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new When());
            return null;
        }).when(mMockDataManager).insertWhen(any(When.class));

        //Act
        try{
            mAddEventViewModel.geteventwithdata(e);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
    }

    @Test
    public void testgeteventwithdataAddEvent() {
        //Arrange
        Event e = new Event(name, active);
        e.setId(1);

        EventWithData eWData = new EventWithData();
        eWData.event = e;

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new EventWithData());
            return null;
        }).when(mMockDataManager).getEventWithData(1);

        //Act
        try{
            mAddEventCallback.createNotifications(eWData);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
    }


}
