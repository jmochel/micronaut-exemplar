package org.saltations.persons;

import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.saltations.IEntity;
import org.saltations.StdEntity;
import org.saltations.StdValueObject;

@Data
@Accessors(fluent = true, chain = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "of")
@StdEntity
@Schema(name = "person_entity", allOf = {Person.class})
public final class PersonEntity extends Person implements IEntity<Long>, IPerson
{
    @Id
    private Long id;
}
