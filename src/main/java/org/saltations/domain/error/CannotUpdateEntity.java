package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

/**
 * Denotes the failure to update an entity for a given id
 */

public class CannotUpdateEntity extends DomainException {

    private static final String TITLE = "Cannot update {0}";

    public CannotUpdateEntity(String resourceTypeName, Object id)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), "Cannot update {0} with id {1}", resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }

    public CannotUpdateEntity(Throwable e, String resourceTypeName, Object id)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName), "Cannot update {0} with id {1}", resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }
}
