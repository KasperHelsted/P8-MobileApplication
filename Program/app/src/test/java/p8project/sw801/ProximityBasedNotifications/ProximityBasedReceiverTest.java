package p8project.sw801.ProximityBasedNotifications;

import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityReceiver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProximityBasedReceiverTest {

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
        ProximityReceiver proximityReceiver = mock(ProximityReceiver.class);
        //Act
        proximityReceiver.onReceive(mockContext, i);
        //Assert
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(arg0, mockContext);
            assertEquals(arg1, i);

            return null;
        }).when(proximityReceiver).onReceive(any(Context.class), any(Intent.class));
    }

    @Test
    public void cancelProximity() {
        //Arrange
        List<TriggerWithSmartDevice> triggerWithSmartDevices = new ArrayList<>();
        String name = "Testname";

        mockContext = mock(Context.class);
        ProximityReceiver proximityReceiver = mock(ProximityReceiver.class);
        //Act
        proximityReceiver.triggerFunction(triggerWithSmartDevices, name, mockContext);
        //Assert
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            Object arg2 = invocation.getArgument(2);

            assertEquals(triggerWithSmartDevices, arg0);
            assertEquals("Testname", arg1);
            assertEquals(mockContext,arg2);

            return null;
        }).when(proximityReceiver).triggerFunction(any(List.class), any(String.class), any(Context.class));
    }
}