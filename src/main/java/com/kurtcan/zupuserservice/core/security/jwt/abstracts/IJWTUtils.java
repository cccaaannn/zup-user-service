package com.kurtcan.zupuserservice.core.security.jwt.abstracts;

import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTTokenPayloadCore;
import com.kurtcan.zupuserservice.core.security.jwt.exceptions.JWTException;

import java.util.Map;

/**
 * <h2>Interface for generic jwt utils.</h2>
 * <hr/>
 *
 * @author Can Kurt
 * @version 1.0
 * @since 2022-11-11
 */
public interface IJWTUtils {

    JWTToken generateToken(Map<String, Object> claims);

    <TokenPayloadType> JWTToken generateToken(TokenPayloadType tokenPayloadType);

    Map<String, Object> getDataAsMap(JWTToken jwtToken) throws JWTException;

    <TokenPayloadType> TokenPayloadType getDataAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException;

    JWTTokenPayloadCore<Map<String, Object>> getPayloadAsMap(JWTToken jwtToken) throws JWTException;

    <TokenPayloadType> JWTTokenPayloadCore<TokenPayloadType> getPayloadAsObject(JWTToken jwtToken, Class<TokenPayloadType> tokenPayloadType) throws JWTException;

    boolean isValid(JWTToken jwtToken);
}
