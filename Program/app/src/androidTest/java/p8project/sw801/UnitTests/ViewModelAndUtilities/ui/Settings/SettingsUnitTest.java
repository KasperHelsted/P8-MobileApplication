package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings;

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
import p8project.sw801.ui.Settings.SettingsNavigator;
import p8project.sw801.ui.Settings.SettingsViewModel;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class SettingsUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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
    public void navigateToGlobalMute() {
        //Arrange
        //Act
        settingsViewModel.navigateToGlobalMute();
        //Assert
        verify(settingsNavigator).navigateToGlobalMute();

    }

    @Test
    public void navigateToPredefinedLocation() {
        //Arrange
        //Act
        settingsViewModel.navigateToPredefinedLocation();
        //Assert
        verify(settingsNavigator).navigateToPredefinedLocation();
    }

    @Test
    public void navigateToPreferedShopping() {
        //Arrange
        //Act
        settingsViewModel.navigateToPreferedShopping();
        //Assert
        verify(settingsNavigator).navigateToPreferedShopping();
    }
}
