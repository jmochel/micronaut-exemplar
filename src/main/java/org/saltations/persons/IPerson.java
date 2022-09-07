package org.saltations.persons;

import io.swagger.v3.oas.annotations.media.Schema;
import org.saltations.domain.StdEmailAddress;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "person", description = "Represents a person's basic contact info")
public interface IPerson
{
    @Schema(description = "The first name of the person")
    String getFirstName();
    void setFirstName(@NotNull @NotBlank @Size(max = 100) String firstName);

    @Schema(description = "The last name of the person")
    String getLastName();

    void setLastName(@NotNull @NotBlank @Size(max = 100) String lastName);

    @Schema(description = "Email address", example = "jmochel@landschneckt.org")
    String getEmailAddress();
    void setEmailAddress(@NotNull @NotBlank @StdEmailAddress String emailAddress);

    
}
