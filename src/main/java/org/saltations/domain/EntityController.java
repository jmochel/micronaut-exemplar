package org.saltations.domain;

// EntityService<ID,IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>>

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.json.tree.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.saltations.domain.error.CannotFindEntity;
import org.saltations.domain.error.DomainException;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;


/**
 * Generic controller for processing entities
 * <p>
 * This generic controller interacts with core business objects and their interfaces as well as entities.
 * A core business object contains all the attributes needed to define a specific business concept without any metadata
 * dealing with persistence (Such as identifiers, versions, creation an update dates and so on). It typically has an
 * interface that is shared between the core business object and the entity.
 * <p>
 * An entity is an object It has the same interface as a core business object but is uniquely identifiable and can be
 * persisted. An entity may have optional additional metadata dealing with the entities lifecycle such as creation and
 * update timestamps as well as versioning.
 *
 * @param <ID> Type of the unique identifier for the entity <E>.
 * @param <IC> Core interface that describes the business item
 * @param <C> Class of the business item
 * @param <E> Class of the business item entity. Contains all the same data as <C> but supports additional
 *           entity specific meta-data.
 * @param <R> Type of the entity repository used by the service
 * @param <ES> Type of the entity service
 */

@Slf4j
public class EntityController<ID,IC, C extends IC,E extends IEntity<ID>, S extends EntitySearchSpec<E>, R extends EntityRepo<ID,E>, M extends EntityMapper<ID,C,E>, SP extends EntitySearchSpecProvider<E,S>, ES extends EntityService<ID,IC,C,E,S,R,M,SP>>
{
    private final Class<E> clazz;

    protected final ES service;

    public EntityController(Class<E> clazz, ES service)
    {
        this.clazz = clazz;
        this.service = service;
    }

    protected String entityTypeName()
    {
        return clazz.getSimpleName();
    }


    /**
     * Create an entity from a given core business object.
     *
     * @param toBeCreated Core business subject describing the entity to be created.
     *
     * @return Http response containing the persisted entity
     */

    public HttpResponse<E> createEntity(@NotNull @Valid @Body C toBeCreated)
    {
        E created;

        try
        {
            created = service.create(toBeCreated);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(created);
    }

    /**
     * Get the entity by id.
     * <p>
     * Throws a Zalando Problem if the entity cannot be found.
     *
     * @param id Unique identifier for the entity.
     *
     * @return Http response containing the persisted entity
     */

    public HttpResponse<E> getEntity(@NotNull ID id)
    {
        E found;

        try
        {
            found = this.service.findById(id).orElseThrow(() -> new CannotFindEntity(entityTypeName(), id));
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(found);
    }

    /**
     * Find all entities that match a map of query criteria
     * <p>
     * The criteria are in the form of property-name and string value that contains a comma delimited set of an operator
     * and criteria for that operator. Some examples:
     * <table border=1>
     *     <tr><td>Property Name</td><td>Operator and criteria</td></tr>
     *     <tr><td>firstName</td><td>is_null</td></tr>
     *     <tr><td>firstName</td><td>is_not_null</td></tr>
     *     <tr><td>firstName</td><td>eq,Lazarus</td></tr>
     *     <tr><td>lastUpdated</td><td>eq,2022-09-22T12:00:00</td></tr>
     *     <tr><td>lastUpdated</td><td>btn,2022-09-22T12:00:00,2022-09-24T12:00:00</td></tr>
     * </table>
     *
     * @return Http response containing A list of the retrieved entities. An empty list is returned if there are no
     * entities that match the criteria
     */

    public HttpResponse<List<E>> find(@NotNull Map<String,String> queryCriteria)
    {
        List<E> found;

        try
        {
            found = service.find(queryCriteria);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(found);
    }

    /**
     * Replace the entity with the given id with the supplied replacement
     *
     * @param id Unique identifier for the entity
     * @param replacement Replacement entity
     *
     * @return Http response containing the replaced entity.
     */

    public HttpResponse<E> replaceEntity(@NotNull ID id, @NotNull @Valid @Body E replacement)
    {
        E replaced;

        try
        {
            service.findById(id)
                    .orElseThrow(() -> new CannotFindEntity(entityTypeName(), id));

            replaced = service.replace(replacement);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(replaced);
    }

    /**
     * Modify the entity with the given id with the supplied patch
     *
     * @param id Unique identifier for the entity
     * @param patch
     *
     * @return Http response containing the modified entity.
     */

    public HttpResponse<E> modifyEntity(@NotNull ID id, @NotNull JsonNode patch)
    {
        E modified;

        try
        {
            modified = service.modify(id, patch);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(modified);
    }

    public HttpResponse<?> deleteEntity(@NotNull ID id)
    {
        try
        {
            service.findById(id)
                    .orElseThrow(() -> new CannotFindEntity(entityTypeName(), id));

            service.delete(id);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok();
    }

    public ThrowableProblem createThrowableProblem(@NotNull DomainException e)
    {
        var builder = Problem.builder()
                .withTitle(e.getTitle())
                .withStatus(e.getStatusType())
                .withDetail(e.getDetail());

        // Add the type

        builder.withType(createType(e));

        // Add the properties

        e.getProperties().entrySet().forEach( entry -> builder.with(entry.getKey(), entry.getValue()));

        return builder.build();
    }

    private URI createType(DomainException e)
    {
        return URI.create("https://localhost/probs/" + e.getClass().getSimpleName().replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2").replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase());
    }


}
