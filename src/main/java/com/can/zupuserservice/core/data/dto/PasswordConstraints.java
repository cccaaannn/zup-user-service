package com.can.zupuserservice.core.data.dto;

import com.can.zupuserservice.core.data.enums.PasswordConstraintStatus;
import lombok.Data;

@Data
public class PasswordConstraints {
    private PasswordConstraintStatus minLength = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus maxLength = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus minLowerCaseLetterCount = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus minUpperCaseLetterCount = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus minDigitCount = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus minSpecialCharCount = PasswordConstraintStatus.PASS;
    private PasswordConstraintStatus badChars = PasswordConstraintStatus.NOT_CHECKED;
    private PasswordConstraintStatus isNull = PasswordConstraintStatus.PASS;
}
