package com.kurtcan.zupuserservice.service.abstracts;

import com.kurtcan.zupuserservice.core.utilities.result.concretes.DataResult;
import com.kurtcan.zupuserservice.core.utilities.result.concretes.Result;
import com.kurtcan.zupuserservice.data.dto.user.UserAddDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserDeleteDTO;
import com.kurtcan.zupuserservice.data.dto.user.UserUpdateDTO;
import com.kurtcan.zupuserservice.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserService {
    DataResult<Page<UserDTO>> getAll(PageRequest pageRequest, List<Long> ids);

    DataResult<UserDTO> getById(Long userId);

    DataResult<User> getByIdInternal(Long userId);

    DataResult<UserDTO> getByUsername(String username);

    DataResult<User> getByUsernameInternal(String username);

    DataResult<User> getByEmail(String email);

    Boolean isExistsByUsername(String username);

    Boolean isExistsByUsername(String username, Long id);

    Boolean isExistsByEmail(String email);

    Boolean isExistsByEmail(String email, Long id);

    Result selfActivateUser(Long id);

    Result activateUser(Long id);

    Result suspendUser(Long id);

    Result changePassword(Long id, String newPassword);

    DataResult<User> add(UserAddDTO userAddDTO);

    Result update(UserUpdateDTO userUpdateDTO);

    Result delete(UserDeleteDTO userDeleteDTO);
}
