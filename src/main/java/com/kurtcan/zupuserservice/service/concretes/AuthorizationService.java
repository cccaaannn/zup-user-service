package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.service.abstracts.IAuthorizationService;
import com.kurtcan.zupuserservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements IAuthorizationService {

    private final TokenUtils tokenUtils;

    @Autowired
    public AuthorizationService(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public DataResult<TokenPayload> isAuthorized(JWTToken jwtToken) {
        return new SuccessDataResult<>(tokenUtils.getTokenPayload(jwtToken));
    }

}
