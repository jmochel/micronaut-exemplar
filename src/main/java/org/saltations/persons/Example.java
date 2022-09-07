package org.saltations.persons;


import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;

@Getter
@Setter
@Builder(builderMethodName = "of")
@Serdeable(naming = SnakeCaseStrategy.class)
@Schema(name = "example", description = "An example class")
public class Example {

    @Schema(description = "The Olson/IANA zone id",  type = "string")
    private ZoneId zoneId;

}
