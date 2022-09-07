package org.saltations.domain;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Represents standard mapping between core business objects and entities
 *
 * @param <ID>
 * @param <C> Class of the business item
 * @param <E> Class of the persistable business item entity. Contains all the same data as <C> but supports additional
 *           entity specific meta-data.
 */

public interface EntityMapper<ID, C, E extends IEntity<ID>>
{
    @Mapping(target = "id", ignore = true)
    E createEntity(C source);

    C createCore(E source);

    E update(C source, @MappingTarget E destination);
}
