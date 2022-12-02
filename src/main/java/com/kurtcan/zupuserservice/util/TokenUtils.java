package com.kurtcan.zupuserservice.util;

import com.kurtcan.javacore.exception.ForbiddenException;
import com.kurtcan.javacore.exception.UnAuthorizedException;
import com.kurtcan.javacore.security.aspects.abstracts.IAuthorizationProvider;
import com.kurtcan.javacore.security.jwt.abstracts.IJWTUtils;
import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import com.kurtcan.javacore.security.jwt.exceptions.JWTException;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TokenUtils implements IAuthorizationProvider {

    private final HeaderUtils headerUtils;
    private final IJWTUtils jwtUtils;

    @Autowired
    public TokenUtils(HeaderUtils headerUtils, IJWTUtils jwtUtils) {
        this.headerUtils = headerUtils;
        this.jwtUtils = jwtUtils;
    }

    public TokenPayload getTokenPayload() throws JWTException {
        String token = headerUtils.getToken();
        if (Objects.isNull(token)) {
            throw new ForbiddenException("Token is null");
        }
        return jwtUtils.getDataAsObject(new JWTToken(token), TokenPayload.class);
    }

    public TokenPayload getTokenPayload(JWTToken jwtToken) {
        return jwtUtils.getDataAsObject(jwtToken, TokenPayload.class);
    }

    public JWTToken generateToken(TokenPayload tokenPayload) {
        return jwtUtils.generateToken(tokenPayload);
    }



    @Override
    public List<String> getClaims() throws UnAuthorizedException {
        try {
            TokenPayload tokenPayload = getTokenPayload();
            return List.of(tokenPayload.getRole());
        } catch (JWTException ignored) {
            throw new UnAuthorizedException();
        }
    }

    @Override
    public void isAuthorized() throws UnAuthorizedException {
        try {
            getTokenPayload();
        } catch (JWTException ignored) {
            throw new UnAuthorizedException();
        }
    }
}
