package org.saltations.persons.repo;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.convert.ConversionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.saltations.domain.EntitySearchSpecProvider;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;
import org.saltations.persons.PersonEntity;

import java.util.Map;

@Singleton
@Introspected
public class PersonSearchSpecProvider extends EntitySearchSpecProvider<PersonEntity, PersonSearchSpec>
{
    @Inject
    public PersonSearchSpecProvider(ConversionService<?> conversionService) {
        super(PersonEntity.class, conversionService);
    }

    @Override
    public PersonSearchSpec supply(Map<String,String> queryCriteria) throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        return new PersonSearchSpec(propertiesByName, conversionService, queryCriteria);
    }
}
