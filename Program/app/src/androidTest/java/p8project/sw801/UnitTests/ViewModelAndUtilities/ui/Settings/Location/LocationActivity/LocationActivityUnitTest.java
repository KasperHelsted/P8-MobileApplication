package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.Location.LocationActivity;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.Settings.Location.LocationNavigator;
import p8project.sw801.ui.Settings.Location.LocationViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class LocationActivityUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    LocationNavigator locationNavigator;
    @Mock
    DataManager mMockDataManager;

    private LocationViewModel locationViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        locationViewModel = new LocationViewModel(mMockDataManager, testSchedulerProvider);
        locationViewModel.setNavigator(locationNavigator);


    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        locationViewModel = null;
        locationNavigator = null;
    }

    @Test
    public void createLocation() {
        //Arrange
        //Act
        locationViewModel.createLocation();
        //Assert
        verify(locationNavigator).createLocation();
    }

    @Test
    public void getLatestPredefinedLocationData() {
        //Arrange
        List<PredefinedLocation> locationList = new ArrayList<>();
        doReturn(Observable.just(locationList)).when(mMockDataManager).getAllPredefinedLocations();
        //Act
        locationViewModel.getLatestPredefinedLocationData();
        mTestScheduler.triggerActions();
        //Assert
        verify(locationNavigator).createList(any(List.class));
    }

    @Test
    public void onLocationClicked() {
        //Arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation("name");
        //Act
        locationViewModel.onLocationClicked(predefinedLocation);
        //Assert
        verify(locationNavigator).onLocationClicked(any(PredefinedLocation.class));
    }

    @Test
    public void removePredefinedLocation() {
        //Arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation("name");
        doReturn(Observable.just(true)).when(mMockDataManager).deletePredefinedLocation(any(PredefinedLocation.class));
        //Act
        locationViewModel.removePredefinedLocation(predefinedLocation);
        //Assert
        //Does not return anything
    }
}