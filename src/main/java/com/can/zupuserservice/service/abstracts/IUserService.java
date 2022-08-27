package com.can.zupuserservice.service.abstracts;

import com.can.zupuserservice.core.data.dto.SortParamsDTO;
import com.can.zupuserservice.core.utilities.result.abstracts.DataResult;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.dto.user.UserAddDTO;
import com.can.zupuserservice.data.dto.user.UserDTO;
import com.can.zupuserservice.data.dto.user.UserDeleteDTO;
import com.can.zupuserservice.data.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {
    DataResult<Page<UserDTO>> getAll(SortParamsDTO sortParamsDTO);

    DataResult<User> getById(Long userId);

    DataResult<User> getByUsername(String username);

    DataResult<User> getByEmail(String email);

    Boolean isExistsByUsername(String username);

    Boolean isExistsByUsername(String username, Long id);

    Boolean isExistsByEmail(String email);

    Boolean isExistsByEmail(String email, Long id);

    @Transactional
    Result activateUser(Long id);

    @Transactional
    Result suspendUser(Long id);

    @Transactional
    Result changePassword(Long id, String newPassword);

    DataResult<User> add(UserAddDTO userAddDTO);

    Result update(UserUpdateDTO userUpdateDTO);

    Result delete(UserDeleteDTO userDeleteDTO);
}
