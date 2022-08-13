package org.saltations.persons.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.saltations.persons.IPerson;
import org.saltations.persons.PersonEntity;
import org.saltations.persons.repo.PersonRepo;

@Singleton
public abstract class PersonService extends EntityService<Long, IPerson, PersonEntity, PersonRepo>
{
    @Inject
    public PersonService(PersonRepo repo) {
        super(repo, PersonEntity.class);
    }
}
