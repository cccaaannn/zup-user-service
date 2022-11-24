package com.kurtcan.zupuserservice.data.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kurtcan.zupuserservice.core.data.enums.UserStatus;
import com.kurtcan.zupuserservice.data.dto.rold.RoleDTO;
import com.kurtcan.zupuserservice.data.entity.UserOnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserStatus userStatus;
    private RoleDTO role;
    private UserOnlineStatus userOnlineStatus;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean isFriend = false;

    public UserDTO(Long id, String username, String email, UserStatus userStatus, RoleDTO role, UserOnlineStatus userOnlineStatus, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userStatus = userStatus;
        this.role = role;
        this.userOnlineStatus = userOnlineStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
