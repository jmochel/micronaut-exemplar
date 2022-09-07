package org.saltations.domain;

import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE})
@ExecuteOn(TaskExecutors.IO)
@ApiResponse(responseCode = "400",
        description = "Malformed request could not be understood by the server due to " +
                "malformed syntax. The client SHOULD NOT repeat the request without modifications."
//        ,
//        content = @Content(
//                mediaType = "application/problem+json",
//                schema = @Schema(implementation = ProblemSchema.class)
//        )
)
@ApiResponse(responseCode = "401",
        description = "Unauthorized. The request requires an authenticated user. " +
                "User is either unauthenticated or someone forgot to put an Authorization Header with a bearer access token " +
                "in it."
//        ,
//        content = @Content(
//                mediaType = "application/problem+json",
//                schema = @Schema(implementation = ProblemSchema.class)
//        )
)
@ApiResponse(responseCode = "403",
        description = "Forbidden. User is authenticated but does not have sufficient " +
                "permissions to perform the operation for this resource."
//        ,
//        content = @Content(
//                mediaType = "application/problem+json",
//                schema = @Schema(implementation = ProblemSchema.class)
//        )
)
@ApiResponse(responseCode = "500", description = "Some other error."
//        ,
//        content = @Content(
//                mediaType = "application/problem+json",
//                schema = @Schema(implementation = ProblemSchema.class)
//        )
)
public @interface StdController
{
}