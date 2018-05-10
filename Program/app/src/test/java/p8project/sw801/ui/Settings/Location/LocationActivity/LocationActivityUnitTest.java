package p8project.sw801.ui.Settings.Location.LocationActivity;

import org.junit.After;
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
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.Settings.Location.LocationNavigator;
import p8project.sw801.ui.Settings.Location.LocationViewModel;
import p8project.sw801.utils.rx.TestSchedulerProvider;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocationActivityUnitTest {
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
    public void createLocation(){
        //Arrange
        //Act
        locationViewModel.createLocation();
        //Assert
        verify(locationNavigator).createLocation();
    }
    @Test
    public void getLatestPredefinedLocationData(){
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
    public void onLocationClicked(){
        //Arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation("name", 123);
        //Act
        locationViewModel.onLocationClicked(predefinedLocation);
        //Assert
        verify(locationNavigator).onLocationClicked(any(PredefinedLocation.class));
    }
    @Test
    public void removePredefinedLocation(){
        //Arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation("name", 123);
        doReturn(Observable.just(true)).when(mMockDataManager).deletePredefinedLocation(any(PredefinedLocation.class));
        //Act
        locationViewModel.removePredefinedLocation(predefinedLocation);
        //Assert
        //Does not return anything
    }
}