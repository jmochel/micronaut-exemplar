package org.saltations;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class MicronautExemplarTest
{
    @Inject
    EmbeddedApplication<?> application;

    @Test
    void appIsRunning()
    {
        Assertions.assertTrue(application.isRunning());
    }

}
