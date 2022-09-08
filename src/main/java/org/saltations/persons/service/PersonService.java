package org.saltations.persons.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.saltations.domain.EntityService;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.repo.PersonRepo;

@Singleton
public class PersonService extends EntityService<Long, IPerson, PersonCore, PersonEntity, PersonRepo, PersonMapper>
{
    @Inject
    public PersonService(PersonRepo repo, PersonMapper mapper)
    {
        super(PersonEntity.class, repo, mapper);
    }
}
