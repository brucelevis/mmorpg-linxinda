package com.wan37.behavior;

import base.BaseTest;
import com.wan37.logic.test.behavior.TestBehavior;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BehaviorManagerTest extends BaseTest {

    @Autowired
    BehaviorManager _sut;

    @Test
    public void get() {
        // Arrange

        // Act
        Behavior result = _sut.get(TestBehavior.class, 1);

        // Assert
        assertThat(result).isNotNull();
    }
}