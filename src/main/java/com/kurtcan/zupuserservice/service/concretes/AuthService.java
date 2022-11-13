package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.core.data.enums.UserStatus;
import com.kurtcan.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.dto.auth.LoginDTO;
import com.kurtcan.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.kurtcan.zupuserservice.data.dto.auth.UserEmailDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;
import com.kurtcan.zupuserservice.data.entity.User;
import com.kurtcan.zupuserservice.data.enums.TokenType;
import com.kurtcan.zupuserservice.service.abstracts.IAuthService;
import com.kurtcan.zupuserservice.service.abstracts.IEmailUtilsService;
import com.kurtcan.zupuserservice.service.abstracts.IUserService;
import com.kurtcan.zupuserservice.util.HeaderUtils;
import com.kurtcan.zupuserservice.util.MessageUtils;
import com.kurtcan.zupuserservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final IPasswordEncryptor passwordEncryptor;
    private final IEmailUtilsService emailUtilsService;
    private final TokenUtils tokenUtils;
    private final HeaderUtils headerUtils;
    private final MessageUtils messageUtils;

    @Autowired
    public AuthService(IUserService userService, IPasswordEncryptor passwordEncryptor, IEmailUtilsService emailUtilsService, TokenUtils tokenUtils, HeaderUtils headerUtils, MessageUtils messageUtils) {
        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.emailUtilsService = emailUtilsService;
        this.tokenUtils = tokenUtils;
        this.headerUtils = headerUtils;
        this.messageUtils = messageUtils;
    }

    @Override
    public DataResult<JWTToken> login(LoginDTO loginDTO) {
        User user = userService.getByUsernameInternal(loginDTO.getUsername()).getData();

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            return new ErrorDataResult<>(messageUtils.getMessage("auth.error.user-not-active"));
        }

        if (!passwordEncryptor.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ErrorDataResult<>(messageUtils.getMessage("auth.error.incorrect-password"));
        }

        JWTToken jwtToken = tokenUtils.generateToken(new TokenPayload(user, TokenType.AUTHENTICATION));

        return new SuccessDataResult<>(jwtToken);
    }

    @Override
    public Result signup(UserAddDTO userAddDTO) {
        DataResult<User> userResult = userService.add(userAddDTO);

        if (!userResult.getStatus()) {
            return userResult.toResult();
        }

        Locale locale = headerUtils.getLanguage();
        emailUtilsService.sendVerifyAccountEmail(userResult.getData(), locale);

        return new SuccessResult(messageUtils.getMessage("auth.success.account-created"));
    }

    @Override
    public Result sendVerifyAccountEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        if (user.getUserStatus() == UserStatus.ACTIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-already-active"));
        }
        if (user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-suspended"));
        }
        if (user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-deleted"));
        }

        Locale locale = headerUtils.getLanguage();
        emailUtilsService.sendVerifyAccountEmail(user, locale);
        return new SuccessResult();
    }

    @Override
    public Result sendForgetPasswordEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        if (user.getUserStatus() == UserStatus.PASSIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-not-active"));
        }
        if (user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-suspended"));
        }
        if (user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-deleted"));
        }

        Locale locale = headerUtils.getLanguage();
        emailUtilsService.sendResetPasswordEmail(user, locale);
        return new SuccessResult();
    }

    @Override
    public Result verifyAccount(JWTToken jwtToken) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload(jwtToken);

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        if (user.getUserStatus() == UserStatus.ACTIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-already-active"));
        }
        if (user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-suspended"));
        }
        if (user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-deleted"));
        }

        if (user.getUserStatus() == UserStatus.PASSIVE) {
            userService.selfActivateUser(user.getId());
            return new SuccessResult(messageUtils.getMessage("auth.success.account-activated"));
        }

        return new ErrorResult(messageUtils.getMessage("generic.error"));
    }

    @Override
    public Result resetPassword(PasswordResetDTO passwordResetDTO) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload(passwordResetDTO.getJwtToken());

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        if (user.getUserStatus() == UserStatus.PASSIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-not-active"));
        }
        if (user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-suspended"));
        }
        if (user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-deleted"));
        }

        if (user.getUserStatus() == UserStatus.ACTIVE) {
            userService.changePassword(user.getId(), passwordResetDTO.getPassword());
            return new SuccessResult(messageUtils.getMessage("auth.success.password-reset"));
        }

        return new ErrorResult(messageUtils.getMessage("generic.error"));
    }

}
