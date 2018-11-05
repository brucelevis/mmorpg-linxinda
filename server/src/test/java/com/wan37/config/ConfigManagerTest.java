package com.wan37.config;

import base.BaseTest;
import com.wan37.config.entity.TestCfgExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FIXME: Junit的测试根路径是server，而main运行时根路径却是整个工程mmorpg
 */
//@Ignore
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