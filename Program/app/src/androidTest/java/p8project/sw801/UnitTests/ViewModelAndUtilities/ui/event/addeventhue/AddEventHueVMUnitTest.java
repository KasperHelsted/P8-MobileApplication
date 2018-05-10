package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.addeventhue;

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
import p8project.sw801.ui.event.addeventhue.AddEventHueNavigator;
import p8project.sw801.ui.event.addeventhue.AddEventHueViewModel;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddEventHueVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddEventHueNavigator mAddEventHueCallback;
    @Mock
    DataManager mMockDataManager;

    private AddEventHueViewModel mAddEventHueViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mAddEventHueViewModel = new AddEventHueViewModel(mMockDataManager, testSchedulerProvider);
        mAddEventHueViewModel.setNavigator(mAddEventHueCallback);
    }

    @Test
    public void testTurnOnClickAddEventHue(){
        //Arrange
        //Act
        mAddEventHueViewModel.turnOnClick();
        //Assert
        verify(mAddEventHueCallback).turnOn();
    }

    @Test
    public void testTurnOffClickAddEventHue(){
        //Arrange
        //Act
        mAddEventHueViewModel.turnOffClick();
        //Assert
        verify(mAddEventHueCallback).turnOff();
    }

    @Test
    public void testSetBrightnessClickAddEventHue(){
        //Arrange
        //Act
        mAddEventHueViewModel.setBrightnessClick();
        //Assert
        verify(mAddEventHueCallback).setBrightness();
    }
}
