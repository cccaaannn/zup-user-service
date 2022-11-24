package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.zupuserservice.core.data.enums.OnlineStatus;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;

public interface IUserOnlineStatusService {
    Result setUserOnlineStatus(Long userId, OnlineStatus newStatus);

    SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId);
}
