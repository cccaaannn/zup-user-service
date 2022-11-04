package com.can.zupuserservice.data.dto.user;

import com.can.zupuserservice.core.data.enums.UserStatus;
import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.data.entity.UserOnlineStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserStatus userStatus;
    private Role role;
    private UserOnlineStatus userOnlineStatus;
    private Boolean isFriend = false;

    public UserDTO(Long id, String username, String email, UserStatus userStatus, Role role, UserOnlineStatus userOnlineStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userStatus = userStatus;
        this.role = role;
        this.userOnlineStatus = userOnlineStatus;
    }

}
