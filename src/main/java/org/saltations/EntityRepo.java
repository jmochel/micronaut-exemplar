package org.saltations;

import io.micronaut.data.repository.CrudRepository;
import org.saltations.IEntity;

public abstract class EntityRepo<ID, E extends IEntity<ID>> implements CrudRepository<E, ID>
{
}
