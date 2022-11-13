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
import com.can.zupuserservice.service.abstracts.IUserOnlineStatusService;
import com.can.zupuserservice.util.MessageUtils;
import com.can.zupuserservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserOnlineStatusService implements IUserOnlineStatusService {

    private final UserOnlineStatusRepository userOnlineStatusRepository;
    private final TokenUtils tokenUtils;
    private final MessageUtils messageUtils;

    @Autowired
    public UserOnlineStatusService(UserOnlineStatusRepository userOnlineStatusRepository, TokenUtils tokenUtils, MessageUtils messageUtils) {
        this.userOnlineStatusRepository = userOnlineStatusRepository;
        this.tokenUtils = tokenUtils;
        this.messageUtils = messageUtils;
    }

    @Override
    @Transactional
    public Result setUserOnlineStatus(Long userId, OnlineStatus newStatus) {
        userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        if (!tokenPayload.getId().equals(userId)) {
            throw new ForbiddenException(messageUtils.getMessage("user-online-status.error.not-own-status"));
        }

        userOnlineStatusRepository.updateUserOnlineStatus(userId, newStatus.status, new Date());
        return new SuccessResult(messageUtils.getMessage("user-online-status.success.updated"));
    }

    @Override
    public SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId) {
        UserOnlineStatus userOnlineStatus = userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        return new SuccessDataResult<>(userOnlineStatus);
    }

}
