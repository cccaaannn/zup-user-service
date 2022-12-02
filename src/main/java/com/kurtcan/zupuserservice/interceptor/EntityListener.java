package com.kurtcan.zupuserservice.interceptor;

import com.kurtcan.javacore.exception.ForbiddenException;
import com.kurtcan.zupuserservice.data.dto.TokenPayload;
import com.kurtcan.zupuserservice.data.entity.EntityBase;
import com.kurtcan.zupuserservice.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Slf4j
@Component
public class EntityListener {

    private static TokenUtils tokenUtils;

    public EntityListener() {
    }

    @Autowired
    public void setTokenUtils(TokenUtils tokenUtils) {
        EntityListener.tokenUtils = tokenUtils;
    }

    @PrePersist
    public void prePersist(Object object) {
        if (object instanceof EntityBase) {
            try {
                TokenPayload tokenPayload = tokenUtils.getTokenPayload();
                ((EntityBase) object).setCreatedBy(tokenPayload.getId());
            } catch (ForbiddenException e) {
                ((EntityBase) object).setCreatedBy(null);
            }
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        if (object instanceof EntityBase) {
            try {
                TokenPayload tokenPayload = tokenUtils.getTokenPayload();
                ((EntityBase) object).setUpdatedBy(tokenPayload.getId());
            } catch (ForbiddenException e) {
                ((EntityBase) object).setUpdatedBy(((EntityBase) object).getUpdatedBy());
            }
        }
    }

}
