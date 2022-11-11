package com.can.zupuserservice.core.security.validation.password.abstracts;

import com.can.zupuserservice.core.security.validation.password.data.dtos.PasswordValidationResult;
import com.can.zupuserservice.core.security.validation.password.data.enums.PasswordConstraint;

public interface IPasswordValidator {
    PasswordValidationResult validate(String password);

    int getMaxLength();

    int getMinLength();

    int getMinLowerCaseLetterCount();

    int getMinUpperCaseLetterCount();

    int getMinDigitCount();

    int getMinSpecialCharCount();

    Integer getConstraintByPasswordConstraint(PasswordConstraint passwordConstraint);
}
