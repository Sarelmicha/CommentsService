package com.cloudcomputing.commentsservice.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// regular expression for at least one digit, no white spaces and at least 5 chars
@Pattern(regexp = "^(?=.*[0-9])(?=\\S+$).{5,}$")
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Password {
	String message() default "Invalid password, must contain at least one digit";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
