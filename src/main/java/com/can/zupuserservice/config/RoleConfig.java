package com.can.zupuserservice.config;

import com.can.zupuserservice.data.entity.Role;
import com.can.zupuserservice.data.enums.DefaultRoles;
import com.can.zupuserservice.service.abstracts.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class RoleConfig {

    private final IRoleService roleService;

    @Autowired
    public RoleConfig(IRoleService roleService) {
        this.roleService = roleService;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        roleService.add(new Role(null, DefaultRoles.USER.name, DefaultRoles.USER.description, null));
        roleService.add(new Role(null, DefaultRoles.ADMIN.name, DefaultRoles.ADMIN.description, null));
        roleService.add(new Role(null, DefaultRoles.SYS_ADMIN.name, DefaultRoles.SYS_ADMIN.description, null));
    }

}
