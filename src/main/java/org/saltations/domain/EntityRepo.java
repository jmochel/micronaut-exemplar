package org.saltations.domain;

import io.micronaut.data.repository.CrudRepository;

/**
 * Represents a repository for entities
 *
 * @param <ID> Type of the entity identifier.
 * @param <E> Type of the entity
 */

public abstract class EntityRepo<ID, E extends IEntity<ID>> implements CrudRepository<E, ID>
{
}
