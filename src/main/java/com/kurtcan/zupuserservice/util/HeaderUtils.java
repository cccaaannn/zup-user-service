package com.kurtcan.zupuserservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

@Component
public class HeaderUtils {

    private String isAuthorizationHeaderValid(String header) {
        try {
            String[] tokenArr = header.split("\\s+");
            if (tokenArr.length == 2 && tokenArr[0].equals("Bearer")) {
                return tokenArr[1];
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    public String getToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(servletRequestAttributes)) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String header = request.getHeader("Authorization");
            String token = isAuthorizationHeaderValid(header);
            return token;
        }
        else{
            return null;
        }
    }

    public Locale getLanguage() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(servletRequestAttributes)) {
            try {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                Locale header = new Locale(request.getHeader("Accept-Language"));
                return header;
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public Locale getLanguageWithDefaultFallback() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Locale locale = new Locale(Locale.ENGLISH.getLanguage());
        if (Objects.isNull(servletRequestAttributes)) {
            return locale;
        }
        try {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return new Locale(request.getHeader("Accept-Language"));
        }
        catch (Exception e) {
            return locale;
        }
    }

    public String getIP() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(servletRequestAttributes)) {
            try {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                return request.getRemoteHost();
            }
            catch (Exception e) {
                return "0.0.0.0";
            }
        }
        return "0.0.0.0";
    }

    public String getFromHeader(String field) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(servletRequestAttributes)) {
            try {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                return request.getHeader(field);
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}