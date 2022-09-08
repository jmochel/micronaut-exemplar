package org.saltations.persons.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.saltations.DBContainerTestBase;
import org.saltations.domain.error.CannotCreateEntity;
import org.saltations.domain.error.CannotDeleteEntity;
import org.saltations.domain.error.CannotFindEntity;
import org.saltations.domain.error.CannotUpdateEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.PersonOracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Person Service")
public class PersonServiceTest extends DBContainerTestBase
{
    @Inject
    private PersonOracle oracle;
    @Inject
    private PersonMapper modelMapper;
    @Inject
    private PersonService service;

    @Test
    @Order(2)
    @DisplayName("Can insert, read, update, and delete")
    void canCreateReadUpdateAndDelete() throws CannotCreateEntity, CannotUpdateEntity, CannotDeleteEntity, CannotFindEntity
    {
        // Save

        var prototype = oracle.corePrototype();
        var saved = service.create(prototype);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        oracle.hasSameCoreContent(prototype, saved);

        // Read

        var retrieved = service.findById(saved.getId()).orElseThrow();
        oracle.hasSameCoreContent(saved, retrieved);
        assertEquals(saved.getId(), retrieved.getId());

        // Update

        var alteredCore = oracle.alteredCore();
        var modified = modelMapper.update(alteredCore, retrieved);
        service.update(modified);

        var updated = service.findById(saved.getId()).orElseThrow();
        oracle.hasSameCoreContent(alteredCore, updated);

        // Delete

        service.delete(saved.getId());
        var possible = service.findById(saved.getId());
        assertFalse(possible.isPresent());
    }
}
