package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.data.enums.OnlineStatus;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.data.entity.UserOnlineStatus;

public interface IUserOnlineStatusService {
    Result setUserOnlineStatus(Long userId, OnlineStatus newStatus);

    SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId);
}
