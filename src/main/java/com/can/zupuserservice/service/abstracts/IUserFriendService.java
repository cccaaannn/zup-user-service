package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.entity.UserFriend;

import java.util.List;
import java.util.Optional;

public interface IUserFriendService {
    DataResult<List<UserDTO>> getFriends();

    DataResult<Optional<UserFriend>> getFriend(Long ownUserId, Long friendUserId);

    Result toggleFriend(UserFriendAddDeleteDTO userFriendAddDeleteDTO);

    Result addByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO);

    Result deleteByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO);
}
