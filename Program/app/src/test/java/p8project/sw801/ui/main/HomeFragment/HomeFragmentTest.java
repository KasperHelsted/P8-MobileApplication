package p8project.sw801.ui.main.HomeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragmentNavigator;
import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragmentViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomeFragmentTest {
    @Mock
    HomeFragmentNavigator homeFragmentNavigator;
    @Mock
    DataManager mMockDataManager;


    private HomeFragmentViewModel homeFragmentViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);


        homeFragmentViewModel = new HomeFragmentViewModel(mMockDataManager, testSchedulerProvider);
        homeFragmentViewModel.setNavigator(homeFragmentNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        homeFragmentViewModel = null;
        homeFragmentNavigator = null;
    }

    @Test
    public void buttonCreateEventOnClick(){
        //Arrange
        //Act
        homeFragmentViewModel.buttonCreateEventOnClick();
        //Assert
        verify(homeFragmentNavigator).buttonCreateEventOnClick();
    }
    @Test
    public void buttonMyEventsOnClick(){
        //Arrange
        //Act
        homeFragmentViewModel.buttonMyEventsOnClick();
        //Assert
        verify(homeFragmentNavigator).buttonMyEventsOnClick();

    }
    @Test
    public void buttonMySmartDevicesOnClick(){
        //Arrange
        //Act
        homeFragmentViewModel.buttonMySmartDevicesOnClick();
        //Assert
        verify(homeFragmentNavigator).buttonMySmartDevicesOnClick();

    }
    @Test
    public void buttonSettingsOnClick(){
        //Arrange
        //Act
        homeFragmentViewModel.buttonSettingsOnClick();
        //Assert
        homeFragmentNavigator.buttonSettingsOnClick();
    }
}