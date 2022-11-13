package com.kurtcan.zupuserservice.data.dto.UserFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendAddDeleteDTO {
    @NotNull
    private Long userFriendId;
}
