package com.can.zupuserservice.config;

import com.can.zupuserservice.data.enums.DefaultRoles;
import com.can.zupuserservice.filter.AuthorizationFilter;
import com.can.zupuserservice.service.abstracts.ITokenUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final ITokenUtilsService tokenUtilsService;

    @Autowired
    public SecurityConfig(ITokenUtilsService tokenUtilsService) {
        this.tokenUtilsService = tokenUtilsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        OncePerRequestFilter authorizationFilter = new AuthorizationFilter(tokenUtilsService);

        http.csrf().disable();
        http.cors().disable();
        http.httpBasic().disable();
        http.exceptionHandling().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/v1/auth/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/account/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/authorization/**").permitAll();
        http.authorizeRequests().antMatchers("api/v1/user/**").hasAnyAuthority(DefaultRoles.USER.name, DefaultRoles.ADMIN.name, DefaultRoles.SYS_ADMIN.name);
        http.authorizeRequests().antMatchers("api/v1/role/**").hasAnyAuthority(DefaultRoles.USER.name, DefaultRoles.ADMIN.name, DefaultRoles.SYS_ADMIN.name);
//        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
