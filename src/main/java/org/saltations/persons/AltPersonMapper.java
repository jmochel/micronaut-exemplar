package org.saltations.persons;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "jsr330")
public interface AltPersonMapper
{

    @Mapping(target = "id", ignore = true)
    PersonEntity createEntity(PersonCore source);

    PersonCore createDTO(PersonEntity source);

    PersonEntity update(PersonCore source, @MappingTarget PersonEntity destination);
}
