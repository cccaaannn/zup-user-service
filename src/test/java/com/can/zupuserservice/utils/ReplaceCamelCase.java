package com.can.zupuserservice.utils;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Locale;

public class ReplaceCamelCase extends DisplayNameGenerator.Standard {
    public ReplaceCamelCase() {
    }

    public String generateDisplayNameForClass(Class<?> testClass) {
        return this.camelToTitleCase(super.generateDisplayNameForClass(testClass));
    }

    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return this.camelToTitleCase(super.generateDisplayNameForNestedClass(nestedClass));
    }

    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return this.camelToTitleCase(testMethod.getName());
    }

    private String camelToTitleCase(String name) {
        name = name.replaceAll("([A-Z])", " $1");
        name = name.replaceAll("([0-9]+)", " $1");
        name = Character.toUpperCase(name.toCharArray()[0]) + name.substring(1).toLowerCase(Locale.ENGLISH);
        return name;
    }
}