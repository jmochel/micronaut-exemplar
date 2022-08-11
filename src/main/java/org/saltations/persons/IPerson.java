package org.saltations.persons;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.saltations.StdEmailAddress;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "person", description = "Represents a person's basic contact info")
public interface IPerson
{
    @JsonProperty("first_name")
    String firstName();
    @JsonProperty("first_name")
    void firstName(@NotNull @NotBlank @Size(max = 100) String firstName);

    @JsonProperty("last_name")
    String lastName();
    @JsonProperty("last_name")
    void lastName(@NotNull @NotBlank @Size(max = 100) String lastName);

    @JsonProperty("email_address")
    String emailAddress();
    @JsonProperty("email_address")
    void emailAddress(@NotNull @NotBlank @StdEmailAddress String emailAddress);
}
