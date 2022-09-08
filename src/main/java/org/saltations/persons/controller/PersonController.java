package org.saltations.persons.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.saltations.domain.EntityController;
import org.saltations.domain.StdController;
import org.saltations.domain.error.ProblemSchema;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.repo.PersonRepo;
import org.saltations.persons.service.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@StdController
@Controller(value = "/persons")
@Tag(name="Persons", description = "People's names and contact info")
public class PersonController extends EntityController<Long, IPerson, PersonCore, PersonEntity, PersonRepo, PersonMapper, PersonService>
{
    @Inject
    public PersonController(PersonService service)
    {
        super(PersonEntity.class, service);
    }

    @Post
    @Operation(summary = "Create person", description = "Create the person from core data")
    @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = PersonEntity.class))
    )
    public HttpResponse<PersonEntity> create(@NotNull @Body PersonCore toBeCreated) {
        return super.createEntity(toBeCreated);
    }

    @Get(uri="/{id}")
    @Operation(summary = "Get person", description = "Get the person with the given id")
    @ApiResponse(responseCode = "404",
            description = "Not Found. Resource with that id does not exist",
            content = @Content(mediaType = "application/problem+json",schema = @Schema(implementation = ProblemSchema.class))
    )
    public HttpResponse<PersonEntity> get(@NotNull Long id) {
        return super.getEntity(id);
    }

    @Put(uri="/{id}")
    @Operation(summary = "Replace person", description = "Overwrite the person using values from the given resource")
    @ApiResponse(responseCode = "404",
            description = "Not Found. Resource with that id does not exist",
            content = @Content(mediaType = "application/problem+json",schema = @Schema(implementation = ProblemSchema.class))
    )
    public HttpResponse<PersonEntity> replace(@NotNull @PathVariable Long id, @NotNull @Body PersonEntity replacement) {
        return super.replaceEntity(id, replacement);
    }

    @Delete(uri="/{id}")
    @Operation(summary = "Delete person", description = "Delete the person with the given id")
    @ApiResponse(responseCode = "404",
            description = "Not Found. Resource with that id does not exist",
            content = @Content(mediaType = "application/problem+json",schema = @Schema(implementation = ProblemSchema.class))
    )
    public HttpResponse<?> delete(@NotNull @PathVariable Long id) {
        return super.deleteEntity(id);
    }
}
