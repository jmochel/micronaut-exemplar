package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

public class CannotPatchUnknownProperty extends DomainException {

    private static final String TITLE = "Cannot find {0} property to patch";
    private static final String DETAIL = "Cannot find property {0} in resource {1} to patch";

    public CannotPatchUnknownProperty(String resourceTypeName, String name)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), DETAIL, resourceTypeName, name);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }

    public CannotPatchUnknownProperty(Throwable e, String resourceTypeName, String name)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName),DETAIL, resourceTypeName, name);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }
}
