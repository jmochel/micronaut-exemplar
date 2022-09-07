package org.saltations.persons;

import jakarta.inject.Singleton;
import org.saltations.domain.EntityOracle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Singleton
public class PersonOracle extends EntityOracle<IPerson, PersonCore, PersonEntity>
{
    public PersonOracle()
    {
        super(PersonCore.class, PersonEntity.class);
    }

    @Override
    public PersonCore corePrototype()
    {
        return PersonCore.of()
                .firstName("Lazarus")
                .lastName("Long")
                .emailAddress("lazlong@thehowardfamilies.com")
                .build();
    }

    @Override
    public PersonCore alteredCore() {
        return PersonCore.of()
                .firstName("Sanford")
                .lastName("Sandman")
                .emailAddress("ss@sandman.com")
                .build();
    }

    @Override
    public PersonEntity entityPrototype()
    {
        var value = corePrototype();

        return PersonEntity.of()
                .id(1L)
                .firstName(value.getFirstName())
                .lastName(value.getLastName())
                .emailAddress(value.getEmailAddress())
                .build();

    }

    @Override
    public void hasSameCoreContent(IPerson expected, IPerson actual)
    {
        assertAll(
                () -> assertEquals(expected.getFirstName(), actual.getFirstName(),"firstName"),
                () -> assertEquals(expected.getLastName(), actual.getLastName(),"lastName"),
                () -> assertEquals(expected.getEmailAddress(), actual.getEmailAddress(),"emailAddress")
        );
    }

}
