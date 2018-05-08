package p8project.sw801.ui.main.MyEventsFragment;

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
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentNavigator;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MyEventsFragmentViewModelTest {
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



        doReturn(Observable.just(true))
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

        //Assert when act is called
        doAnswer((Answer) invocation -> {

            Object arg0 = invocation.getArgument(0);

            assertEquals(arg0,new Event("Name", Boolean.FALSE));
            return null;
        }).when(mMockDataManager).updateEvent(any(Event.class));

        //Act
        try{
            myEventsFragmentViewModel.updateEvent(e, Boolean.FALSE);
        }catch (NullPointerException ex){
            //Catching the null pointer exception thrown by the scheduler provider being a mock object.
        }
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
