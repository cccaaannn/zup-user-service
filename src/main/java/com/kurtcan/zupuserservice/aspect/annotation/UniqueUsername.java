package com.kurtcan.zupuserservice.aspect.annotation;

import com.kurtcan.zupuserservice.aspect.concrete.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username is taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
