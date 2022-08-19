package com.can.zupuserservice.core.security.validation.abstracts;

import com.can.zupuserservice.core.data.dto.PasswordValidationResult;

public interface IPasswordValidator {
    PasswordValidationResult validate(String password);
    int getMaxLength();
    int getMinLength();
    int getMinLowerCaseLetterCount();
    int getMinUpperCaseLetterCount();
    int getMinDigitCount();
    int getMinSpecialCharCount();
}
