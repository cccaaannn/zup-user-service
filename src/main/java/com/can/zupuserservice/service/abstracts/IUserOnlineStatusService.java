package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.utilities.result.abstracts.Result;

public interface IUserOnlineStatusService {
    Result setUserOnline(Long userId);

    Result setUserOffline(Long userId);
}
