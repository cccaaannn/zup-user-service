package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.data.dto.JWTToken;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.dto.auth.LoginDTO;
import com.can.zupuserservice.data.dto.auth.PasswordResetDTO;
import com.can.zupuserservice.data.dto.auth.UserEmailDTO;
import com.can.zupuserservice.data.dto.user.UserAddDTO;

public interface IAuthService {
    DataResult<JWTToken> login(LoginDTO loginDTO);

    Result signup(UserAddDTO userAddDTO);

    Result sendVerifyAccountEmail(UserEmailDTO userEmailDTO);

    Result sendForgetPasswordEmail(UserEmailDTO userEmailDTO);

    Result verifyAccount(JWTToken jwtToken);

    Result resetPassword(PasswordResetDTO passwordResetDTO);
}
