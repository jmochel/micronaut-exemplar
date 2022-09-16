package org.saltations.domain;

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.convert.ConversionService;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generic service for providing search specs for building queries for the given entity type.
 *
 * @param <E> Class of the persistable business item entity. Contains all the same data as <C> but supports additional entity specific meta-data.
 * @param <S> Class of the search spec
 */

public abstract class EntitySearchSpecProvider<E,S extends EntitySearchSpec<E>>
{
    protected final Class<E> entityClass;

    protected final ConversionService<?> conversionService;

    protected Map<String, BeanProperty<E, Object>> propertiesByName;

    public EntitySearchSpecProvider(Class<E> entityClass, ConversionService<?> conversionService) {
        this.entityClass = entityClass;
        this.conversionService = conversionService;
        propertiesByName = BeanIntrospection.getIntrospection(entityClass).getBeanProperties().stream()
                .collect(Collectors.toMap(bp -> bp.getName(), bp -> bp));
    }

    public abstract S supply(Map<String,String> queryCriteria) throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator;

}
