package com.can.zupuserservice.service.concretes;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.can.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import com.can.zupuserservice.util.HeaderOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenUtilsService implements ITokenUtilsService {

    private final HeaderOperations headerOperations;
    private final IJWTUtils jwtUtils;

    @Autowired
    public TokenUtilsService(HeaderOperations headerOperations, IJWTUtils jwtUtils) {
        this.headerOperations = headerOperations;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public TokenPayload getTokenPayload() throws ForbiddenException, JWTVerificationException {
        String token = headerOperations.getToken();
        if (Objects.isNull(token)) {
            throw new ForbiddenException("Token is null");
        }
        return jwtUtils.getDataAsObject(new JWTToken(token), TokenPayload.class);
    }

    @Override
    public TokenPayload getTokenPayload(JWTToken jwtToken) {
        return jwtUtils.getDataAsObject(jwtToken, TokenPayload.class);
    }

    @Override
    public JWTToken generateToken(TokenPayload tokenPayload) {
        return jwtUtils.generateToken(tokenPayload);
    }

}
