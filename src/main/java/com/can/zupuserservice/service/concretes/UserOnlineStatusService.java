package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.exception.NotFoundException;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.repository.UserOnlineStatusRepository;
import com.can.zupuserservice.service.abstracts.IUserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserOnlineStatusService implements IUserOnlineStatusService {

    private final UserOnlineStatusRepository userOnlineStatusRepository;

    @Autowired
    public UserOnlineStatusService(UserOnlineStatusRepository userOnlineStatusRepository) {
        this.userOnlineStatusRepository = userOnlineStatusRepository;
    }

    @Override
    @Transactional
    public Result setUserOnline(Long userId) {
        userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        userOnlineStatusRepository.updateUserOnlineStatus(userId, 1);
        return new SuccessResult("Status updated");
    }

    @Override
    @Transactional
    public Result setUserOffline(Long userId) {
        userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        userOnlineStatusRepository.updateUserOnlineStatus(userId, 0);
        return new SuccessResult("Status updated");
    }

}
