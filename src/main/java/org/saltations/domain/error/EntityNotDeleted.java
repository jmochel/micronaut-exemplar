package org.saltations.domain.error;

/**
 * Denotes the failure to delete an entity of a given type and id
 */

public class EntityNotDeleted extends FormattedCheckedError
{
    public EntityNotDeleted(String resourceTypeName, Object id)
    {
        super("Unable to delete a {0} with id {1}", resourceTypeName, id);
    }

    public EntityNotDeleted(Throwable e, String resourceTypeName, Object id)
    {
        super(e, "Unable to delete a {0} with id {1}", resourceTypeName, id);
    }
}
