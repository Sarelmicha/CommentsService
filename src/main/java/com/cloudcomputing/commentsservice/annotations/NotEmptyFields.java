package com.cloudcomputing.commentsservice.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyFieldsValidator.class)
public @interface NotEmptyFields {

    String message() default "List cannot contain empty fields";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}