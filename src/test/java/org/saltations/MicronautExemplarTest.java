package org.saltations;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(application = ExemplarApp.class)
@DisplayName("Exemplar App")
class MicronautExemplarTest extends DBContainerTestBase
{
    @Inject
    private EmbeddedApplication<?> application;

    @Inject
    private AppClient client;

    @Test
    @Order(2)
    @DisplayName("is running")
    void isRunning()
    {
        assertTrue(application.isRunning());
    }

    @Test
    @Order(4)
    @DisplayName("is healthy")
    void isHealthy()
    {
        var healthCheck = client.getHealth().block();

        assertNotNull(healthCheck);
        assertTrue(healthCheck.contains("UP"));
    }

    @Client("/")
    @Header(name="Content-Type", value= MediaType.APPLICATION_JSON)
    @Header(name="Accept", value = MediaType.APPLICATION_JSON + ", application/problem+json")
    interface AppClient
    {
        @Get("/health")
        @SingleResult
        Mono<String> getHealth();
    }

}
