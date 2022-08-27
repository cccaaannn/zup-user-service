package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.dto.UserFriend.UserFriendAddDTO;

public interface IUserFriendService {
    Result add(UserFriendAddDTO userFriendAddDTO);
}
