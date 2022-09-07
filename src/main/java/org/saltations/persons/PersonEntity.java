package org.saltations.persons;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.saltations.domain.IEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "of")
@Serdeable(naming = SnakeCaseStrategy.class)
@Schema(name = "person_entity", allOf = {PersonCore.class})
@MappedEntity("person")
public final class PersonEntity extends PersonCore implements IEntity<Long>, IPerson
{
    @Id
    @GeneratedValue
    private Long id;

}
