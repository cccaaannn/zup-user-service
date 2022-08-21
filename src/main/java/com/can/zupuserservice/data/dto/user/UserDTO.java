package com.can.zupuserservice.data.dto.user;

import com.can.zupuserservice.core.data.enums.UserStatus;
import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.data.entity.UserOnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserStatus userStatus;
    private Role role;
    private UserOnlineStatus userOnlineStatus;
}
