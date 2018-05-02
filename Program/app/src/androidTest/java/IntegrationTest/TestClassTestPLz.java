package IntegrationTest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import p8project.sw801.R;
import p8project.sw801.data.local.dao.GlobalMuteDao;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestClassTestPLz {
    private GlobalMuteDao mGlobalMuteDao;
    private AppDatabase mDb;

    @Rule
    public ActivityTestRule<AddGlobalMuteSettingActivity> mActivityRule = new ActivityTestRule<>(
            AddGlobalMuteSettingActivity.class);

    @BeforeClass
    public static void beforeClass() {
        InstrumentationRegistry.getTargetContext().deleteDatabase("dat_database");
    }

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mGlobalMuteDao = mDb.globalMuteDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testSubmitGlobalMuteClickAddGlobalMute3() throws Exception {
        // arrange

        onView(withId(R.id.textInputGlobalMuteName))
                .perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextComment))
                .perform(typeText("yo"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonCancel)).perform(click());

        // act

        List<GlobalMute> dbGlobalMute = mDb.globalMuteDao().getAll();
        String test = "test";

        // assert
        assertEquals(dbGlobalMute.get(0).getName(), test);
    }
}
