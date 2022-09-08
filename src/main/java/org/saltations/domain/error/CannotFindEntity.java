package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

/**
 * Denotes the failure to find an entity for a given id
 */

public class CannotFindEntity extends DomainException {

    private static final String TITLE = "Cannot find {0}";
    private static final String DETAIL = "Cannot update {0} with id {1}";

    public CannotFindEntity(String resourceTypeName, Object id)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), DETAIL, resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }

    public CannotFindEntity(Throwable e, String resourceTypeName, Object id)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName), DETAIL, resourceTypeName, id);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }
}
