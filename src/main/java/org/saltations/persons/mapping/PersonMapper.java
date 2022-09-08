package org.saltations.persons.mapping;

import org.mapstruct.Mapper;
import org.saltations.domain.EntityMapper;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;

@Mapper(componentModel = "jsr330")
public interface PersonMapper extends EntityMapper<Long, PersonCore, PersonEntity>
{
}
