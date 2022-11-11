package com.can.zupuserservice.core.security.jwt.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken {
    @NotNull
    private String token;
}
