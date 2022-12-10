package com.kurtcan.zupuserservice.config;

import com.kurtcan.javacore.security.jwt.abstracts.IJWTUtils;
import com.kurtcan.javacore.security.jwt.auth0.JWTUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret_key}")
    String secretKey;

    @Value("${security.jwt.authentication_expires_after}")
    Long authenticationExpiresAfter;

    @Value("${security.jwt.account_activation_expires_after}")
    Long accountActivationExpiresAfter;

    @Value("${security.jwt.password_reset_expires_after}")
    Long passwordResetExpiresAfter;

    @Bean
    @Primary
    IJWTUtils getAuthenticationJwtUtils() {
        return new JWTUtils(authenticationExpiresAfter, secretKey);
    }

    @Bean
    @Qualifier("account_activation")
    IJWTUtils getAccountActivationJwtUtils() {
        return new JWTUtils(accountActivationExpiresAfter, secretKey);
    }

    @Bean
    @Qualifier("password_reset")
    IJWTUtils getPasswordResetJwtUtils() {
        return new JWTUtils(accountActivationExpiresAfter, secretKey);
    }

}
