package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

/**
 * Denotes the failure to delete an entity of a given type and id
 */

public class CannotDeleteEntity extends DomainException
{
    private static final String TITLE = "Cannot delete {0}";

    public CannotDeleteEntity(String resourceTypeName, Object id)
    {
        super(MessageFormat.format(TITLE, resourceTypeName),"Cannot delete {0} with id {1}", resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }

    public CannotDeleteEntity(Throwable e, String resourceTypeName, Object id)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName),"Cannot delete {0} with id {1}", resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }



}
