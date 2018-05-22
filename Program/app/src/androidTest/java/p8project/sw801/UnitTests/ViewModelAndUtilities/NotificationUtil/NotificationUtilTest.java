package p8project.sw801.UnitTests.ViewModelAndUtilities.NotificationUtil;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import p8project.sw801.utils.NotificationUtil;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class NotificationUtilTest {

    @Mock
    private Context mockContext;


    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void CreateNotification() {
        //Arrange
        String Title = "title";
        String Content = "content";
        mockContext = Mockito.mock(Context.class);
        NotificationUtil notificationUtil = mock(NotificationUtil.class);
        //Act
        notificationUtil.CreateNotification("title", "content");
        //Assert
        when(mockContext.getSystemService(Context.NOTIFICATION_SERVICE)).thenReturn(notificationUtil);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(Title, arg0);
            assertEquals(Content, arg1);
            return null;
        })
                .when(notificationUtil).CreateNotification(any(String.class), any(String.class));
    }
}
