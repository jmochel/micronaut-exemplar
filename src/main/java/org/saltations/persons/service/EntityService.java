package org.saltations.persons.service;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import jakarta.inject.Singleton;
import org.saltations.IEntity;
import org.saltations.persons.repo.EntityRepo;

import javax.validation.constraints.NotNull;
import java.util.Optional;


public abstract class EntityService<ID,IC,E extends IEntity<ID>, R extends EntityRepo<ID,E>>
{
    private final R repo;
    private final Class<E> clazz;

    public EntityService(R repo, Class<E> clazz)
    {
        this.repo = repo;
        this.clazz = clazz;
    }

    protected String entityName()
    {
        return clazz.getSimpleName();
    }

    public Optional<E> findById(@NotNull ID id)
    {
        return repo.findById(id);
    }

}
