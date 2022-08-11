package org.saltations;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.saltations.persons.Person;

@MicronautTest
class PersonTest
{
    @Test
    void can()
    {
        var person = Person.of()
                .firstName("Martin")
                .lastName("Luther")
                .emailAddress("marting@thesynod.com")
                .build();

        person.firstName();
    }

}
