package org.saltations.persons;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.saltations.domain.error.EntityNotCreated;
import org.saltations.domain.error.EntityNotDeleted;
import org.saltations.domain.error.EntityNotUpdated;
import org.saltations.persons.service.PersonService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Person Service")
public class PersonServiceTest
{
    private final PersonOracle oracle;

    private final PersonMapper modelMapper;

    private final PersonService service;

    @Inject
    public PersonServiceTest(PersonOracle oracle, PersonMapper modelMapper, PersonService service) {
        this.oracle = oracle;
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @Test
    @Order(2)
    @DisplayName("Can insert, read, update, and delete")
    void canCreateReadUpdateAndDelete() throws EntityNotCreated, EntityNotUpdated, EntityNotDeleted {
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
