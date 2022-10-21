package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.data.enums.UserStatus;
import com.can.zupuserservice.core.security.encryption.abstracts.IPasswordEncryptor;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.dto.auth.LoginDTO;
import com.can.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.can.zupuserservice.data.dto.auth.UserEmailDTO;
import com.can.zupuserservice.data.dto.user.UserAddDTO;
import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.enums.TokenType;
import com.can.zupuserservice.service.abstracts.IAuthService;
import com.can.zupuserservice.service.abstracts.IEmailUtilsService;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import com.can.zupuserservice.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final IPasswordEncryptor passwordEncryptor;
    private final IEmailUtilsService emailUtilsService;
    private final ITokenUtilsService tokenUtilsService;

    @Autowired
    public AuthService(IUserService userService, IPasswordEncryptor passwordEncryptor, IEmailUtilsService emailUtilsService, ITokenUtilsService tokenUtilsService) {
        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.emailUtilsService = emailUtilsService;
        this.tokenUtilsService = tokenUtilsService;
    }


    @Override
    public DataResult<AccessToken> login(LoginDTO loginDTO) {
        User user = userService.getByUsernameInternal(loginDTO.getUsername()).getData();

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            return new ErrorDataResult<>("User is not active");
        }

        if (!passwordEncryptor.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ErrorDataResult<>("Login failed");
        }

        AccessToken accessToken = tokenUtilsService.generateToken(new TokenPayload(user, TokenType.AUTHENTICATION));

        return new SuccessDataResult<>(accessToken);
    }

    @Override
    public Result signup(UserAddDTO userAddDTO) {
        DataResult<User> userResult = userService.add(userAddDTO);

        if (!userResult.getStatus()) {
            return userResult.toResult();
        }

        emailUtilsService.sendVerifyAccountEmail(userResult.getData());

        return new SuccessResult("New account created, waiting to be activated.");
    }

    @Override
    public Result sendVerifyAccountEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        if(user.getUserStatus() == UserStatus.ACTIVE) {
            return new ErrorResult("Account is already active.");
        }
        if(user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult("Account is suspended.");
        }
        if(user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult("Account is deleted.");
        }

        emailUtilsService.sendVerifyAccountEmail(user);
        return new SuccessResult();
    }

    @Override
    public Result sendForgetPasswordEmail(UserEmailDTO userEmailDTO) {
        User user = userService.getByEmail(userEmailDTO.getEmail()).getData();

        if(user.getUserStatus() == UserStatus.PASSIVE) {
            return new ErrorResult("Account is not activated yet.");
        }
        if(user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult("Account is suspended.");
        }
        if(user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult("Account is deleted.");
        }

        emailUtilsService.sendResetPasswordEmail(user);
        return new SuccessResult();
    }

    @Override
    public Result verifyAccount(AccessToken accessToken) {
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload(accessToken);

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        if(user.getUserStatus() == UserStatus.ACTIVE) {
            return new ErrorResult("Account is already active.");
        }
        if(user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult("Account is suspended.");
        }
        if(user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult("Account is deleted.");
        }

        if(user.getUserStatus() == UserStatus.PASSIVE) {
            userService.selfActivateUser(user.getId());
            return new SuccessResult("Account activated.");
        }

        return new ErrorResult("Unknown user state.");
    }

    @Override
    public Result resetPassword(PasswordResetDTO passwordResetDTO) {
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload(passwordResetDTO.getAccessToken());

        User user = userService.getByIdInternal(tokenPayload.getId()).getData();

        if(user.getUserStatus() == UserStatus.PASSIVE) {
            return new ErrorResult("Account is not activated yet.");
        }
        if(user.getUserStatus() == UserStatus.SUSPENDED) {
            return new ErrorResult("Account is suspended.");
        }
        if(user.getUserStatus() == UserStatus.DELETED) {
            return new ErrorResult("Account is deleted.");
        }

        if(user.getUserStatus() == UserStatus.ACTIVE) {
            userService.changePassword(user.getId(), passwordResetDTO.getPassword());
            return new SuccessResult("Password reset.");
        }

        return new ErrorResult("Unknown user state.");
    }

}
