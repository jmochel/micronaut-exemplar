package org.saltations.domain.error;

/**
 * Denotes the failure to create an entity of a given type from a prototype
 */

public class EntityNotCreated extends FormattedCheckedError
{
    public EntityNotCreated(String resourceTypeName, Object prototype)
    {
        super("Unable to create a {0} with contents {1}", resourceTypeName, prototype.toString());
    }

    public EntityNotCreated(Throwable e, String resourceTypeName, Object prototype)
    {
        super(e, "Unable to create a {0} with contents {1}", resourceTypeName, prototype.toString());
    }
}
