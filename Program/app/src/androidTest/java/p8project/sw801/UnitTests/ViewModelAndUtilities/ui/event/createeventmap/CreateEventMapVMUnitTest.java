package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.event.createeventmap;

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
import p8project.sw801.ui.event.createeventmap.CreateEventMapNavigator;
import p8project.sw801.ui.event.createeventmap.CreateEventMapViewModel;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class CreateEventMapVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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
    public void testCancelButtonCreateEventMap() {
        //Arrange
        //Act
        mCreateEventMapViewModel.cancleButton();
        //Assert
        verify(mCreateEventMapCallback).cancelButton();
    }

    @Test
    public void testConfirmButtonCreateEventMap() {
        //Arrange
        //Act
        mCreateEventMapViewModel.confirmButton();
        //Assert
        verify(mCreateEventMapCallback).confirmButton();
    }


}
