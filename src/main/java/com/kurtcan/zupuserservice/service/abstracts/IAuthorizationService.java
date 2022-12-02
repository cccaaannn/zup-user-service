package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import com.kurtcan.javacore.utilities.result.concretes.DataResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;

public interface IAuthorizationService {
    DataResult<TokenPayload> isAuthorized(JWTToken jwtToken);
}
