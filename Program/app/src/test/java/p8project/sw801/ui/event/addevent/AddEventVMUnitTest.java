package p8project.sw801.ui.event.addevent;

import org.junit.After;
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
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
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
    private String triggerName = "name of trigger";


    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mAddEventViewModel = new AddEventViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventViewModel.setNavigator(mAddEventCallback);

        Coordinate coordinate = new Coordinate(testLatitude,testLongitude);
        coordinate.setId(1);

        Event event =  new Event(name,active);
        event.setId(1);

        Trigger trigger = new Trigger();
        trigger.setId(1);

        List<Trigger> triggerList = new ArrayList<>();
        triggerList.add(trigger);

        TriggerWithSmartDevice triggerWithSmartDevice = new TriggerWithSmartDevice();
        triggerWithSmartDevice.trigger = trigger;

        When when = new When();
        when.setId(1);

        WhenWithCoordinate whenWithCoordinate = new WhenWithCoordinate();
        whenWithCoordinate.when = when;

        EventWithData eventWithData =  new EventWithData();
        eventWithData.event = event;
        //eventWithData.triggers.add(triggerWithSmartDevice);
        //eventWithData.whens.add(whenWithCoordinate);


        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertEvent(event);

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertCoordinate(coordinate);

        doReturn(Observable.just(coordinate))
                .when(mMockDataManager)
                .getLast();

        doReturn(Observable.just(event))
                .when(mMockDataManager)
                .getLastEvent();

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertAllTriggers(triggerList);

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertWhen(when);

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .getEventWithData(eventWithData.event.getId());
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mAddEventViewModel = null;
        mAddEventCallback = null;
    }

    @Test
    public void testSaveEventAddEvent() {

        Event e = new Event(name, active);

        mAddEventViewModel.saveEvent(e);
        mTestScheduler.triggerActions();

        verify(mAddEventCallback).notitfyActivity();
    }

    @Test
    public void testSaveCoordinateAddEvent() {

        Event e = new Event();
        When w = new When();
        List<Trigger> t = new ArrayList<>();
        Coordinate c = new Coordinate(testLatitude, testLongitude);
        EventWithData eWD = new EventWithData();

        mAddEventViewModel.saveCoordinate(w, t, c);
        mTestScheduler.triggerActions();

        verify(mAddEventCallback).createNotifications(eWD);
    }
}
