package p8project.sw801.ui.event.addeventnest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddEventNestVMUnitTest {
    @Mock
    AddEventNestNavigator mAddEventNestCallback;
    @Mock
    DataManager mMockDataManager;

    private AddEventNestViewModel mAddEventNestViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mAddEventNestViewModel = new AddEventNestViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventNestViewModel.setNavigator(mAddEventNestCallback);
    }

    @Test
    public void testTurnOnClickAddEventNest(){
        //Arrange
        //Act
        mAddEventNestViewModel.turnOnClick();
        //Assert
        verify(mAddEventNestCallback).turnOn();
    }

    @Test
    public void testTurnOffClickAddEventNest(){
        //Arrange
        //Act
        mAddEventNestViewModel.turnOffClick();
        //Assert
        verify(mAddEventNestCallback).turnOff();
    }

    @Test
    public void testSetTempClickAddEventNest(){
        //Arrange
        //Act
        mAddEventNestViewModel.setTempClick();
        //Assert
        verify(mAddEventNestCallback).setTemp();
    }

}
