package com.can.zupuserservice.aspect.concrete;

import com.can.zupuserservice.service.abstracts.IUserService;
import com.can.zupuserservice.aspect.annotation.UniqueEmail;
import com.can.zupuserservice.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final IUserService userService;
    private final MessageUtils messageUtils;

    @Autowired
    public UniqueEmailValidator(IUserService userService, MessageUtils messageUtils) {
        this.userService = userService;
        this.messageUtils = messageUtils;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageUtils.getMessage("user.error.email-taken", email)).addConstraintViolation();

        return !userService.isExistsByEmail(email);
    }

}
