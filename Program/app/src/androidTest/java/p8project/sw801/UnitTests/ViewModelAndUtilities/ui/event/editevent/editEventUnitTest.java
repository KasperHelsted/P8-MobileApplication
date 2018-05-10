package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.editevent;

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

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.event.editevent.EditEventNavigator;
import p8project.sw801.ui.event.editevent.EditEventViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class editEventUnitTest {


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    EditEventNavigator editEventNavigator;
    @Mock
    DataManager mMockDataManager;


    private EditEventViewModel editEventViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);


        editEventViewModel = new EditEventViewModel(mMockDataManager, testSchedulerProvider);
        editEventViewModel.setNavigator(editEventNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        editEventViewModel = null;
        editEventNavigator = null;
    }

    @Test
    public void addEvent(){
        //Arrange
        //Act
        editEventViewModel.addEvent();
        //Assert
        verify(editEventNavigator).addEventTrigger();
    }

    @Test
    public void chooseLocation(){
        //Arrange
        //Act
        editEventViewModel.chooseLocation();
        //Assert
        verify(editEventNavigator).chooseLocation();
    }

    @Test
    public void close(){
        //Arrange
        //Act
        editEventViewModel.close();
        //Assert
        verify(editEventNavigator).cancelEditEvent();
    }

    @Test
    public void deleteTrigger(){
        //Arrange
        Trigger t = new Trigger();
        doReturn(Observable.just(true)).when(mMockDataManager).deleteTrigger(any(Trigger.class));
        //Act
        editEventViewModel.deleteTrigger(t);
        //Assert
        //Returns nothing
    }
}