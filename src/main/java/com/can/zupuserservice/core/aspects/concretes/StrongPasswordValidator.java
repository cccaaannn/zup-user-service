package com.can.zupuserservice.core.aspects.concretes;

import com.can.zupuserservice.core.data.enums.PasswordConstraintStatus;
import com.can.zupuserservice.core.data.dto.PasswordConstraints;
import com.can.zupuserservice.core.data.dto.PasswordValidationResult;
import com.can.zupuserservice.core.security.validation.abstracts.IPasswordValidator;
import com.can.zupuserservice.core.security.validation.concrete.PasswordValidator;
import com.can.zupuserservice.core.aspects.annotations.StrongPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private IPasswordValidator passwordValidator;
    private Boolean detailedMessages = false;

    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        detailedMessages = constraintAnnotation.detailedMessages();
        passwordValidator = PasswordValidator.builder()
                .minLength(constraintAnnotation.minLength())
                .maxLength(constraintAnnotation.maxLength())
                .minLowerCaseLetterCount(constraintAnnotation.minLowerCaseLetterCount())
                .minUpperCaseLetterCount(constraintAnnotation.minUpperCaseLetterCount())
                .minDigitCount(constraintAnnotation.minDigitCount())
                .minSpecialCharCount(constraintAnnotation.minSpecialCharCount())
                .checkForBadChars(constraintAnnotation.checkForBadChars())
                .build();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidationResult validationResult = passwordValidator.validate(password);

        if (detailedMessages) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(passwordConstraintToString(validationResult.getPasswordConstraints()))
                    .addConstraintViolation();
        }

        return validationResult.getStatus();
    }

    private String passwordConstraintToString(PasswordConstraints passwordConstraints) {
        StringBuilder stringBuilder = new StringBuilder();

        if (passwordConstraints.getIsNull() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Null is not allowed.");
        }
        if (passwordConstraints.getMaxLength() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed max length: ").append(passwordValidator.getMaxLength());
        }
        if (passwordConstraints.getMinLength() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed min length: ").append(passwordValidator.getMinLength());
        }
        if (passwordConstraints.getMinLowerCaseLetterCount() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed min lowercase letter count: ").append(passwordValidator.getMinLowerCaseLetterCount());
        }
        if (passwordConstraints.getMinUpperCaseLetterCount() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed min uppercase letter count: ").append(passwordValidator.getMinUpperCaseLetterCount());
        }
        if (passwordConstraints.getMinDigitCount() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed min digit count: ").append(passwordValidator.getMinDigitCount());
        }
        if (passwordConstraints.getMinSpecialCharCount() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("Allowed min special character count: ").append(passwordValidator.getMinSpecialCharCount());
        }
        if (passwordConstraints.getBadChars() == PasswordConstraintStatus.FAIL) {
            if(stringBuilder.length() != 0) stringBuilder.append(", ");
            stringBuilder.append("There are not allowed characters.");
        }

        return stringBuilder.toString();
    }

}
