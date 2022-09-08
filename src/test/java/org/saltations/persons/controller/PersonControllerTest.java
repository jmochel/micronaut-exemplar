package org.saltations.persons.controller;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.saltations.DBContainerTestBase;
import org.saltations.ExemplarApp;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.PersonOracle;
import org.saltations.persons.repo.PersonRepo;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Person Controller")
public class PersonControllerTest extends DBContainerTestBase
{
    private static final String RESOURCE = "persons";

    @Inject
    private PersonOracle oracle;

    @Inject
    private PersonRepo repo;

    @Inject
    @Client
    private PersonClient client;

    @BeforeAll
    void cleanDB()
    {
        repo.deleteAll();
    }

    @Test
    @Order(2)
    @DisplayName("Can create " + RESOURCE)
    void canCreate()
    {
        var prototype = oracle.corePrototype();
        var created = client.create(prototype).block();
        oracle.hasSameCoreContent(prototype, created);
    }

    @Test
    @Order(4)
    @DisplayName("Cannot create " + RESOURCE + " from invalid prototype")
    void cannotCreateInvalid()
    {
        var prototype = oracle.corePrototype();
        prototype.setFirstName(null);

        var e = Assertions.assertThrows(HttpClientResponseException.class, () -> client.create(prototype).block());
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
    }

    @Test
    @Order(6)
    @DisplayName("Can create and get and delete" + RESOURCE)
    void canCreateAndGetAndDelete()
    {
        var prototype = oracle.corePrototype();
        var created = client.create(prototype).block();

        var retrieved = client.get(created.getId()).block();
        oracle.hasSameCoreContent(created, retrieved);

        client.delete(created.getId());
        retrieved = client.get(created.getId()).block();
        assertNull(retrieved);
    }

    @Client("/" + RESOURCE)
    @Header(name="Content-Type", value= MediaType.APPLICATION_JSON)
    @Header(name="Accept", value= MediaType.APPLICATION_JSON + ", application/problem+json")
    interface PersonClient
    {
        @Post
        @SingleResult
        Mono<PersonEntity> create(@NotNull @Body PersonCore prototype);

        @Get("/{id}")
        @SingleResult
        Mono<PersonEntity> get(@NotNull Long id);

        @Delete("/{id}")
        @SingleResult
        void delete(@NotNull Long id);

    }

}
