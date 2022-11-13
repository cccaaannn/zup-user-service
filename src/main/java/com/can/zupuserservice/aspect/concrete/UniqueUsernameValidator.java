package com.can.zupuserservice.aspect.concrete;

import com.can.zupuserservice.service.abstracts.IUserService;
import com.can.zupuserservice.aspect.annotation.UniqueUsername;
import com.can.zupuserservice.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final IUserService userService;
    private final MessageUtils messageUtils;

    @Autowired
    public UniqueUsernameValidator(IUserService userService, MessageUtils messageUtils) {
        this.userService = userService;
        this.messageUtils = messageUtils;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageUtils.getMessage("user.error.username-taken", username)).addConstraintViolation();

        return !userService.isExistsByUsername(username);
    }

}
