package com.can.zupuserservice.core.security.validation.concrete;

import com.can.zupuserservice.core.data.dto.PasswordConstraints;
import com.can.zupuserservice.core.data.dto.PasswordValidationResult;
import com.can.zupuserservice.core.data.enums.PasswordConstraintStatus;
import com.can.zupuserservice.core.security.validation.abstracts.IPasswordValidator;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class PasswordValidator implements IPasswordValidator {

    @Builder.Default
    private final int minLength = 8;

    @Builder.Default
    private final int maxLength = 30;

    @Builder.Default
    private final int minLowerCaseLetterCount = 1;

    @Builder.Default
    private final int minUpperCaseLetterCount = 1;

    @Builder.Default
    private final int minDigitCount = 1;

    @Builder.Default
    private final int minSpecialCharCount = 1;

    @Builder.Default
    private final Boolean checkForBadChars = true;


    private final List<Integer> specialCharsAscii = List.of(
            33, 40, 41, 42, 43, 44, 45, 46,
            58, 59, 60, 61, 62, 63
    );
    private final List<Integer> badCharsAscii = List.of(
            32, 34, 35, 36, 37, 38, 39, 47,
            64,
            91, 92, 93, 94, 95, 96,
            123, 124, 125, 126
    );

    public PasswordValidationResult validate(String password) {
        PasswordConstraints passwordConstraints = new PasswordConstraints();
        boolean status = true;

        if (Objects.isNull(password)) {
            passwordConstraints.setIsNull(PasswordConstraintStatus.FAIL);
            status = false;
            return new PasswordValidationResult(status, passwordConstraints);
        }

        if (password.length() < this.minLength) {
            passwordConstraints.setMinLength(PasswordConstraintStatus.FAIL);
            status = false;
        }
        if (this.maxLength != -1 && password.length() > this.maxLength) {
            passwordConstraints.setMaxLength(PasswordConstraintStatus.FAIL);
            status = false;
        }

        TokenOccurrences tokenOccurrences = countTokenOccurrences(password);
        if (tokenOccurrences.upperCaseLetters() < this.minUpperCaseLetterCount) {
            passwordConstraints.setMinUpperCaseLetterCount(PasswordConstraintStatus.FAIL);
            status = false;
        }
        if (tokenOccurrences.lowerCaseLetters() < this.minLowerCaseLetterCount) {
            passwordConstraints.setMinLowerCaseLetterCount(PasswordConstraintStatus.FAIL);
            status = false;
        }
        if (tokenOccurrences.digits() < minDigitCount) {
            passwordConstraints.setMinDigitCount(PasswordConstraintStatus.FAIL);
            status = false;
        }
        if (tokenOccurrences.specialCharacters() < minSpecialCharCount) {
            passwordConstraints.setMinSpecialCharCount(PasswordConstraintStatus.FAIL);
            status = false;
        }
        if (checkForBadChars && tokenOccurrences.badChars() >= 1) {
            passwordConstraints.setBadChars(PasswordConstraintStatus.FAIL);
            status = false;
        }

        return new PasswordValidationResult(status, passwordConstraints);
    }

    private TokenOccurrences countTokenOccurrences(String password) {
        int upperCaseLetters = 0;
        int lowerCaseLetters = 0;
        int digits = 0;
        int specialCharacters = 0;
        int badChars = 0;
        for (int i = 0; i < password.length(); i++) {
            int charAscii = password.charAt(i);
            if (charAscii >= 65 && charAscii <= 90) {
                upperCaseLetters++;
            }
            if (charAscii >= 97 && charAscii <= 122) {
                lowerCaseLetters++;
            }
            if (charAscii >= 48 && charAscii <= 57) {
                digits++;
            }
            if (specialCharsAscii.contains(charAscii)) {
                specialCharacters++;
            }
            if (charAscii <= 32 || charAscii >= 127 || badCharsAscii.contains(charAscii)) {
                badChars++;
            }
        }
        return new TokenOccurrences(upperCaseLetters, lowerCaseLetters, digits, specialCharacters, badChars);
    }

    private record TokenOccurrences(int upperCaseLetters, int lowerCaseLetters, int digits, int specialCharacters, int badChars) { }

}
