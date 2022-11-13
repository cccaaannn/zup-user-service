package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import com.kurtcan.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDTO;
import com.kurtcan.zupuserservice.data.entity.UserFriend;

import java.util.List;
import java.util.Optional;

public interface IUserFriendService {
    DataResult<List<UserDTO>> getFriends();

    DataResult<Optional<UserFriend>> getFriend(Long ownUserId, Long friendUserId);

    Result toggleFriend(UserFriendAddDeleteDTO userFriendAddDeleteDTO);

    Result addByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO);

    Result deleteByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO);
}
