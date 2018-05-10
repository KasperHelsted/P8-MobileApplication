package p8project.sw801.ui.event.createeventmap;

import android.location.Location;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateEventMapVMUnitTest {
    @Mock
    CreateEventMapNavigator mCreateEventMapCallback;
    @Mock
    DataManager mMockDataManager;

    private CreateEventMapViewModel mCreateEventMapViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        mCreateEventMapViewModel = new CreateEventMapViewModel(mMockDataManager, testSchedulerProvider);
        mCreateEventMapViewModel.setNavigator(mCreateEventMapCallback);
    }

    @Test
    public void testCancelButtonCreateEventMap(){
        //Arrange
        //Act
        mCreateEventMapViewModel.cancleButton();
        //Assert
        verify(mCreateEventMapCallback).cancelButton();
    }

    @Test
    public void testConfirmButtonCreateEventMap(){
        //Arrange
        //Act
        mCreateEventMapViewModel.confirmButton();
        //Assert
        verify(mCreateEventMapCallback).confirmButton();
    }


}
