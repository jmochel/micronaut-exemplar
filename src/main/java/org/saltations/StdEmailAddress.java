package org.saltations;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Email
@Size(max = 320)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface StdEmailAddress
{
}
