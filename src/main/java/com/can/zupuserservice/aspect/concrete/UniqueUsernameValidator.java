package com.can.zupuserservice.aspect.concrete;

import com.can.zupuserservice.service.abstracts.IUserService;
import com.can.zupuserservice.aspect.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final IUserService userService;

    @Autowired
    public UniqueUsernameValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isExistsByUsername(username);
    }

}
