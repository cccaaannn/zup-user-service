package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.core.data.enums.OnlineStatus;
import com.kurtcan.zupuserservice.core.exception.ForbiddenException;
import com.kurtcan.zupuserservice.core.exception.NotFoundException;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;
import com.kurtcan.zupuserservice.repository.UserOnlineStatusRepository;
import com.kurtcan.zupuserservice.service.abstracts.IUserOnlineStatusService;
import com.kurtcan.zupuserservice.util.MessageUtils;
import com.kurtcan.zupuserservice.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Slf4j
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

        userOnlineStatusRepository.updateUserOnlineStatus(userId, newStatus.status, OffsetDateTime.now());

        log.info("User {} status updated to {}", userId, newStatus.status);
        return new SuccessResult(messageUtils.getMessage("user-online-status.success.updated"));
    }

    @Override
    public SuccessDataResult<UserOnlineStatus> getUserOnlineStatus(Long userId) {
        UserOnlineStatus userOnlineStatus = userOnlineStatusRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
        return new SuccessDataResult<>(userOnlineStatus);
    }

}
