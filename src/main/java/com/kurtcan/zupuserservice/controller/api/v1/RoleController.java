package com.kurtcan.zupuserservice.controller.api.v1;

import com.kurtcan.javacore.security.aspects.annotations.SecuredRoute;
import com.kurtcan.javacore.utilities.http.response.concrete.HttpApiResponseBuilder;
import com.kurtcan.zupuserservice.service.abstracts.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${api.path.prefix}/roles")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @SecuredRoute
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return HttpApiResponseBuilder.toHttpResponse(roleService.getAll());
    }

}
