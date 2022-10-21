package com.can.zupuserservice.service.abstracts;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.data.dto.TokenPayload;

public interface ITokenUtilsService {
    TokenPayload getTokenPayload() throws ForbiddenException, JWTVerificationException;

    TokenPayload getTokenPayload(AccessToken accessToken) throws JWTVerificationException;

    AccessToken generateToken(TokenPayload tokenPayload);
}
