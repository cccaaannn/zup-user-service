package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import com.kurtcan.javacore.utilities.result.concretes.DataResult;
import com.kurtcan.javacore.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.data.dto.auth.LoginDTO;
import com.kurtcan.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.kurtcan.zupuserservice.data.dto.auth.UserEmailDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;

public interface IAuthService {
    DataResult<JWTToken> login(LoginDTO loginDTO);

    Result signup(UserAddDTO userAddDTO);

    Result sendVerifyAccountEmail(UserEmailDTO userEmailDTO);

    Result sendForgetPasswordEmail(UserEmailDTO userEmailDTO);

    Result verifyAccount(JWTToken jwtToken);

    Result resetPassword(PasswordResetDTO passwordResetDTO);
}
