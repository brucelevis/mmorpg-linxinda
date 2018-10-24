package com.wan37.handler;

import base.BaseTest;
import com.wan37.logic.player.handler.Player_Login;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class GeneralDipatchHandlerManagerTest extends BaseTest {

    @Autowired
    GeneralDipatchHandlerManager _sut;

    @Test
    public void test_() {
        // Arrange
        String req = "player_Login";

        // Act
        GeneralHandler result = _sut.get(req);

        // Assert
        assertThat(result.getClass()).isEqualTo(Player_Login.class);
    }
}