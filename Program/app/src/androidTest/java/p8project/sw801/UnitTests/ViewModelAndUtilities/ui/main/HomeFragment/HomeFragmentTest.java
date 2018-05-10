package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.main.HomeFragment;

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

import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragmentNavigator;
import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragmentViewModel;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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