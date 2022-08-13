package org.saltations;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.saltations.persons.Person;

import java.io.IOException;

@Slf4j
@MicronautTest
class PersonTest
{
    @Inject
    ObjectMapper mapper;

    @Test
    void can() throws Exception
    {
        var person = Person.of()
                .firstName("Martin")
                .lastName("Luther")
                .emailAddress("martin@thesynod.com")
                .build();

        log.info("{}", mapper.writeValueAsString(person));

        person.firstName();
    }

}
