package com.can.zupuserservice.filter;

import com.can.zupuserservice.core.security.jwt.data.dto.JWTToken;
import com.can.zupuserservice.core.security.jwt.exceptions.JWTException;
import com.can.zupuserservice.core.utilities.result.abstracts.Result;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.util.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    private final List<String> ALLOWED_PATHS;

    public AuthorizationFilter(TokenUtils tokenUtils, List<String> ALLOWED_PATHS) {
        this.tokenUtils = tokenUtils;
        this.ALLOWED_PATHS = ALLOWED_PATHS;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        for (String allowedPath : ALLOWED_PATHS) {
            if (path.contains(allowedPath)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Verify authorization header
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            logger.info("Authorization header missing. Host ip: %s".formatted(request.getRemoteHost()));

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Result errorResult = new ErrorResult("Unauthorized", Map.of("auth", "Authorization header is not valid"));
            new ObjectMapper().writeValue(response.getOutputStream(), errorResult);
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length());

        // Verify token
        TokenPayload tokenPayload;
        try {
            tokenPayload = tokenUtils.getTokenPayload(new JWTToken(token));
        } catch (JWTException e) {
            logger.info("Jwt verification failed. Host ip: %s (%s)".formatted(request.getRemoteHost(), e.getMessage()));

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Result errorResult = new ErrorResult("Forbidden", Map.of("jwt", e.getMessage()));
            new ObjectMapper().writeValue(response.getOutputStream(), errorResult);
            return;
        }

        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(tokenPayload.getRole()));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenPayload.getEmail(), null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }

}
