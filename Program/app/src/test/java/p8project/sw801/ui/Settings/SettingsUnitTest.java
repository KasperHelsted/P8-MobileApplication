package p8project.sw801.ui.Settings;

import org.junit.After;
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
public class SettingsUnitTest {
    @Mock
    SettingsNavigator settingsNavigator;
    @Mock
    DataManager mMockDataManager;

    private SettingsViewModel settingsViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        settingsViewModel = new SettingsViewModel(mMockDataManager, testSchedulerProvider);
        settingsViewModel.setNavigator(settingsNavigator);

    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        settingsViewModel = null;
        settingsNavigator = null;
    }
    @Test
    public void navigateToGlobalMute(){
        //Arrange
        //Act
        settingsViewModel.navigateToGlobalMute();
        //Assert
        verify(settingsNavigator).navigateToGlobalMute();

    }
    @Test
    public void navigateToPredefinedLocation(){
        //Arrange
        //Act
        settingsViewModel.navigateToPredefinedLocation();
        //Assert
        verify(settingsNavigator).navigateToPredefinedLocation();
    }
    @Test
    public void navigateToPreferedShopping(){
        //Arrange
        //Act
        settingsViewModel.navigateToPreferedShopping();
        //Assert
        verify(settingsNavigator).navigateToPreferedShopping();
    }
}
