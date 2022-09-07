package org.saltations.persons;

import org.mapstruct.Mapper;
import org.saltations.domain.EntityMapper;

@Mapper(componentModel = "jsr330")
public interface PersonMapper extends EntityMapper<Long, PersonCore, PersonEntity>
{
}
