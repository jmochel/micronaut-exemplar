package org.saltations;

// EntityService<ID,IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>>

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class EntityController<ID,IC,C extends IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>, ES extends EntityService<ID,IC,E,R>>
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
