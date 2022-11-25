package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.data.enums.UserStatus;
import com.kurtcan.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
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

        log.info("User {} login, username: {}", user.getId(), user.getUsername());
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

        log.info("User {} signup, username: {}", userResult.getData().getId(), userResult.getData().getUsername());
        return new SuccessResult(messageUtils.getMessage("auth.success.account-created"));
    }

    @Override
    public Result sendVerifyAccountEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        Result statusResult = userStatusCheck(user.getUserStatus(), UserStatus.ACTIVE);
        if (!statusResult.getStatus()) {
            return statusResult;
        }

        Locale locale = headerUtils.getLanguage();
        emailUtilsService.sendVerifyAccountEmail(user, locale);
        return new SuccessResult();
    }

    @Override
    public Result sendForgetPasswordEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        Result statusResult = userStatusCheck(user.getUserStatus(), UserStatus.PASSIVE);
        if (!statusResult.getStatus()) {
            return statusResult;
        }

        Locale locale = headerUtils.getLanguage();
        emailUtilsService.sendResetPasswordEmail(user, locale);
        return new SuccessResult();
    }

    @Override
    public Result verifyAccount(JWTToken jwtToken) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload(jwtToken);

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        Result statusResult = userStatusCheck(user.getUserStatus(), UserStatus.ACTIVE);
        if (!statusResult.getStatus()) {
            return statusResult;
        }

        if (user.getUserStatus() != UserStatus.PASSIVE) {
            log.error("Invalid db state detected, user {} has a status of {}", user.getId(), user.getUserStatus());
            return new ErrorResult(messageUtils.getMessage("generic.error"));
        }

        userService.selfActivateUser(user.getId());

        log.info("User {} verified account, username: {}", user.getId(), user.getUsername());
        return new SuccessResult(messageUtils.getMessage("auth.success.account-activated"));
    }

    @Override
    public Result resetPassword(PasswordResetDTO passwordResetDTO) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload(passwordResetDTO.getJwtToken());

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        Result statusResult = userStatusCheck(user.getUserStatus(), UserStatus.PASSIVE);
        if (!statusResult.getStatus()) {
            return statusResult;
        }

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            log.error("Invalid db state detected, user {} has a status of {}", user.getId(), user.getUserStatus());
            return new ErrorResult(messageUtils.getMessage("generic.error"));
        }

        userService.changePassword(user.getId(), passwordResetDTO.getPassword());

        log.info("User {} reset password, username: {}", user.getId(), user.getUsername());
        return new SuccessResult(messageUtils.getMessage("auth.success.password-reset"));
    }


    /*
     * failOn parameter can be specified to state the failing user status, and can be ACTIVE or PASSIVE
     */
    private Result userStatusCheck(UserStatus userStatus, UserStatus failOn) {
        if (UserStatus.ACTIVE.equals(failOn) && userStatus == UserStatus.ACTIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-already-active"));
        }
        if (UserStatus.PASSIVE.equals(failOn) && userStatus == UserStatus.PASSIVE) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-not-active"));
        }
        if (userStatus == UserStatus.SUSPENDED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-suspended"));
        }
        if (userStatus == UserStatus.DELETED) {
            return new ErrorResult(messageUtils.getMessage("auth.error.account-deleted"));
        }

        return new SuccessResult();
    }

}
