package org.saltations.domain;

// EntityService<ID,IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>>

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
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

    protected String resourceName()
    {
        return clazz.getSimpleName();
    }


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

    public HttpResponse<E> getEntity(@NotNull ID id)
    {
        E found;

        try
        {
            found = this.service.findById(id).orElseThrow(() -> new CannotFindEntity(resourceName(), id));
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(found);
    }

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

    public HttpResponse<E> replaceEntity(@NotNull ID id, @NotNull @Valid @Body E replacement)
    {
        E replaced;

        try
        {
            service.findById(id)
                    .orElseThrow(() -> new CannotFindEntity(resourceName(), id));

            replaced = service.update(replacement);
        }
        catch (DomainException e)
        {
            throw createThrowableProblem(e);
        }

        return HttpResponse.ok(replaced);
    }

    public HttpResponse<?> deleteEntity(@NotNull ID id)
    {
        try
        {
            service.findById(id)
                    .orElseThrow(() -> new CannotFindEntity(resourceName(), id));

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
