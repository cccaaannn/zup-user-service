package com.can.zupuserservice.service.abstracts;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.can.zupuserservice.core.data.dto.JWTToken;
import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.data.dto.TokenPayload;

public interface ITokenUtilsService {
    TokenPayload getTokenPayload() throws ForbiddenException, JWTVerificationException;

    TokenPayload getTokenPayload(JWTToken jwtToken) throws JWTVerificationException;

    JWTToken generateToken(TokenPayload tokenPayload);
}
