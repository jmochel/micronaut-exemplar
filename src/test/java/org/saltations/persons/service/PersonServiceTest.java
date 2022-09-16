package org.saltations.persons.service;

import jakarta.inject.Inject;
import org.apache.groovy.util.Maps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.saltations.DBContainerTestBase;
import org.saltations.domain.error.CannotCreateEntity;
import org.saltations.domain.error.CannotDeleteEntity;
import org.saltations.domain.error.CannotFindEntity;
import org.saltations.domain.error.CannotUpdateEntity;
import org.saltations.domain.error.DomainException;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonOracle;
import org.saltations.persons.mapping.PersonMapper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Person Service")
public class PersonServiceTest extends DBContainerTestBase
{
    @Inject
    private PersonOracle oracle;
    @Inject
    private PersonMapper modelMapper;
    @Inject
    private PersonService service;

    @BeforeAll
    void cleanDB()
    {
        service.deleteAll();
    }

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

    @ParameterizedTest(name="[{index}] => property name {0} with criteria [{1}]")
    @MethodSource("validQueryCriteriaProvider")
    @Order(4)
    @DisplayName("Can find by valid query criteria")
    void canFindByQueryCriteria(String propName, String  criteriaString, PersonCore expected) throws CannotCreateEntity, SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        // Save

        service.create(oracle.corePrototype());
        service.create(oracle.alteredCore());

        // Find by criteria

        var queryCriteria = Maps.of(propName, criteriaString);

        var found = service.find(queryCriteria);
        assertEquals(1, found.size());
        oracle.hasSameCoreContent(expected, found.get(0));
    }

    @ParameterizedTest(name="[{index}] => property name {0} with criteria [{1}]")
    @MethodSource("invalidQueryCriteriaProvider")
    @Order(6)
    @DisplayName("Can find by invalid query criteria")
    void canDetectInvalidQueryCriteria(String propName, String criteriaString, Class<? extends DomainException> expected) throws CannotCreateEntity, SearchOperatorRequiresDifferentNumberOfCriteria {

        // Find by criteria

        var queryCriteria = Maps.of(propName, criteriaString);

        assertThrows(expected, () -> service.find(queryCriteria));
    }

    Stream<Arguments> validQueryCriteriaProvider()
    {
        var core = oracle.corePrototype();

        return Stream.of(
               arguments("firstName","eq," + core.getFirstName(), core),
               arguments("lastName","eq," + core.getLastName(), core),
               arguments("emailAddress","eq," + core.getEmailAddress(), core)
        );
    }

    Stream<Arguments> invalidQueryCriteriaProvider()
    {
        var core = oracle.corePrototype();

        return Stream.of(
                arguments("firstName","eq", SearchOperatorRequiresDifferentNumberOfCriteria.class),
                arguments("firstName","eq,sam,reg", SearchOperatorRequiresDifferentNumberOfCriteria.class),
                arguments("firstName","bogus,sam,reg", UnknownSearchOperator.class)
        );
    }
}
