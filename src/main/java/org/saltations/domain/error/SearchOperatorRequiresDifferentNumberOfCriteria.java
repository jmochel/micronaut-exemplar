package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

/**
 * Denotes the failure to provide the right number of criteria for a given operator
 */

public class SearchOperatorRequiresDifferentNumberOfCriteria extends DomainException {

    private static final String TITLE = "Search operator for {0} requires a different number of criteria.";

    /**
     * Constructor
     *
     * @param resourceTypeName The name of the resource type
     * @param propertyName The name of the property the criteria are aimed at
     * @param operatorAbbrev The abbreviation for the operator 'eq' , 'lt', etc.
     * @param requiredValueCount The number of values that should be coming after the operator
     * @param actualValueCount The actual number of values coming after the operator
     */

    public SearchOperatorRequiresDifferentNumberOfCriteria(String resourceTypeName, String propertyName, String operatorAbbrev, Integer requiredValueCount, Integer actualValueCount)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), "Operator {0} for property {1} requires {2} values, not {3}", operatorAbbrev, propertyName,requiredValueCount, actualValueCount);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }

    /**
     * Constructor
     *
     * @param e The exception that caused this exception to be thrown
     * @param resourceTypeName The name of the resource type
     * @param propertyName The name of the property the criteria are aimed at
     * @param operatorAbbrev The abbreviation for the operator 'eq' , 'lt', etc.
     * @param requiredValueCount The number of values that should be coming after the operator
     * @param actualValueCount The actual number of values coming after the operator
     */

    public SearchOperatorRequiresDifferentNumberOfCriteria(Throwable e, String resourceTypeName, String propertyName, String operatorAbbrev, Integer requiredValueCount, Integer actualValueCount)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName), "Operator {0} for property {1} requires {2} values, not {3}", operatorAbbrev, propertyName,requiredValueCount, actualValueCount);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }
}
