package org.saltations;

import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.saltations.persons.Person;
import org.saltations.persons.PersonEntity;

import javax.validation.Valid;

@Tag(name = "persons")
@Controller("/exemplar/persons")
public class ExemplarController
{
    @Operation(summary = "Gets a person",
            description = "A person's contact is returned"
    )
    @ApiResponse(responseCode = "400", description = "Invalid Name Supplied")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @Get(uri="/")
    public Person get()
    {
        return Person.of()
                .firstName("Lazarus")
                .lastName("Long")
                .emailAddress("lazlong@thehowardfamilies.com")
                .build();
    }

    @Operation(summary = "Creates a person", description = "Creates a person entity")
    @Post(uri="/")
    public PersonEntity create(@Valid Person toBeCreated)
    {
        return PersonEntity.of()
                .firstName(toBeCreated.firstName())
                .lastName(toBeCreated.lastName())
                .emailAddress(toBeCreated.emailAddress())
                .build();
    }
}