package p8project.sw801.ProximityBasedNotifications;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityBasedNotifications;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProximityBasedNotificationsTest {

    @Mock
    private Context mockContext;


    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void createProximityNotification() {
        //Arrange
        Coordinate coordinate = new Coordinate(11.35, 12.54);
        int requestCode = 1;
        EventWithData eventWithData = new EventWithData();

        mockContext = mock(Context.class);
        ProximityBasedNotifications proximityBasedNotifications = mock(ProximityBasedNotifications.class);
        //Act
        proximityBasedNotifications.createProximityNotification(coordinate, requestCode, eventWithData);
        //Assert
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            Object arg2 = invocation.getArgument(2);

            assertEquals(coordinate, arg0);
            assertEquals(requestCode, arg1);
            assertEquals(eventWithData, arg2);
            return null;
        }).when(proximityBasedNotifications).createProximityNotification(any(Coordinate.class), any(int.class), any(EventWithData.class));
    }

    @Test
    public void cancelProximity() {
        //Arrange
        int requestCode = 1;
        EventWithData eventWithData = new EventWithData();

        mockContext = mock(Context.class);
        ProximityBasedNotifications proximityBasedNotifications = mock(ProximityBasedNotifications.class);
        //Act
        proximityBasedNotifications.cancelProximity(1, eventWithData);
        //Assert
        doAnswer((Answer) invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(requestCode, arg0);
            assertEquals(eventWithData, arg1);

            return null;
        }).when(proximityBasedNotifications).cancelProximity(any(int.class), any(EventWithData.class));
    }
}

