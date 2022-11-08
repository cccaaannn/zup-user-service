package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.data.dto.JWTToken;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.data.dto.TokenPayload;

public interface IAuthorizationService {
    DataResult<TokenPayload> isAuthorized(JWTToken jwtToken);
}
