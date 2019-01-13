package com.wan37.config;

import base.BaseTest;
import com.wan37.logic.test.config.TestCfg;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FIXME: Junit的测试根路径是server，而main运行时根路径却是整个工程
 */
@Ignore
public class ConfigManagerTest extends BaseTest {

    @Autowired
    ConfigManager _sut;

    @Test
    public void loads() {
        // Arrange

        // Act
        List<TestCfg> result = _sut.loads(TestCfg.class);

        // Assert
        assertThat(result).isNotEmpty();
    }

    @Test
    public void load() {
        // Arrange

        // Act
        Optional<TestCfg> result = _sut.load(TestCfg.class, 1);

        // Assert
        assertThat(result.isPresent()).isTrue();
    }
}