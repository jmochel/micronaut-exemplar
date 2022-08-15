package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import org.saltations.StdEmailAddress;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "person", description = "Represents a person's basic contact info")
public interface IPerson
{
    @Schema(description = "The first name of the person")
    String firstName();
    void firstName(@NotNull @NotBlank @Size(max = 100) String firstName);

    @Schema(description = "The last name of the person")
    String lastName();

    void lastName(@NotNull @NotBlank @Size(max = 100) String lastName);

    @Schema(description = "Email address", example = "jmochel@landschneckt.org")
    String emailAddress();
    void emailAddress(@NotNull @NotBlank @StdEmailAddress String emailAddress);

    
}
