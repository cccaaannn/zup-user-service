package com.kurtcan.zupuserservice.service.concretes;

import com.kurtcan.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.abstracts.Result;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDTO;
import com.kurtcan.zupuserservice.data.entity.UserFriend;
import com.kurtcan.zupuserservice.mapper.UserMapper;
import com.kurtcan.zupuserservice.repository.UserFriendRepository;
import com.kurtcan.zupuserservice.service.abstracts.IUserFriendService;
import com.kurtcan.zupuserservice.service.abstracts.IUserService;
import com.kurtcan.zupuserservice.util.MessageUtils;
import com.kurtcan.zupuserservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserFriendService implements IUserFriendService {

    private final UserFriendRepository userFriendRepository;
    private final IUserService userService;
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final MessageUtils messageUtils;

    @Autowired
    public UserFriendService(UserFriendRepository userFriendRepository, @Lazy IUserService userService, TokenUtils tokenUtils, UserMapper userMapper, MessageUtils messageUtils) {
        this.userFriendRepository = userFriendRepository;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
        this.userMapper = userMapper;
        this.messageUtils = messageUtils;
    }

    public DataResult<List<UserDTO>> getFriends() {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        return new SuccessDataResult<>(userMapper.usersToUserDTOs(userFriendRepository.getFriends(tokenPayload.getId())));
    }

    @Override
    public DataResult<Optional<UserFriend>> getFriend(Long ownUserId, Long friendUserId) {
        return new SuccessDataResult<>(userFriendRepository.getFriend(ownUserId, friendUserId));
    }

    @Override
    @Transactional
    public Result toggleFriend(UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        UserFriend oldFriend = userFriendRepository.getFriend(tokenPayload.getId(), userFriendAddDeleteDTO.getUserFriendId()).orElse(null);
        if (Objects.isNull(oldFriend)) {
            return addByFriendId(userFriendAddDeleteDTO);
        }
        return deleteByFriendId(userFriendAddDeleteDTO);
    }

    @Override
    @Transactional
    public Result addByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO) {

        // Check if user exists
        UserFriend userFriend = new UserFriend();
        userFriend.setFriendUser(userService.getByIdInternal(userFriendAddDeleteDTO.getUserFriendId()).getData());

        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        UserFriend oldFriend = userFriendRepository.getFriend(tokenPayload.getId(), userFriendAddDeleteDTO.getUserFriendId()).orElse(null);
        if (Objects.nonNull(oldFriend)) {
            return new ErrorResult(messageUtils.getMessage("user-friend.error.already-exists"));
        }

        userFriend.setOwnUserFromId(tokenPayload.getId());

        userFriendRepository.save(userFriend);

        return new SuccessResult(messageUtils.getMessage("user-friend.success.added", userFriend.getFriendUser().getUsername()));
    }

    @Override
    @Transactional
    public Result deleteByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        TokenPayload tokenPayload = tokenUtils.getTokenPayload();
        UserFriend oldFriend = userFriendRepository.getFriend(tokenPayload.getId(), userFriendAddDeleteDTO.getUserFriendId()).orElse(null);
        if (Objects.isNull(oldFriend)) {
            return new ErrorResult(messageUtils.getMessage("user-friend.error.not-exists"));
        }

        userFriendRepository.delete(oldFriend);
        return new SuccessResult(messageUtils.getMessage("user-friend.success.removed", oldFriend.getFriendUser().getUsername()));
    }

}
