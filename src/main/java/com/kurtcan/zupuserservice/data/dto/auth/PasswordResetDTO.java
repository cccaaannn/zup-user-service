package com.kurtcan.zupuserservice.data.dto.auth;

import com.kurtcan.javacore.security.validation.password.aspects.annotations.StrongPassword;
import com.kurtcan.javacore.security.jwt.data.dto.JWTToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {

    @NotNull
    private JWTToken jwtToken;

    @StrongPassword
    private String password;

}
