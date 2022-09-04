package com.can.zupuserservice.core.security.jwt.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.data.dto.AccessToken;

import java.util.Map;

public interface IJWTUtils {
    AccessToken generateTokenRaw(Map<String, Object> claims);
    <TokenPayloadType> AccessToken generateToken(TokenPayloadType tokenPayloadType);
    Result verifyToken(AccessToken accessToken);
    Map<String, ?> verifyAndGetTokenRaw(AccessToken accessToken);
    <TokenPayloadType> TokenPayloadType verifyAndGetToken(AccessToken accessToken, Class<TokenPayloadType> tokenPayloadType);
}
