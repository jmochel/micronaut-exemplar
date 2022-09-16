package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.saltations.domain.StdEmailAddress;
import org.saltations.domain.StdValueObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents the core attributes describing a person. Used to transfer data on a person from one context to another.
 * They should not have any behavior.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "of")
@StdValueObject
@Schema(name = "person", description = "Names and contact info")
public class PersonCore implements IPerson
{
    @Setter(onParam_={@NotNull,@NotBlank,@Size(max = 100)})
    private String firstName;

    @Setter(onParam_={@NotNull,@NotBlank,@Size(max = 100)})
    private String lastName;

    @Setter(onParam_={@NotNull,@NotBlank,@StdEmailAddress})
    private String emailAddress;
}
