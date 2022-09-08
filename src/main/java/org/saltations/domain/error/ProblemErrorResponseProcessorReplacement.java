package org.saltations.domain.error;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.problem.HttpStatusType;
import io.micronaut.problem.ProblemErrorResponseProcessor;
import io.micronaut.problem.conf.ProblemConfiguration;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.ThrowableProblem;

@Slf4j
@Singleton
@Replaces(ProblemErrorResponseProcessor.class)
public class ProblemErrorResponseProcessorReplacement extends ProblemErrorResponseProcessor
{
    @Inject
    public ProblemErrorResponseProcessorReplacement(ProblemConfiguration config) {
        super(config);
    }

    @Override
    public MutableHttpResponse<Problem> processResponse(ErrorContext errorContext, MutableHttpResponse<?> baseResponse) {

        var response =  super.processResponse(errorContext, baseResponse);

        if (errorContext.getRootCause().isPresent())
        {
            var cause = errorContext.getRootCause().get();

            log.error("Exception thrown on {}", errorContext.getRequest().toString(),cause);
        }

        return response;
    }

    @Override
    protected ThrowableProblem defaultProblem(ErrorContext errorContext, HttpStatus httpStatus)
    {
        ProblemBuilder problemBuilder = Problem.builder()
                .withStatus(new HttpStatusType(httpStatus));

        if (!errorContext.getErrors().isEmpty()) {

            var error = errorContext.getErrors().get(0);

            error.getTitle().ifPresent(title -> problemBuilder.withTitle(title));

            problemBuilder.withDetail(error.getMessage());

            error.getPath().ifPresent((path) -> problemBuilder.with("path", path));
        }

        return problemBuilder.build();
    }


}