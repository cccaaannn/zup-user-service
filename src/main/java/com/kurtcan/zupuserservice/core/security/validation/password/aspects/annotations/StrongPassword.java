package com.kurtcan.zupuserservice.core.security.validation.password.aspects.annotations;

import com.kurtcan.zupuserservice.core.security.validation.password.aspects.concretes.StrongPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <h2>Password validation annotation.</h2>
 * <hr/><br/>
 * Uses Custom <i>PasswordValidator</i> to validate passwords.
 * See <i>PasswordValidator</i> implementation for more detail.
 *
 * @author Can Kurt
 * @version 1.0
 * @since 2022-11-11
 */
@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password is not strong enough";

    boolean usePropertyKeyOnMessages() default false;

    boolean detailedDefaultMessages() default true;

    int minLength() default 8;

    int maxLength() default 30;

    int minLowerCaseLetterCount() default 1;

    int minUpperCaseLetterCount() default 1;

    int minDigitCount() default 1;

    int minSpecialCharCount() default 1;

    boolean checkForBadChars() default true;

    boolean ignoreCase() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
