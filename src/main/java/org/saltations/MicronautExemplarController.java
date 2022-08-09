package org.saltations;

import io.micronaut.http.annotation.*;

@Controller("/micronautExemplar")
public class MicronautExemplarController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}