package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.IdentityStrategy;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.saltations.StdEmailAddress;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static io.micronaut.serde.config.naming.PropertyNamingStrategy.SNAKE_CASE;

/**
 * Represents Person // TODO Document what Person represents
 * <p>
 * Responsible for   // TODO Document Person responsibilities, if any
 * <ol>
 *  <li></li>
 *  <li></li>
 *  <li></li>
 * </ol>
 * <p>
 * Collaborates with // TODO Document Person collaborators, if any
 */

@Accessors(fluent = true, chain = false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "of")
@Serdeable(naming = SnakeCaseStrategy.class)
@Introspected
@AccessorsStyle(readPrefixes = "", writePrefixes = "")
@Schema(name = "person", description = "Names and contact info")
public class Person implements IPerson
{
    @NotNull
    @NotBlank
    @Size(max = 100)
    @Schema(description = "The first name of the person")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @JsonProperty("last_name")
    @Schema(description = "The last name of the person")
    private String lastName;

    @NotNull
    @NotBlank
    @StdEmailAddress
    @JsonProperty("email_address")
    @Schema(description = "The email address of the person")
    private String emailAddress;

}
