package com.can.zupuserservice.core.security.validation.password.concrete;

import com.can.zupuserservice.core.security.validation.password.abstracts.IPasswordValidator;
import com.can.zupuserservice.core.security.validation.password.data.dtos.PasswordValidationResult;
import com.can.zupuserservice.core.security.validation.password.data.enums.PasswordConstraint;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <h2>Custom PasswordValidator implementation.</h2>
 * <hr/><br/>
 * <b>ignoreCase() overrides minLowerCaseLetterCount() and minUpperCaseLetterCount()</b>
 * <br/><br/>
 * Use these property keys for custom messages.
 *
 * <ul>
 * <li>validation.password.length.min=Allowed min length: ${MIN_LENGTH}</li>
 * <li>validation.password.length.max=Allowed max length: ${MAX_LENGTH}</li>
 * <li>validation.password.lowercase.min=Allowed min lowercase letter count: ${MIN_LOWER_CASE_LETTER_COUNT}</li>
 * <li>validation.password.uppercase.min=Allowed min uppercase letter count: ${MIN_UPPER_CASE_LETTER_COUNT}</li>
 * <li>validation.password.digit.min=Allowed min digit count: ${MIN_DIGIT_COUNT}</li>
 * <li>validation.password.special.min=Allowed min special character count: ${MIN_SPECIAL_CHAR_COUNT}</li>
 * <li>validation.password.char.bad=There are not allowed characters.</li>
 * <li>validation.password.null=Null is not allowed.</li>
 * <li>validation.password.aspect.default.message=Password is not strong enough</li>
 * </ul>
 *
 * @author Can Kurt
 * @version 1.0
 * @since 2022-11-11
 */
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

    @Builder.Default
    private final Boolean ignoreCase = false;


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
        List<PasswordConstraint> passwordConstraints = new ArrayList<>();

        if (Objects.isNull(password)) {
            passwordConstraints.add(PasswordConstraint.IS_NULL);
            return new PasswordValidationResult(false, passwordConstraints);
        }

        if (password.length() < this.minLength) {
            passwordConstraints.add(PasswordConstraint.MIN_LENGTH);
        }
        if (this.maxLength != -1 && password.length() > this.maxLength) {
            passwordConstraints.add(PasswordConstraint.MAX_LENGTH);
        }

        TokenOccurrences tokenOccurrences = countTokenOccurrences(password);
        if (!this.ignoreCase && tokenOccurrences.upperCaseLetters() < this.minUpperCaseLetterCount) {
            passwordConstraints.add(PasswordConstraint.MIN_UPPER_CASE_LETTER_COUNT);
        }
        if (!this.ignoreCase && tokenOccurrences.lowerCaseLetters() < this.minLowerCaseLetterCount) {
            passwordConstraints.add(PasswordConstraint.MIN_LOWER_CASE_LETTER_COUNT);
        }
        if (tokenOccurrences.digits() < minDigitCount) {
            passwordConstraints.add(PasswordConstraint.MIN_DIGIT_COUNT);
        }
        if (tokenOccurrences.specialCharacters() < minSpecialCharCount) {
            passwordConstraints.add(PasswordConstraint.MIN_SPECIAL_CHAR_COUNT);
        }
        if (checkForBadChars && tokenOccurrences.badChars() >= 1) {
            passwordConstraints.add(PasswordConstraint.BAD_CHARS);
        }

        boolean status = passwordConstraints.isEmpty();

        return new PasswordValidationResult(status, passwordConstraints);
    }

    @Override
    public Integer getConstraintByPasswordConstraint(PasswordConstraint passwordConstraint) {
        if (PasswordConstraint.MIN_LENGTH.equals(passwordConstraint)) {
            return getMinLength();
        }
        if (PasswordConstraint.MAX_LENGTH.equals(passwordConstraint)) {
            return getMaxLength();
        }
        if (PasswordConstraint.MIN_LOWER_CASE_LETTER_COUNT.equals(passwordConstraint)) {
            return getMinLowerCaseLetterCount();
        }
        if (PasswordConstraint.MIN_UPPER_CASE_LETTER_COUNT.equals(passwordConstraint)) {
            return getMinUpperCaseLetterCount();
        }
        if (PasswordConstraint.MIN_DIGIT_COUNT.equals(passwordConstraint)) {
            return getMinDigitCount();
        }
        if (PasswordConstraint.MIN_SPECIAL_CHAR_COUNT.equals(passwordConstraint)) {
            return getMinSpecialCharCount();
        }
        return null;
    }


    /*
     * Calculates different character occurrences on single pass
     */
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

    private record TokenOccurrences(
            int upperCaseLetters,
            int lowerCaseLetters,
            int digits,
            int specialCharacters,
            int badChars
    ) {
    }

}
