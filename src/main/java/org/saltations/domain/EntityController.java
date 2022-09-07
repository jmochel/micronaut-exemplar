package org.saltations.domain;

// EntityService<ID,IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>>

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.constraints.NotNull;
import java.util.Optional;


/**
 * Generic controller for processing entities; An object uniquely identified by a single identity attribute
 *
 * @param <ID> Type of the unique identifier for the entity <E>.
 * @param <IC> Core interface that describes the business item
 * @param <C> Class of the business item
 * @param <E> Class of the persistable business item entity. Contains all the same data as <C> but supports additional
 *           entity specific meta-data.
 * @param <R> Type of the entity repository used by the service
 * @param <ES> Type of the entity service
 */

public class EntityController<ID,IC, C extends IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>, M extends EntityMapper<ID,C,E>, ES extends EntityService<ID,IC,C,E,R,M>>
{
    private final Class<E> clazz;
    protected final ES service;

    public EntityController(Class<E> clazz, ES service)
    {
        this.clazz = clazz;
        this.service = service;
    }

    protected String entityName()
    {
        return clazz.getSimpleName();
    }

    @Operation(summary = "Gets a person", description = "A person's contact is returned")
    @ApiResponse(responseCode = "400", description = "Invalid Name Supplied")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @Get(uri="/{id}")
    public Optional<E> getById(@NotNull @PathVariable ID id)
    {
        return this.service.findById(id);
    }


}
