package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.data.dto.AccessToken;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.entity.User;

public interface ITokenUtilsService {
    DataResult<TokenPayload> getTokenPayload();

    DataResult<AccessToken> generateToken(TokenPayload tokenPayload);

    DataResult<User> verifyAndGetUser(AccessToken accessToken);
}
