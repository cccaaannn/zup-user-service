package com.kurtcan.zupuserservice.core.security.validation.password.aspects.concretes;

import com.kurtcan.zupuserservice.core.security.validation.password.aspects.annotations.StrongPassword;
import com.kurtcan.zupuserservice.core.security.validation.password.data.dtos.PasswordValidationResult;
import com.kurtcan.zupuserservice.core.security.validation.password.data.enums.PasswordConstraint;
import com.kurtcan.zupuserservice.core.security.validation.password.abstracts.IPasswordValidator;
import com.kurtcan.zupuserservice.core.security.validation.password.concrete.PasswordValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private IPasswordValidator passwordValidator;
    private Boolean usePropertyKeyOnMessages = false;
    private Boolean detailedDefaultMessages = false;
    private String defaultMessage = "";

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        detailedDefaultMessages = constraintAnnotation.detailedDefaultMessages();
        usePropertyKeyOnMessages = constraintAnnotation.usePropertyKeyOnMessages();
        defaultMessage = constraintAnnotation.message();

        passwordValidator = PasswordValidator.builder()
                .minLength(constraintAnnotation.minLength())
                .maxLength(constraintAnnotation.maxLength())
                .minLowerCaseLetterCount(constraintAnnotation.minLowerCaseLetterCount())
                .minUpperCaseLetterCount(constraintAnnotation.minUpperCaseLetterCount())
                .minDigitCount(constraintAnnotation.minDigitCount())
                .minSpecialCharCount(constraintAnnotation.minSpecialCharCount())
                .checkForBadChars(constraintAnnotation.checkForBadChars())
                .ignoreCase(constraintAnnotation.ignoreCase())
                .build();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidationResult validationResult = passwordValidator.validate(password);

        constraintValidatorContext.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
        StringBuilder constraintViolationString = new StringBuilder();

        if (detailedDefaultMessages && !validationResult.getStatus()) {
            for (PasswordConstraint passwordConstraint : validationResult.getPasswordConstraints()) {
                if (constraintViolationString.length() != 0) constraintViolationString.append(", ");

                if (usePropertyKeyOnMessages) {
                    hibernateConstraintValidatorContext.addExpressionVariable(passwordConstraint.getName(), passwordValidator.getConstraintByPasswordConstraint(passwordConstraint));
                    constraintViolationString.append(passwordConstraint.getPropertyKey());
                } else {
                    constraintViolationString.append(passwordConstraint.getDefaultMessage());
                    Integer constraintVal = passwordValidator.getConstraintByPasswordConstraint(passwordConstraint);
                    if (Objects.nonNull(constraintVal)) constraintViolationString.append(constraintVal);
                }
            }
        }
        else {
            if (usePropertyKeyOnMessages) {
                constraintViolationString.append("{validation.password.aspect.default.message}");
            }
            else {
                constraintViolationString.append(defaultMessage);
            }
        }

        hibernateConstraintValidatorContext
                    .buildConstraintViolationWithTemplate(constraintViolationString.toString())
                    .addConstraintViolation();

        return validationResult.getStatus();
    }

}
