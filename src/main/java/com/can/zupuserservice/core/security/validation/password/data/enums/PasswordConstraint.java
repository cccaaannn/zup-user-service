package com.can.zupuserservice.core.security.validation.password.data.enums;

public enum PasswordConstraint {
    MIN_LENGTH("MIN_LENGTH", "Allowed min length: ", "{validation.password.length.min}"),
    MAX_LENGTH("MAX_LENGTH", "Allowed max length: ", "{validation.password.length.max}"),
    MIN_LOWER_CASE_LETTER_COUNT("MIN_LOWER_CASE_LETTER_COUNT", "Allowed min lowercase letter count: ", "{validation.password.lowercase.min}"),
    MIN_UPPER_CASE_LETTER_COUNT("MIN_UPPER_CASE_LETTER_COUNT", "Allowed min uppercase letter count: ", "{validation.password.uppercase.min}"),
    MIN_DIGIT_COUNT("MIN_DIGIT_COUNT", "Allowed min digit count: ", "{validation.password.digit.min}"),
    MIN_SPECIAL_CHAR_COUNT("MIN_SPECIAL_CHAR_COUNT", "Allowed min special character count: ", "{validation.password.special.min}"),
    BAD_CHARS("BAD_CHARS", "There are not allowed characters.", "{validation.password.char.bad}"),
    IS_NULL("IS_NULL", "Null is not allowed.", "{validation.password.null}");

    private final String name;
    private final String defaultMessage;
    private final String propertyKey;

    PasswordConstraint(String name, String defaultMessage, String propertyKey) {
        this.name = name;
        this.defaultMessage = defaultMessage;
        this.propertyKey = propertyKey;
    }

    public String getName() {
        return name;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
