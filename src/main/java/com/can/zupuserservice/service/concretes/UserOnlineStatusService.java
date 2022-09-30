package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.data.enums.OnlineStatus;
import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.core.exception.NotFoundException;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.entity.UserOnlineStatus;
import com.can.zupuserservice.repository.UserOnlineStatusRepository;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import com.can.zupuserservice.service.abstracts.IUserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Service
public class UserOnlineStatusService implements IUserOnlineStatusService {

    private final UserOnlineStatusRepository userOnlineStatusRepository;
    private final ITokenUtilsService tokenUtilsService;

    @Autowired
    public UserOnlineStatusService(UserOnlineStatusRepository userOnlineStatusRepository, ITokenUtilsService tokenUtilsService) {
        this.userOnlineStatusRepository = userOnlineStatusRepository;
        this.tokenUtilsService = tokenUtilsService;
    }

    @Override
    @Transactional
    public Result setUserOnlineStatus(Long userId, OnlineStatus newStatus) {
        userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();
        if(!tokenPayload.getId().equals(userId)) {
            throw new ForbiddenException("Can not change status of another user.");
        }

        userOnlineStatusRepository.updateUserOnlineStatus(userId, newStatus.status, new Date());
        return new SuccessResult("Status updated");
    }

    @Override
    public SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId) {
        UserOnlineStatus userOnlineStatus = userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);

        // TODO check if requested user is fried of the current user
        // TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();

        return new SuccessDataResult<>(userOnlineStatus);
    }

}
