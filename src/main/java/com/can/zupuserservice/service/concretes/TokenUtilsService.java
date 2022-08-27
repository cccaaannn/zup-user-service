package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.entity.User;
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
    public DataResult<TokenPayload> getTokenPayload() {
        String token = headerOperations.getToken();
        if (Objects.isNull(token)) {
            throw new ForbiddenException("Token is null");
        }
        return jwtUtils.verifyAndGetToken(new AccessToken(token), TokenPayload.class);
    }

    @Override
    public DataResult<AccessToken> generateToken(TokenPayload tokenPayload) {
        return jwtUtils.generateToken(tokenPayload);
    }

    @Override
    public DataResult<User> verifyAndGetUser(AccessToken accessToken) {
        return jwtUtils.verifyAndGetToken(accessToken, User.class);
    }

}
