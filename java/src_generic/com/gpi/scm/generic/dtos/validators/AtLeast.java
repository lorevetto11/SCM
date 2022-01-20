package com.gpi.scm.generic.dtos.validators;


import static java.lang.annotation.ElementType.*;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ TYPE, PARAMETER })
@Constraint(validatedBy = AtLeastValidator.class)

public @interface AtLeast {
	
    String message() default "Missing at least one association between " ;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] required();

    
}
