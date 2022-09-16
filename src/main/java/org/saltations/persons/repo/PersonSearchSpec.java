package org.saltations.persons.repo;

import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.convert.ConversionService;
import org.saltations.domain.EntitySearchSpec;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;
import org.saltations.persons.PersonEntity;

import java.util.Map;

public class PersonSearchSpec extends EntitySearchSpec<PersonEntity>
{
    public PersonSearchSpec(Map<String, BeanProperty<PersonEntity, Object>> propertiesByName, ConversionService<?> conversionService, Map<String, String> queryCriteria)
            throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        super(PersonEntity.class, propertiesByName, conversionService, queryCriteria);
    }

}
