package com.can.zupuserservice.service.concretes;

import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.SuccessResult;
import com.can.zupuserservice.data.entity.UserFriend;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.dto.UserFriend.UserFriendAddDTO;
import com.can.zupuserservice.repository.UserFriendRepository;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import com.can.zupuserservice.service.abstracts.IUserFriendService;
import com.can.zupuserservice.service.abstracts.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFriendService implements IUserFriendService {

    private final UserFriendRepository userFriendRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;
    private final ITokenUtilsService ITokenUtilsService;

    @Autowired
    public UserFriendService(UserFriendRepository userFriendRepository, IUserService userService, ModelMapper modelMapper, ITokenUtilsService ITokenUtilsService) {
        this.userFriendRepository = userFriendRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.ITokenUtilsService = ITokenUtilsService;
    }

    @Override
    public Result add(UserFriendAddDTO userFriendAddDTO) {

        // Check if user exists
        userFriendAddDTO.setFriendUser(userService.getById(userFriendAddDTO.getFriendUser().getId()).getData());

        UserFriend userFriend = modelMapper.map(userFriendAddDTO, UserFriend.class);

        TokenPayload tokenPayload = ITokenUtilsService.getTokenPayload();

        userFriend.setOwnUserFromId(tokenPayload.getId());

        userFriendRepository.save(userFriend);

        return new SuccessResult("User %s added to friends list".formatted(userFriendAddDTO.getFriendUser().getUsername()));

    }

}
