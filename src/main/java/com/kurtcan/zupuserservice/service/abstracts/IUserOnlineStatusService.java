package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.javacore.data.enums.OnlineStatus;
import com.kurtcan.javacore.utilities.result.concretes.Result;
import com.kurtcan.javacore.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;

public interface IUserOnlineStatusService {
    Result setUserOnlineStatus(Long userId, OnlineStatus newStatus);

    SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId);
}
