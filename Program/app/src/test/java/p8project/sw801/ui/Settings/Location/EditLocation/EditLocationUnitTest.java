package p8project.sw801.ui.Settings.Location.EditLocation;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.utils.rx.TestSchedulerProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EditLocationUnitTest {
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
        //Arrange
        doReturn(Observable.just(new PredefinedLocation("name", 123))).when(mMockDataManager).getPredefinedLocationById(any(int.class));
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
        PredefinedLocation predefinedLocation = new PredefinedLocation("name", 1234);
        //Act
        editLocationViewModel.updatePredefinedLoc(coordinate, locName, predefinedLocation);
        mTestScheduler.triggerActions();
        //Assert
        verify(editLocationNavigator).openLocationActivty();
    }
}