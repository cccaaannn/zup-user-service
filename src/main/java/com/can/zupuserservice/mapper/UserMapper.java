package com.can.zupuserservice.mapper;

import com.can.zupuserservice.data.dto.user.UserAddDTO;
import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User userAddDTOToUser(UserAddDTO userAddDTO) {
        return modelMapper.map(userAddDTO, User.class);
    }

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
    }

}
