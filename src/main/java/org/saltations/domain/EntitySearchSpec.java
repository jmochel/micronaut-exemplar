package org.saltations.domain;

import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.repository.jpa.criteria.QuerySpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.saltations.domain.error.SearchOperatorRequiresDifferentNumberOfCriteria;
import org.saltations.domain.error.UnknownSearchOperator;
import org.saltations.persons.PersonEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class EntitySearchSpec<E> implements QuerySpecification<E>
{
    private final Class<E> entityClass;

    private final Map<String, BeanProperty<E, Object>> propertiesByName;

    private final ConversionService<?> conversionService;

    private final Map<String,String> queryCriteria;

    public EntitySearchSpec(Class<E> entityClass, Map<String, BeanProperty<E, Object>> propertiesByName, ConversionService<?> conversionService, Map<String, String> queryCriteria) throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        this.entityClass = entityClass;
        this.propertiesByName = propertiesByName;
        this.conversionService = conversionService;
        this.queryCriteria = queryCriteria;

        validateSearchOperatorCriteria();
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate[] arrayTemplate = {};

        Predicate result;

        var predicates = queryCriteria.entrySet().stream()
                .map(e -> map(propertiesByName.get(e.getKey()),root, cb, e.getValue()))
                .collect(Collectors.toList());

        result = cb.and(predicates.toArray(arrayTemplate));

        return result;
    }


    protected void validateSearchOperatorCriteria() throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator
    {
        for (Map.Entry<String,String> entry : queryCriteria.entrySet())
        {
            validate(entry.getKey(), entry.getValue());
        }
    }

    private void validate(String key, String compoundValue) throws SearchOperatorRequiresDifferentNumberOfCriteria, UnknownSearchOperator {
        // If there are not enough values for the operator, signal an error

        var values = StringUtils.splitOmitEmptyStringsList(compoundValue, ',');
        var abbrev = values.get(0).toLowerCase();
        values.remove(0);

        switch (abbrev)
        {
            case "is_null":
                hasRequiredNumberOfCriteria(values, abbrev, 0, key);
                break;
            case "is_not_null":
                hasRequiredNumberOfCriteria(values, abbrev, 0, key);
                break;
            case "eq":
                hasRequiredNumberOfCriteria(values, abbrev, 1, key);
                break;
            default:
                throw new UnknownSearchOperator(entityClass.getSimpleName(), key, abbrev);
        }
    }

    private void hasRequiredNumberOfCriteria(List<String> values, String abbrev, Integer requiredValueCount, String propertyName) throws SearchOperatorRequiresDifferentNumberOfCriteria
    {
        if (requiredValueCount == values.size())
        {
            return;
        }

        throw new SearchOperatorRequiresDifferentNumberOfCriteria(entityClass.getSimpleName(), propertyName, abbrev, requiredValueCount, values.size());
    }



    Predicate map(BeanProperty<E, Object> prop, Root<E> root, CriteriaBuilder cb, String compoundValue)
    {
        var path = root.get(prop.getName());
        var values = StringUtils.splitOmitEmptyStringsList(compoundValue, ',');
        var abbrev = values.get(0).toLowerCase();

        values.remove(0);   // Remove the operator abbreviations

        switch (abbrev)
        {
            case "is_null":
                return cb.isNull(path);
            case "is_not_null":
                return cb.isNotNull(path);
            case "eq":
                return cb.equal(path, conversionService.convert(values.get(0), prop.getType()).get());
        }

        return null;
    }

}
