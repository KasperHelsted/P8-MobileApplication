package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.Location.AddLocaiton;

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
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationNavigator;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class AddLocationUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddLocationNavigator addLocationNavigator;
    @Mock
    DataManager mMockDataManager;

    private AddLocationViewModel addLocationViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        addLocationViewModel = new AddLocationViewModel(mMockDataManager, testSchedulerProvider);
        addLocationViewModel.setNavigator(addLocationNavigator);



    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        addLocationViewModel = null;
        addLocationNavigator = null;
    }

    @Test
    public void openLocationActivty(){
        //Arrange
        PredefinedLocation predefinedLocation = new PredefinedLocation("Location");
        //Act
        addLocationViewModel.openLocationActivty(predefinedLocation);
        //Assert
        verify(addLocationNavigator).openLocationActivty(predefinedLocation);
    }
    @Test
    public void showMapActivity(){
        //Arrange
        //Act
        addLocationViewModel.showMapActivity();
        //Assert
        verify(addLocationNavigator).openCreateMapActivity();

    }
    @Test
    public void submitLocationClick(){
        //Arrange
        //Act
        addLocationViewModel.submitLocationClick();
        //Assert
        verify(addLocationNavigator).submitLocationClick();


    }
    @Test
    public void submitLocationToDatabase(){
        //Arrange
        String locName = "Name";
        Coordinate coordinate = new Coordinate(21.12, 34.65);


        doReturn(Observable.just(true)).when(mMockDataManager).insertCoordinate(any(Coordinate.class));
        doReturn(Observable.just(new Coordinate(21.21,34.43))).when(mMockDataManager).getLast();
        doReturn(Observable.just(true)).when(mMockDataManager).insertPredefinedLocation(any(PredefinedLocation.class));
        doReturn(Observable.just(new PredefinedLocation("name"))).when(mMockDataManager).getLastPredefinedLocation();
        //Act
        addLocationViewModel.submitLocationToDatabase(locName, coordinate);
        mTestScheduler.triggerActions();
        //Assert
        verify(addLocationNavigator).openLocationActivty(any(PredefinedLocation.class));

    }



}