package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.Location.EditLocation;


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

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.UnitTests.ViewModelAndUtilities.rx.TestSchedulerProvider;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationNavigator;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class EditLocationUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    EditLocationNavigator editLocationNavigator;
    @Mock
    DataManager mMockDataManager;

    private EditLocationViewModel editLocationViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        editLocationViewModel = new EditLocationViewModel(mMockDataManager, testSchedulerProvider);
        editLocationViewModel.setNavigator(editLocationNavigator);



    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        editLocationViewModel = null;
        editLocationNavigator = null;
    }

    @Test
    public void getLocationFromId(){
        PredefinedLocation predefinedLocation = new PredefinedLocation("name");
        predefinedLocation.setCoordinateId(1);
        predefinedLocation.setId(1);

        //Arrange
        doReturn(Observable.just(predefinedLocation)).when(mMockDataManager).getPredefinedLocationById(any(int.class));
        doReturn(Observable.just(new Coordinate(12.21, 34.43))).when(mMockDataManager).getCoordinateById(any(Integer.class));

        //Act
        editLocationViewModel.getLocationFromId(1);
        mTestScheduler.triggerActions();
        //Assert
        verify(editLocationNavigator).renderFields(any(PredefinedLocation.class), any(Coordinate.class));

    }
    @Test
    public void showMapActivity(){
        //Arrange
        //Act
        editLocationViewModel.showMapActivity();
        //Assert
        verify(editLocationNavigator).openCreateMapActivity();
    }
    @Test
    public void submitEditLocationClick(){
        //Arrange
        //Act
        editLocationViewModel.submitEditLocationClick();
        //Assert
        verify(editLocationNavigator).submitEditLocationClick();
    }
    @Test
    public void updatePredefinedLoc(){
        //Arrange
        doReturn(Observable.just(true)).when(mMockDataManager).updateCoordinate(any(Coordinate.class));
        doReturn(Observable.just(true)).when(mMockDataManager).updatePredefinedLocation(any(PredefinedLocation.class));


        Coordinate coordinate = new Coordinate(12.21, 34.43);
        String locName = "name";
        PredefinedLocation predefinedLocation = new PredefinedLocation("name");
        //Act
        editLocationViewModel.updatePredefinedLoc(coordinate, locName, predefinedLocation);
        mTestScheduler.triggerActions();
        //Assert
        verify(editLocationNavigator).openLocationActivty();
    }
}