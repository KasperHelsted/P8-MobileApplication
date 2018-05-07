package p8project.sw801.TimeBasedNotifications;


import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityReceiver;
import p8project.sw801.utils.TimeBasedNotifications.AlarmReceiver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
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