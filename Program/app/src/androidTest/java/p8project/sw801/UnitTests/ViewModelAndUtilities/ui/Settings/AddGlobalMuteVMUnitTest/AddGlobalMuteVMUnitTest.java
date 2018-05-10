package p8project.sw801.UnitTests.ViewModelAndUtilities.ui.Settings.AddGlobalMuteVMUnitTest;

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
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingViewModel;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4.class)
public class AddGlobalMuteVMUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    AddGlobalMuteSettingNavigator mAddGlobalMuteCallback;
    @Mock
    DataManager mMockDataManager;

    private AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
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

        mAddGlobalMuteSettingViewModel = new AddGlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        mAddGlobalMuteSettingViewModel.setNavigator(mAddGlobalMuteCallback);

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertGlobalMute(
                        new GlobalMute(
                                name,
                                startTime,
                                endTime,
                                null,
                                comment
                        )
                );
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mAddGlobalMuteSettingViewModel = null;
        mAddGlobalMuteCallback = null;
    }

    @Test
    public void testSaveAddGlobalMute() {

        mAddGlobalMuteSettingViewModel.save(
                name,
                startTime,
                endTime,
                comment
        );
        mTestScheduler.triggerActions();

        verify(mAddGlobalMuteCallback).finish();
    }
}
