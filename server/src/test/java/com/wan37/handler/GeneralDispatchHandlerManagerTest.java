package com.wan37.handler;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class GeneralDispatchHandlerManagerTest extends BaseTest {

    @Autowired
    GeneralDispatchHandlerManager _sut;

    @Test
    public void test_() {
        // Arrange
        String req = "playerLoginCmd";

        // Act
        Optional<GeneralHandler> result = _sut.get(req);

        // Assert
        assertThat(result).isNotEmpty();
    }
}