package p8project.sw801.UnitTests.ViewModelAndUtilities.TimeBasedNotifications;


import android.content.Context;
import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import p8project.sw801.utils.TimeBasedNotifications.AlarmReceiver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class AlarmReceiverTest {

    @Mock
    private Context mockContext;


    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void onReceive() {
        //Arrange
        Intent i = new Intent();
        mockContext = mock(Context.class);
        AlarmReceiver alarmReceiver = mock(AlarmReceiver.class);
        //Act
        alarmReceiver.onReceive(mockContext, i);
        //Assert
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(arg0, mockContext);
            assertEquals(arg1, i);

            return null;
        }).when(alarmReceiver).onReceive(any(Context.class), any(Intent.class));
    }
}