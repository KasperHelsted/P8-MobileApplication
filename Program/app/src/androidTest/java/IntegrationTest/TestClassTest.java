package IntegrationTest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import IntegrationTest.utils.rx.TestSchedulerProvider;
import io.reactivex.schedulers.TestScheduler;
import p8project.sw801.R;
import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.dao.PredefinedLocationDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingNavigator;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingViewModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestClassTest {
    @Mock
    AddGlobalMuteSettingNavigator mAddGlobalMuteSettingNavigator;
    @Mock
    DataManager mMockDataManager;
    private AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private TestScheduler mTestScheduler;
    private PredefinedLocationDao mPredefinedLocationDao;
    private GlobalMuteDao mGlobalMuteDao;
    private AppDatabase mDb;
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<AddGlobalMuteSettingActivity> mActivityRule = new ActivityTestRule<>(
            AddGlobalMuteSettingActivity.class);

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mGlobalMuteDao = mDb.globalMuteDao();
    }

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "test";
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mAddGlobalMuteSettingViewModel = new AddGlobalMuteSettingViewModel(mMockDataManager, testSchedulerProvider);
        mAddGlobalMuteSettingViewModel.setNavigator(mAddGlobalMuteSettingNavigator);
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testSubmitGlobalMuteClickAddGlobalMute2() throws Exception {
        // arrange

        mAddGlobalMuteSettingViewModel.globulMuteName.set("test");
        mAddGlobalMuteSettingViewModel.comment.set("hello");

        mAddGlobalMuteSettingViewModel.startTimeLong = 100L;
        mAddGlobalMuteSettingViewModel.endTimeLong = 100L;

        // act
        mAddGlobalMuteSettingViewModel.submitGlobalMuteClick();
        mTestScheduler.triggerActions();
        List<GlobalMute> dbGlobalMute = this.mGlobalMuteDao.getAll();
        String test = "test";

        // assert
        assertEquals(dbGlobalMute.get(0).getName(), test);
    }

    @Test
    public void testSubmitGlobalMuteClickAddGlobalMute3() throws Exception {
        // arrange
        int hour = 10;
        int minutes = 59;

        onView(withId(R.id.textInputGlobalMuteName))
                .perform(typeText(mStringToBetyped), ViewActions.closeSoftKeyboard());
        //onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        // onView(withId(R.id.editTextTimeBetween))
        //       .perform(typeText("12:01"));
        onView(withId(R.id.editTextComment))
                .perform(typeText("yo"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonCancel)).perform(click());


        // act

        List<GlobalMute> dbGlobalMute = this.mGlobalMuteDao.getAll();
        String test = "test";

        // assert
        assertEquals(dbGlobalMute.get(0).getName(), test);
    }
}
