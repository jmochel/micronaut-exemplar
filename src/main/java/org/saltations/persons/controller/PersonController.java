package org.saltations.persons.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.saltations.EntityController;
import org.saltations.StdController;
import org.saltations.persons.IPerson;
import org.saltations.persons.Person;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.repo.PersonRepo;
import org.saltations.persons.service.PersonService;

import javax.validation.Valid;

@Slf4j
@StdController
@Controller(value = "/persons")
@Tag(name="persons", description = "People's contact info")
public abstract class PersonController extends EntityController<Long, IPerson, Person, PersonEntity, PersonRepo, PersonService>
{
    @Inject
    public PersonController(PersonService service)
    {
        super(PersonEntity.class, service);
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
