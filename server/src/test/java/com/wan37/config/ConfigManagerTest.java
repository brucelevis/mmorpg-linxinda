package com.wan37.config;

import base.BaseTest;
import com.wan37.logic.test.config.TestCfgExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigManagerTest extends BaseTest {

    @Autowired
    ConfigManager _sut;

    @Test
    public void loads_() {
        // Arrange

        // Act
        List<TestCfgExcel> result = _sut.loads(TestCfgExcel.class);

        // Assert
        assertThat(result).isNotEmpty();
    }
}