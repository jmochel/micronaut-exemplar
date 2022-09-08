package org.saltations.persons.repo;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.saltations.DBContainerTestBase;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.PersonOracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Person Repo")
public class PersonRepoTest extends DBContainerTestBase
{
    @Inject
    private PersonOracle oracle;

    @Inject
    private PersonMapper modelMapper;

    @Inject
    private PersonRepo repo;

    @Test
    @Order(2)
    @DisplayName("Can insert, read, update, and delete")
    void canCreateReadUpdateAndDelete()
    {
        // Save

        var prototype = oracle.entityPrototype();
        var saved = repo.save(prototype);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        oracle.hasSameCoreContent(prototype, saved);

        // Read

        var retrieved = repo.findById(saved.getId()).orElseThrow();
        oracle.hasSameCoreContent(saved, retrieved);
        assertEquals(saved.getId(), retrieved.getId());

        // Update

        var alteredCore = oracle.alteredCore();
        var modified = modelMapper.update(alteredCore, retrieved);
        repo.update(modified);

        var updated = repo.findById(saved.getId()).orElseThrow();
        oracle.hasSameCoreContent(alteredCore, updated);

        // Delete

        repo.deleteById(saved.getId());
        var possible = repo.findById(saved.getId());
        assertFalse(possible.isPresent());
    }
}
