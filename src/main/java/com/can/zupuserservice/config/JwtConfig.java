package com.can.zupuserservice.config;

import com.can.zupuserservice.core.security.jwt.abstracts.IJWTUtils;
import com.can.zupuserservice.core.security.jwt.auth0.Auth0TokenUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Value("${security.jwt.authentication-expires-after}")
    Long authenticationExpiresAfter;

    @Value("${security.jwt.account_activation-expires-after}")
    Long accountActivationExpiresAfter;

    @Value("${security.jwt.password_reset-expires-after}")
    Long passwordResetExpiresAfter;

    @Bean
    @Primary
    IJWTUtils getAuthenticationJwtUtils() {
        return new Auth0TokenUtils(authenticationExpiresAfter, secretKey);
    }

    @Bean
    @Qualifier("account_activation")
    IJWTUtils getAccountActivationJwtUtils() {
        return new Auth0TokenUtils(accountActivationExpiresAfter, secretKey);
    }

    @Bean
    @Qualifier("password_reset")
    IJWTUtils getPasswordResetJwtUtils() {
        return new Auth0TokenUtils(accountActivationExpiresAfter, secretKey);
    }

}
