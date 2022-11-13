package com.can.zupuserservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class MessageUtils {

    private final MessageSource messageSource;
    private final HeaderUtils headerUtils;

    @Autowired
    MessageUtils(MessageSource messageSource, HeaderUtils headerUtils) {
        this.messageSource = messageSource;
        this.headerUtils = headerUtils;
    }

    public String getMessage(Locale loc, String key) {
        return messageSource.getMessage(key, null, loc);
    }

    /*
     * Returns message for the language on the header.
     * If not language presents in the header uses default language specified in the application.properties.
     */
    public String getMessage(String key) {
        Locale loc = headerUtils.getLanguageWithDefaultFallback();
        return getMessage(loc, key);
    }

    public String getMessage(Locale loc, String key, Object... args) {
        MessageFormat form = new MessageFormat(getMessage(loc, key));
        return form.format(args);
    }

    /*
     * Returns message and replaces arguments for the language on the header.
     */
    public String getMessage(String key, Object... args) {
        MessageFormat form = new MessageFormat(getMessage(key));
        return form.format(args);
    }

}
