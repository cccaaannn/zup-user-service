package com.can.zupuserservice.core.security.jwt.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.data.dto.AccessToken;

import java.util.Map;

public interface IJWTUtils {
    DataResult<AccessToken> generateTokenRaw(Map<String, Object> claims);
    <TokenPayloadType> DataResult<AccessToken> generateToken(TokenPayloadType tokenPayloadType);
    Result verifyToken(AccessToken accessToken);
    DataResult<Map<String, ?>> verifyAndGetTokenRaw(AccessToken accessToken);
    <TokenPayloadType> DataResult<TokenPayloadType> verifyAndGetToken(AccessToken accessToken, Class<TokenPayloadType> tokenPayloadType);
}
