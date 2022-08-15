package org.saltations.persons.repo;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import org.saltations.EntityRepo;
import org.saltations.persons.PersonEntity;

@JdbcRepository(dialect = Dialect.MYSQL)
public abstract class PersonRepo extends EntityRepo<Long, PersonEntity>
{
}
