package com.wan37.event;

import base.BaseTest;
import com.wan37.event.login.LoginEvent;
import com.wan37.logic.scene.listener.SceneOnLogin;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GenernalEventListenersManagerTest extends BaseTest {

    @Autowired
    GenernalEventListenersManager _sut;

    @Autowired
    SceneOnLogin sceneOnLogin;

    @Test
    public void fireEvent() {
        // Arrange
        LoginEvent event = new LoginEvent();
        GenernalEventListenersManager sut = Mockito.mock(GenernalEventListenersManager.class);
        SceneOnLogin mock = Mockito.mock(SceneOnLogin.class);
        Mockito.when(sut.).thenThrow(new RuntimeException("call"));

        // Act
        Mockito._sut.fireEvent(event);

        // Assert
        Mockito.verify(_a, times(1)).execute(event);
    }
}