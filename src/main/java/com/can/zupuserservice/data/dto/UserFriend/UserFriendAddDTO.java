package com.can.zupuserservice.data.dto.UserFriend;

import com.can.zupuserservice.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendAddDTO {
    private User friendUser;
}
