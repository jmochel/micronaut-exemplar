package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

import javax.validation.constraints.NotNull;
import java.net.URI;

@Singleton
public class ErrorMapper
{
    public ThrowableProblem create(@NotNull DomainException e)
    {
        var builder = Problem.builder()
                .withTitle(e.getTitle())
                .withStatus(e.getStatusType())
                .withDetail(e.getDetail());

        // Add the type

        builder.withType(createType(e));

        // Add the properties

        e.getProperties().entrySet().forEach( entry -> builder.with(entry.getKey(), entry.getValue()));

        return builder.build();
    }

    private URI createType(DomainException e)
    {
        return URI.create("https://localhost/probs/" + e.getClass().getSimpleName().replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2").replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase());
    }
}
