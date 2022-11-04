package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessDataResult;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.dto.UserFriend.UserFriendAddDeleteDTO;
import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.entity.UserFriend;
import com.can.zupuserservice.repository.UserFriendRepository;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import com.can.zupuserservice.service.abstracts.IUserFriendService;
import com.can.zupuserservice.service.abstracts.IUserService;
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
    private final ITokenUtilsService tokenUtilsService;

    @Autowired
    public UserFriendService(UserFriendRepository userFriendRepository, @Lazy IUserService userService, ITokenUtilsService tokenUtilsService) {
        this.userFriendRepository = userFriendRepository;
        this.userService = userService;
        this.tokenUtilsService = tokenUtilsService;
    }

    @Override
    public DataResult<List<UserDTO>> getAllFriends() {
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();
        return new SuccessDataResult<>(userFriendRepository.getAllFriends(tokenPayload.getId()));
    }

    @Override
    public DataResult<Optional<UserFriend>> getFriend(Long ownUserId, Long friendUserId) {
        return new SuccessDataResult<>(userFriendRepository.getFriend(ownUserId, friendUserId));
    }

    @Override
    @Transactional
    public Result toggleFriend(UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();
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

        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();
        UserFriend oldFriend = userFriendRepository.getFriend(tokenPayload.getId(), userFriendAddDeleteDTO.getUserFriendId()).orElse(null);
        if (Objects.nonNull(oldFriend)) {
            return new ErrorResult("This user is already added as friend.");
        }

        userFriend.setOwnUserFromId(tokenPayload.getId());

        userFriendRepository.save(userFriend);

        return new SuccessResult("User %s added to friends list.".formatted(userFriend.getFriendUser().getUsername()));
    }

    @Override
    @Transactional
    public Result deleteByFriendId(UserFriendAddDeleteDTO userFriendAddDeleteDTO) {
        TokenPayload tokenPayload = tokenUtilsService.getTokenPayload();
        UserFriend oldFriend = userFriendRepository.getFriend(tokenPayload.getId(), userFriendAddDeleteDTO.getUserFriendId()).orElse(null);
        if (Objects.isNull(oldFriend)) {
            return new ErrorResult("This user was not added as friend.");
        }

        userFriendRepository.delete(oldFriend);
        return new SuccessResult("User %s removed from friends list.".formatted(oldFriend.getFriendUser().getUsername()));
    }

}
