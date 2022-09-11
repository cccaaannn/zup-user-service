package com.can.zupuserservice.data.dto.auth;

import com.can.zupuserservice.core.aspects.annotations.StrongPassword;
import com.can.zupuserservice.core.data.dto.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {

    @NotNull
    private AccessToken accessToken;

    @StrongPassword
    private String password;

}
