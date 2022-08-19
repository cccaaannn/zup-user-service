package com.can.zupuserservice.core.aspects.annotations;

import com.can.zupuserservice.core.aspects.concretes.StrongPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password is not strong enough";

    boolean detailedMessages() default true;
    int minLength() default 8;
    int maxLength() default 30;
    int minLowerCaseLetterCount() default 1;
    int minUpperCaseLetterCount() default 1;
    int minDigitCount() default 1;
    int minSpecialCharCount() default 1;
    boolean checkForBadChars() default true;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
