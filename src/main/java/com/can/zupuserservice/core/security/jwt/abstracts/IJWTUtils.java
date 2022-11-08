package com.can.zupuserservice.core.security.jwt.abstracts;

import com.can.zupuserservice.core.data.dto.JWTToken;
import com.can.zupuserservice.core.data.dto.JWTTokenPayloadCore;
import com.can.zupuserservice.core.exception.JWTException;

import java.util.Map;

public interface IJWTUtils {
    JWTToken generateToken(Map<String, Object> claims);

    <TokenPayloadType> JWTToken generateToken(TokenPayloadType tokenPayloadType);

    Map<String, Object> getDataAsMap(JWTToken jwtToken) throws JWTException;

    <TokenPayloadType> TokenPayloadType getDataAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException;

    JWTTokenPayloadCore<Map<String, Object>> getPayloadAsMap(JWTToken jwtToken) throws JWTException;

    <TokenPayloadType> JWTTokenPayloadCore<TokenPayloadType> getPayloadAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException;

    boolean isValid(JWTToken jwtToken);
}
