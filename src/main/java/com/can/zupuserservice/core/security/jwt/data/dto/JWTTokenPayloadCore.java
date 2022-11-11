package com.can.zupuserservice.core.security.jwt.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTTokenPayloadCore<T> {
    private String issuer;
    private Date expiresAt;
    private Date issuedAt;
    private T data;
}