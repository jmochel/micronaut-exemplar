package org.saltations.domain.error;

import io.micronaut.http.HttpStatus;
import io.micronaut.problem.HttpStatusType;

import java.text.MessageFormat;

/**
 * Denotes the failure to provide the right number of criteria for a given operator
 */

public class UnknownSearchOperator extends DomainException {

    private static final String TITLE = "Search operator for {0} is unknown";

    /**
     * Constructor
     *
     * @param resourceTypeName The name of the resource type
     * @param propertyName The name of the property the criteria are aimed at
     * @param operatorAbbrev The abbreviation for the operator 'eq' , 'lt', etc.
     */

    public UnknownSearchOperator(String resourceTypeName, String propertyName, String operatorAbbrev)
    {
        super(MessageFormat.format(TITLE, resourceTypeName), "Operator {0} for property {1} is unknown", operatorAbbrev, propertyName);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }

    /**
     * Constructor
     *
     * @param e The exception that caused this exception to be thrown
     * @param resourceTypeName The name of the resource type
     * @param propertyName The name of the property the criteria are aimed at
     * @param operatorAbbrev The abbreviation for the operator 'eq' , 'lt', etc.
     */

    public UnknownSearchOperator(Throwable e, String resourceTypeName, String propertyName, String operatorAbbrev)
    {
        super(e, MessageFormat.format(TITLE, resourceTypeName),"Operator {0} for property {1} is unknown", operatorAbbrev, propertyName);
        setStatusType(new HttpStatusType(HttpStatus.BAD_REQUEST));
    }
}
