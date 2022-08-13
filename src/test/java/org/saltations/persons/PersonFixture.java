package org.saltations.persons;

import jakarta.inject.Singleton;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Singleton
public class PersonFixture extends EntityFixture<IPerson, Person, PersonEntity>
{
    @Override
    public Person protoValue()
    {
        return Person.of()
                .firstName("Lazarus")
                .lastName("Long")
                .emailAddress("lazlong@thehowardfamilies.com")
                .build();
    }

    @Override
    public PersonEntity protoEntity()
    {
        var value = protoValue();

        return PersonEntity.of()
                .id(1L)
                .firstName(value.firstName())
                .lastName(value.lastName())
                .emailAddress(value.emailAddress())
                .build();

    }

    @Override
    public void hasSameCoreContent(IPerson expected, IPerson actual)
    {
        assertAll(
                () -> assertEquals(expected.firstName(), actual.firstName(),"firstName"),
                () -> assertEquals(expected.lastName(), actual.lastName(),"lastName"),
                () -> assertEquals(expected.emailAddress(), actual.emailAddress(),"emailAddress")
        );
    }

}
