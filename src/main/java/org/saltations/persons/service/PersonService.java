package org.saltations.persons.service;

import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.saltations.domain.EntityService;
import org.saltations.domain.JsonMergePatchProvider;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonCore;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.mapping.PersonMapper;
import org.saltations.persons.repo.PersonRepo;
import org.saltations.persons.repo.PersonSearchSpec;
import org.saltations.persons.repo.PersonSearchSpecProvider;

@Singleton
public class PersonService extends EntityService<Long, IPerson, PersonCore, PersonEntity, PersonSearchSpec, PersonRepo, PersonMapper,  PersonSearchSpecProvider>
{
    @Inject
    public PersonService(PersonRepo repo, PersonMapper mapper, PersonSearchSpecProvider searchSpecProvider, JsonMergePatchProvider patchProvider, JsonMapper jsonMapper)
    {
        super(PersonEntity.class, repo, mapper, searchSpecProvider, patchProvider, jsonMapper);
    }
}
