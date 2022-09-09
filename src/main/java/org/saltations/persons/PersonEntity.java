package org.saltations.persons;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.saltations.domain.IEntity;
import org.saltations.domain.StdEntity;

/**
 * Represents the core attributes describing a person AND the information to track its persistence.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "of")
@StdEntity
@Schema(name = "person_entity",
        description = "Represents the core attributes describing a person AND the information to track its persistence",
        allOf = {PersonCore.class})
@MappedEntity("person")
public final class PersonEntity extends PersonCore implements IEntity<Long>, IPerson
{
    @Id
    @GeneratedValue
    private Long id;

}
