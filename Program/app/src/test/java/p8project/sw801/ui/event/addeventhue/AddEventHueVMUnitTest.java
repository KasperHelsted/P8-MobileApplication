package p8project.sw801.ui.event.addeventhue;

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
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddEventHueVMUnitTest {
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
