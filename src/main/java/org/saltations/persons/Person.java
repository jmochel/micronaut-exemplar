package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.IdentityStrategy;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.saltations.StdEmailAddress;
import org.saltations.StdEntity;
import org.saltations.StdValueObject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static io.micronaut.serde.config.naming.PropertyNamingStrategy.SNAKE_CASE;

@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true, chain = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "of")
@StdValueObject
@Schema(name = "person", description = "Names and contact info")
public class Person implements IPerson
{
    @Setter(onParam_={@NotNull,@NotBlank,@Size(max = 100)})
    private String firstName;

    @Setter(onParam_={@NotNull,@NotBlank,@Size(max = 100)})
    private String lastName;

    @Setter(onParam_={@NotNull,@NotBlank,@StdEmailAddress})
    private String emailAddress;
}
