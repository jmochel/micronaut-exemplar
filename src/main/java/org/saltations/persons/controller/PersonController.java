package org.saltations.persons.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.saltations.domain.EntityController;
import org.saltations.domain.StdController;
import org.saltations.persons.Example;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.PersonMapper;
import org.saltations.persons.repo.PersonRepo;
import org.saltations.persons.service.PersonService;

import javax.validation.Valid;
import java.time.ZoneId;

@Slf4j
@StdController
@Controller(value = "/persons")
@Tag(name="persons", description = "People's contact info")
public abstract class PersonController extends EntityController<Long, IPerson, PersonCore, PersonEntity, PersonRepo, PersonMapper, PersonService>
{
    @Inject
    public PersonController(PersonService service)
    {
        super(PersonEntity.class, service);
    }

    @Operation(summary = "Creates a person", description = "Creates a person entity")
    @Post(uri="/")
    public PersonEntity create(@Valid PersonCore toBeCreated)
    {
        return PersonEntity.of()
                .firstName(toBeCreated.getFirstName())
                .lastName(toBeCreated.getLastName())
                .emailAddress(toBeCreated.getEmailAddress())
                .build();
    }

    @Operation(summary = "Get an example", description = "Gets the massive example")
    @Post(uri="/sample")
    public Example get(@Valid Example toBeCreated)
    {
        return Example.of()
                .zoneId(ZoneId.of("America/New_York"))
                .build();
    }
}
