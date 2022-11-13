package com.can.zupuserservice.config;

import com.can.zupuserservice.data.enums.DefaultRoles;
import com.can.zupuserservice.filter.AuthorizationFilter;
import com.can.zupuserservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final TokenUtils tokenUtils;

    private static final List<String> ALLOWED_PATHS = List.of(
            "api/user/v1/auth",
            "api/user/v1/account",
            "api/user/v1/authorization"
    );

    @Autowired
    public SecurityConfig(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        OncePerRequestFilter authorizationFilter = new AuthorizationFilter(tokenUtils, ALLOWED_PATHS);

        http.csrf().disable();
        http.cors().disable();
        http.httpBasic().disable();
        http.exceptionHandling().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers("api/v1/auth/**").permitAll();
//        http.authorizeRequests().antMatchers("api/v1/account/**").permitAll();
//        http.authorizeRequests().antMatchers("api/v1/authorization/**").permitAll();
        http.authorizeRequests().antMatchers("api/v1/user/**").hasAnyAuthority(DefaultRoles.USER.name, DefaultRoles.ADMIN.name, DefaultRoles.SYS_ADMIN.name);
        http.authorizeRequests().antMatchers("api/v1/role/**").hasAnyAuthority(DefaultRoles.USER.name, DefaultRoles.ADMIN.name, DefaultRoles.SYS_ADMIN.name);
//        http.authorizeRequests().anyRequest().authenticated();
        http.cors().and().addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
