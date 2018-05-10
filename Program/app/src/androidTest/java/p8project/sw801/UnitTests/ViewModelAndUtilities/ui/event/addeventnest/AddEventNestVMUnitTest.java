package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.addeventnest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.event.addeventnest.AddEventNestNavigator;
import p8project.sw801.ui.event.addeventnest.AddEventNestViewModel;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddEventNestVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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
