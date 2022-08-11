package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.LowerCamelCaseStrategy;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.saltations.IMeta;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(fluent = true, chain = false)
@AllArgsConstructor
@NoArgsConstructor
@Serdeable(naming = SnakeCaseStrategy.class)
@Introspected
@SuperBuilder(builderMethodName = "of")
@AccessorsStyle(readPrefixes = "", writePrefixes = "")
@Schema(name = "person_entity", allOf = {Person.class})
public final class PersonEntity extends Person implements IMeta<Long>
{
    @Id
    private Long id;
}
