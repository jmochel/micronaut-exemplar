package org.saltations.domain.error;

import org.saltations.domain.IEntity;

/**
 * Denotes the failure to update an entity of a given type from a prototype
 */

public class EntityNotUpdated extends FormattedCheckedError
{
    public EntityNotUpdated(String resourceTypeName,Object id)
    {
        super("Unable to update a {0} with id {1}", resourceTypeName, id);
    }

    public EntityNotUpdated(Throwable e, String resourceTypeName, Object id)
    {
        super(e, "Unable to update a {0} with id {1}", resourceTypeName, id);
    }
}
