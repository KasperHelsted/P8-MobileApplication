package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.EditGlobalMuteSettingUnitTest;

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
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.EditGlobalMuteSetting.EditGlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.EditGlobalMuteSetting.EditGlobalMuteSettingViewModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4.class)
public class EditGlobalMuteSettingUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    EditGlobalMuteSettingNavigator editGlobalMuteSettingNavigator;
    @Mock
    DataManager mMockDataManager;

    private EditGlobalMuteSettingViewModel editGlobalMuteSettingViewModel;
    private TestScheduler mTestScheduler;

    private String name = "name of mute";
    private String comment = "No note!";
    private Long startTime = 10000L;
    private Long endTime = 10000L;


    @BeforeClass
    public static void onlyOnce() {
    }

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

        editGlobalMuteSettingViewModel = new EditGlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        editGlobalMuteSettingViewModel.setNavigator(editGlobalMuteSettingNavigator);

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .updateGlobalMute(any(GlobalMute.class
                        )
                );
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        editGlobalMuteSettingViewModel = null;
        editGlobalMuteSettingNavigator = null;
    }

    @Test
    public void submitGlobalMuteClick() {
        //Arrange
        editGlobalMuteSettingViewModel.globulMuteName.set("sdasada");
        //Act
        editGlobalMuteSettingViewModel.submitGlobalMuteClick();
        //Assert
        mTestScheduler.triggerActions();
        verify(editGlobalMuteSettingNavigator).sendNotification(any(String.class));
    }

}

