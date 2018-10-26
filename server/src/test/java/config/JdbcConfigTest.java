package config;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class JdbcConfigTest extends BaseTest {

    @Autowired
    ApplicationContext _ctx;

    @Test
    public void test_() {
        // Arrange

        // Act
        DataSource result = (DataSource) _ctx.getBean("dataSource");

        // Assert
        assertThat(result).isNotNull();
    }
}
