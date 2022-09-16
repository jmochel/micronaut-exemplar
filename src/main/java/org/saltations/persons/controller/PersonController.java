package org.saltations.persons.controller;

import io.micronaut.context.annotation.Prototype;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.RequestBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.saltations.domain.EntityController;
import org.saltations.domain.StdController;
import org.saltations.domain.error.ProblemSchema;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.repo.PersonRepo;
import org.saltations.persons.repo.PersonSearchSpec;
import org.saltations.persons.repo.PersonSearchSpecProvider;
import org.saltations.persons.service.PersonService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@StdController
@Controller(value = "/persons")
@Tag(name="Persons", description = "People's names and contact info")
public class PersonController extends EntityController<Long, IPerson, PersonCore, PersonEntity, PersonSearchSpec, PersonRepo, PersonMapper, PersonSearchSpecProvider, PersonService>
{
    private QuerySpecMapper specMapper = QuerySpecMapper.INSTANCE;

    private PersonRepo repo;


    @Inject
    public PersonController(PersonService service, PersonRepo repo)
    {
        super(PersonEntity.class, service);
        this.repo = repo;
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

    @Get(uri="/{?firstName,lastName,emailAddress}")
    @Operation(summary = "Find person(s)", description = "Find person(s) with the spec")
    @ApiResponse(responseCode = "404",
            description = "Not Found. Resource with that id does not exist",
            content = @Content(mediaType = "application/problem+json",schema = @Schema(implementation = ProblemSchema.class))
    )
    public HttpResponse<List<PersonEntity>> find(@RequestBean QuerySpec querySpec)
    {
        var queryCriteria = specMapper.extract(querySpec);

        return super.find(queryCriteria);
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

    @Data
    @Introspected
    static class QuerySpec
    {
        @Nullable
        @QueryValue
        private String firstName;

        @Nullable
        @QueryValue
        private String lastName;

        @Nullable
        @QueryValue
        private String emailAddress;
    }

    @Mapper
    static interface QuerySpecMapper
    {
        QuerySpecMapper INSTANCE = Mappers.getMapper( QuerySpecMapper.class );

        HashMap<String,String> extract(QuerySpec source);
    }
}
