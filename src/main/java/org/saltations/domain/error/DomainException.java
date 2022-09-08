package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.Problem;

import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Basic internal error that can be mapped to RFC 7807 Problem (without HTTP status)
 */


@Getter
@Setter
@Serdeable
public class DomainException extends Exception
{
    private HttpStatusType statusType;
    private String title;
    private String detail;

    private Map<String,Object> properties = new HashMap<>();

    public DomainException(String title, String detailTemplate, Object...args)
    {
        super(title + ":" + MessageFormat.format(detailTemplate, args));
        this.title = title;
        this.detail = MessageFormat.format(detailTemplate, args);

        properties.put("trace-id", UUID.randomUUID().toString());
    }

    public DomainException(Throwable cause, String title, String detailTemplate, Object...args)
    {
        super(title + ":" + MessageFormat.format(detailTemplate, args), cause);
        this.title = title;
        this.detail = MessageFormat.format(detailTemplate, args);

        properties.put("trace-id", UUID.randomUUID().toString());
    }



}
