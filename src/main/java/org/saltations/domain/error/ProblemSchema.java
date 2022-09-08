package org.saltations.domain.error;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@Serdeable
@Introspected
@NoArgsConstructor
@Schema(name = "Problem", description = "Standard error reporting details corresponding to RFC 7807")
public class ProblemSchema
{
    @Nullable
    @Schema(name = "type",
            description = "An absolute URI that identifies the problem type.  " +
                    "When dereferenced,it SHOULD provide human-readable documentation for the problem type" +
                    " (e.g., using HTML)",
            defaultValue = "about:blank",
            example = "https://zalando.github.io/problem/constraint-violation"
    )
    private URI type;

    @Nullable
    @Schema(
            description = "A short, summary of the problem type. Written in english and readable for engineers " +
                    "(usually not suited for non technical stakeholders and not localized)",
            example = "Service Unavailable"
    )
    private String title;

    @Schema(
            description = "The HTTP status code generated by the origin server for this occurrence of the problem",
            minimum = "100", maximum = "600", exclusiveMaximum = true,
            example = "403"
    )
    private Integer status;

    @Nullable
    @Schema(
            description = "A human readable explanation specific to this occurrence of the problem",
            example = "Connection to database timed out"
    )
    private String detail;

    @Nullable
    @Schema(
            description = "Additional property with unique identifier for the instance of the problem." +
                "Logged by the server so someone can link the log to the REST Call",
            example = "9508f49c-2f80-11ed-a261-0242ac120002"
    )
    private UUID traceId;
}