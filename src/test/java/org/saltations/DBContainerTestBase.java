package org.saltations;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;


@MicronautTest(application = ExemplarApp.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBContainerTestBase implements TestPropertyProvider
{
    private static MySQLContainer dbContainer;

    private static final Map<String,String> properties = new HashMap<>();

    static
    {
        dbContainer = new MySQLContainer().withUsername("admin").withPassword("password123");
        dbContainer.start();

        properties.put("datasources.default.url",dbContainer.getJdbcUrl());
        properties.put("datasources.default.username",dbContainer.getUsername());
        properties.put("datasources.default.password",dbContainer.getPassword());
    }

    @Override
    public Map<String, String> get() {
        return getProperties();
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }
}
