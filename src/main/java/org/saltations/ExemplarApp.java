package org.saltations;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;

@OpenAPIDefinition(
    info = @Info(
            title = "micronaut-exemplar",
            version = "1.0"
    )
)
public class ExemplarApp
{
    public static void main(String[] args) {
        Micronaut.run(ExemplarApp.class, args);
    }
}
