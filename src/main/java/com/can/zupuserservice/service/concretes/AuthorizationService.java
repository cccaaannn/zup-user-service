package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.service.abstracts.IAuthorizationService;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements IAuthorizationService {

    private final ITokenUtilsService tokenUtilsService;

    @Autowired
    public AuthorizationService(ITokenUtilsService tokenUtilsService) {
        this.tokenUtilsService = tokenUtilsService;
    }

    @Override
    public DataResult<TokenPayload> isAuthorized(AccessToken accessToken) {
        return new SuccessDataResult<>(tokenUtilsService.verifyAndGetTokenPayload(accessToken));
    }

}