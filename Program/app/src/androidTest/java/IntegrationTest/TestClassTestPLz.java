package IntegrationTest;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import IntegrationTest.utils.rx.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingViewModel;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TestClassTestPLz {
    @Mock
    AddGlobalMuteSettingNavigator mAddGlobalMuteCallback;
    @Mock
    DataManager mMockDataManager;
    private AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mAddGlobalMuteSettingViewModel = new AddGlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        mAddGlobalMuteSettingViewModel.setNavigator(mAddGlobalMuteCallback);
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mAddGlobalMuteSettingViewModel = null;
        mAddGlobalMuteCallback = null;
    }

    @Test
    public void testServerLoginSuccess() {
        String email = "dummy@gmail.com";
        String password = "password";

        LoginResponse loginResponse = new LoginResponse();

        doReturn(Single.just(loginResponse))
                .when(mMockDataManager)
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password));

        mAddGlobalMuteSettingViewModel.submitGlobalMuteClick();
        mTestScheduler.triggerActions();

        verify(mAddGlobalMuteCallback).finish();
    }
}
