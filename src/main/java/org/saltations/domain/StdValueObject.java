package org.saltations.domain;

import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Serdeable(naming = SnakeCaseStrategy.class)
//@AccessorsStyle(readPrefixes = "", writePrefixes = "")
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface StdValueObject
{
}
