package com.can.zupuserservice.data.dto;

import com.can.zupuserservice.data.entity.User;
import com.can.zupuserservice.data.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenPayload {

    private Long id;
    private Integer userStatus;
    private String username;
    private String email;
    private String role;
    private Integer tokenType;

    public TokenPayload(User user, TokenType tokenType) {
        fromUser(user, tokenType);
    }

    public void fromUser(User user, TokenType tokenType) {
        id = user.getId();
        userStatus = user.getUserStatus().status;
        username = user.getUsername();
        email = user.getEmail();
        role = user.getRole().getName();
        this.tokenType = tokenType.type;
    }

}
