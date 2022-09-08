package org.saltations.domain;

import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Inherited
@Introspected
@Serdeable(naming = SnakeCaseStrategy.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StdEntity
{
}
