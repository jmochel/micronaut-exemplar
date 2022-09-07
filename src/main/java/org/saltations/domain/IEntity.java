package org.saltations.domain;

import java.net.InterfaceAddress;

/**
 * Interface representing identifier attribute of the entity
 *
 * @param <ID> Type of the identifier
 */
public interface IEntity<ID>
{
    Long getId();

    void setId(Long id);
}
