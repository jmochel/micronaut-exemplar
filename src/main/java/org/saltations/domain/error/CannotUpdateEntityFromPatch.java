package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

public class CannotUpdateEntityFromPatch extends DomainException {

    private static final String TITLE = "Cannot update {0} from patch";
    private static final String DETAIL = "Cannot update {0} with id {1} from patch {2}";

    public CannotUpdateEntityFromPatch(String resourceTypeName, Object id, JsonNode patch)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), DETAIL, resourceTypeName, id, patch);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }

    public CannotUpdateEntityFromPatch(Throwable e, String resourceTypeName, Object id, JsonNode patch)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName), DETAIL, resourceTypeName, id, patch);
        setStatusType(new HttpStatusType(HttpStatus.NOT_FOUND));
    }
}
