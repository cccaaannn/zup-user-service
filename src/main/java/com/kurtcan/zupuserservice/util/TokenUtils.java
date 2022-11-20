package com.kurtcan.zupuserservice.util;

import com.kurtcan.zupuserservice.core.exception.ForbiddenException;
import com.kurtcan.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.security.jwt.exceptions.JWTException;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenUtils {

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

}
