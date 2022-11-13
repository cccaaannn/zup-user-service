package com.can.zupuserservice.interceptor;

import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.data.dto.TokenPayload;
import com.can.zupuserservice.data.entity.EntityBase;
import com.can.zupuserservice.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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
