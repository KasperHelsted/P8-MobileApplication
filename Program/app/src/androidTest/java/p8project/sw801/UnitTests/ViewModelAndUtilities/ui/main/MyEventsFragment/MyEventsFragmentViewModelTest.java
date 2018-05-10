package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.main.MyEventsFragment;

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
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentNavigator;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MyEventsFragmentViewModelTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    MyEventsFragmentNavigator myEventsFragmentCallback;
    @Mock
    DataManager mMockDataManager;

    private MyEventsFragmentViewModel myEventsFragmentViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);



        List<Event> events = new ArrayList<>();
        events.add(new Event("Name", true));

        doReturn(Observable.just(events))
                .when(mMockDataManager)
                .getAllEvents();

        myEventsFragmentViewModel = new MyEventsFragmentViewModel(mMockDataManager, testSchedulerProvider);
        myEventsFragmentViewModel.setNavigator(myEventsFragmentCallback);
    }



    @After
    public void tearDown() {
        mTestScheduler = null;
        myEventsFragmentViewModel = null;
        myEventsFragmentCallback = null;
    }

    @Test
    public void RenderList() {
        //Arrange
        List<Event> e = new ArrayList<>();
        e.add(new Event(
                "name",
                true
        ));
        //Act
        myEventsFragmentViewModel.RenderList(e);
        //Assert
        verify(myEventsFragmentCallback).updatelist();
    }
    @Test
    public void fetchFromDatabase(){

        //Act
        myEventsFragmentViewModel.addNewEvent();

        //Assert
        verify(myEventsFragmentCallback).addNewEvent();
    }

    @Test
    public void updateEvent(){
        //Arrange
        Event e = new Event();
        e.setName("Name");
        e.setActive(Boolean.TRUE);
        doReturn(Observable.just(true)).when(mMockDataManager).updateEvent(any(Event.class));

        //Act
        myEventsFragmentViewModel.updateEvent(e, Boolean.FALSE);
        mTestScheduler.triggerActions();
        //Assert
        verify(myEventsFragmentCallback, times(2)).updatelist();
    }

    @Test
    public void addNewEvent(){
        //Arrange
        //Act
        myEventsFragmentViewModel.addNewEvent();
        //Assert
        verify(myEventsFragmentCallback).addNewEvent();
    }


}
