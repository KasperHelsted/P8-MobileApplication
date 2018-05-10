package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.GlobalMuteSettingUnitTest;

import android.databinding.ObservableArrayList;
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
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class GlobalMuteSettingUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    GlobalMuteSettingNavigator globalMuteSettingNavigator;
    @Mock
    DataManager mMockDataManager;

    private GlobalMuteSettingViewModel globalMuteSettingViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        ObservableArrayList<GlobalMute> globalMuteObservableArrayList = new ObservableArrayList<>();
        globalMuteObservableArrayList.add(new GlobalMute(
                "Name",
                100L,
                101L,
                null,
                "Comment"
        ));


        doReturn(Observable.just(globalMuteObservableArrayList))
                .when(mMockDataManager)
                .getAllGlobalMutes();

        globalMuteSettingViewModel = new GlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        globalMuteSettingViewModel.setNavigator(globalMuteSettingNavigator);



    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        globalMuteSettingViewModel = null;
        globalMuteSettingNavigator = null;
    }

    @Test
    public void getListFromDb() {
        //Arrange

        //Act
        globalMuteSettingViewModel.getListFromDb();
        mTestScheduler.triggerActions();
        verify(globalMuteSettingNavigator, times(2)).updatelist();
        //Assert

    }
    @Test
    public void deleteGlobalMute() {
        //Arrange
        GlobalMute globalMute = new GlobalMute();

        doReturn(Observable.just(true)).when(mMockDataManager).deleteGlobalMute(any(GlobalMute.class));
        //Act

        globalMuteSettingViewModel.deleteGlobalMute(globalMute);
        mTestScheduler.triggerActions();
        //Assert
        verify(globalMuteSettingNavigator, times(2)).updatelist();

    }
    @Test
    public void RenderList() {
        //Arrange
        List<GlobalMute> globalMuteList= new ArrayList<>();
        globalMuteList.add(new GlobalMute(
                "Name",
                100L,
                101L,
                null,
                "Comment"));
        //Act
        globalMuteSettingViewModel.RenderList(globalMuteList);
        verify(globalMuteSettingNavigator).updatelist();
        //Assert

    }
    @Test
    public void showAddGlobalMuteSetting() {
        //Arrange
        //Act
        globalMuteSettingViewModel.showAddGlobalMuteSetting();
        //Assert
        verify(globalMuteSettingNavigator).openAddGlobalMuteSettingActivity();
    }

}