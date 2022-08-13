package org.saltations.persons;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.saltations.persons.repo.PersonRepo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Person Repo")
@MicronautTest
public class PersonRepoTest
{
    @Inject
    private PersonFixture fixture;

    @Inject
    private PersonRepo repo;

    @Test
    @DisplayName("Can insert, read, update, and delete")
    void canCreateReadUpdateAndDelete()
    {
        var saved = repo.save(fixture.protoEntity());

        assertNotNull(saved);
    }
}
